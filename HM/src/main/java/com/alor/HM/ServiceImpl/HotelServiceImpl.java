package com.alor.HM.ServiceImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alor.HM.Bean.HotelRequestBean;
import com.alor.HM.Bean.HotelResponseBean;
import com.alor.HM.Entity.HotelEntity;
import com.alor.HM.Entity.HotelFaclity;
import com.alor.HM.Entity.HotelRoomBooking;
import com.alor.HM.Entity.RatingEntity;
import com.alor.HM.Repository.HotelRepository;
import com.alor.HM.Service.HotelFacilityService;
import com.alor.HM.Service.HotelRoomBookingService;
import com.alor.HM.Service.HotelService;
import com.alor.HM.Service.RatingService;
import com.alor.HM.Utility.DateUtils;

@Service
public class HotelServiceImpl implements HotelService{
	
	@Autowired
	HotelRepository hotelRepository;
	
	@Autowired
	HotelFacilityService hotelFacilityService;
	
	@Autowired
	HotelRoomBookingService hotelRoomBookingService;
	
	@Autowired
	RatingService ratingService;

	/**
	 * adding hotel
	 */
	@Override
	public HotelRequestBean addHotel(HotelRequestBean hotelRequestBean) {
		
		HotelEntity hotelEntity = new HotelEntity();
		
		//saving hotel object
		BeanUtils.copyProperties(hotelRequestBean, hotelEntity);
		hotelEntity = hotelRepository.save(hotelEntity);
		
		//saving hotel facility Object
		HotelFaclity hotelFacility = new HotelFaclity();
		BeanUtils.copyProperties(hotelRequestBean, hotelFacility);
		hotelFacility.setHotelId(hotelEntity.getId());
		
		hotelFacility = hotelFacilityService.addHotelFacility(hotelFacility);
		
		return hotelRequestBean;
		
	}
	/**
	 * deleting Hotel By id
	 */

	@Override
	public void deleteHotel(int hotelId) {
		
		if(hotelId <= 0)
			throw new BadRequestException("hotel id can not be zero");
		try {
		HotelEntity hotelEntity = hotelRepository.findById(hotelId).get();
		if(hotelEntity != null) {
			hotelEntity.setIsActive(0);
			hotelRepository.save(hotelEntity);
		}
		
		}catch (Exception e) {
			throw e;
		}
	}
    /**
     * returning all the hotels by given search creterias
     */
	@Override
	public List<HotelResponseBean> getHotels(String city, Integer numberOfRooms, String date, Integer wifi, Integer resturant, Integer ac, Integer mealsIncluded,
			Integer rating) throws ParseException {
		
		int numberOfRoomsInt = numberOfRooms != null ? numberOfRooms : 0;
		int ratingInt = rating != null ? rating : 0;;
		int wifiInt = wifi != null ? wifi : 0;;
		int resturantInt = resturant != null ? resturant : 0;;
		int acInt = ac != null ? ac : 0;;
		int mealsIncludedInt = mealsIncluded != null ? mealsIncluded : 0;;
		List<HotelEntity> hotelEntities;		
		List<HotelResponseBean> hotelResponseBeanList = new ArrayList<HotelResponseBean>();
		
		//first getting hotelIds for given facilities
		List<HotelFaclity> hotelFacilityList = hotelFacilityService.
				getHotelFacilityByAllGivenFacilities(wifiInt, resturantInt, acInt, mealsIncludedInt);	
		
		Set<Integer> hotelIds = hotelFacilityList.stream().map(hf -> hf.getHotelId()).collect(Collectors.toSet());
		
		//getting hotel id's with given city
		if(city != null && city.equalsIgnoreCase("")) {
			hotelEntities = hotelRepository.findByIdInAndCity((List)hotelIds, city);
			hotelIds = hotelEntities.stream().map(hf -> hf.getId()).collect(Collectors.toSet());
		}		
		//if date is null : generating current Date
		if(date == null )
			date = DateUtils.getCurrentDate();
		
		//getting rating, facility and hotel related data 				
        List<RatingEntity> hotelRatings = ratingService.getRatingsByHotelIdsIn(hotelIds);
		
		Map<Integer, List<RatingEntity>> hotelRatingMapGroupByRating = hotelRatings.stream().collect(Collectors.
				groupingBy(RatingEntity :: getHotelId));
		
        Map<Integer, List<HotelEntity>> hotelMap = hotelRepository.findByIdIn(hotelIds).stream().collect(Collectors.
        		groupingBy(HotelEntity :: getId));
		
		Map<Integer, List<HotelFaclity>> hotelFacilityMap = hotelFacilityList.stream().collect(Collectors.
				groupingBy(HotelFaclity :: getHotelId));
		
		//getting list of hotels where some rooms are booked on given date or today
		List<HotelRoomBooking> hotelRoomBookings = hotelRoomBookingService.getHotelRoomBookingRecords(hotelIds, date);
		List<HotelResponseBean> hotelResponseBeanListWithSomeBookedRooms = getHotelWithSomeBookedAndAvailableRooms(hotelRoomBookings, 
				hotelMap, hotelFacilityMap, hotelRatingMapGroupByRating);
				
		//for hotels which is not having any booking for given date ot today
		hotelIds.removeAll(hotelRoomBookings.stream().map(hrb -> hrb.getHotelId()).collect(Collectors.toList()));		
		List<HotelResponseBean> hotelResponseBeanListWithAllAvailableRooms = getHotelWithAllAvailbaleRooms(hotelIds, hotelMap, 
				hotelFacilityMap, hotelRatingMapGroupByRating);
		
		hotelResponseBeanList.addAll(hotelResponseBeanListWithSomeBookedRooms);
		hotelResponseBeanList.addAll(hotelResponseBeanListWithAllAvailableRooms);
				
		if(ratingInt != 0)
			hotelResponseBeanList =  hotelResponseBeanList.stream().filter(hrb -> hrb.getRating() == rating).collect(Collectors.toList());
		
		if(numberOfRoomsInt != 0)
			hotelResponseBeanList =  hotelResponseBeanList.stream().filter(hrb -> hrb.getAvailableRooms() >= numberOfRoomsInt).
			 collect(Collectors.toList());
		
		return hotelResponseBeanList.stream().sorted().collect(Collectors.toList());
		}
	
	
	private List<HotelResponseBean> getHotelWithAllAvailbaleRooms(Set<Integer> hotelIds, Map<Integer, List<HotelEntity>> hotelMap, 
			Map<Integer, List<HotelFaclity>> hotelFacilityMap, Map<Integer, List<RatingEntity>> hotelRatingMapGroupByRating) {
            
		List<HotelResponseBean> hotelResponseBeanList = new ArrayList<HotelResponseBean>();
		for(int hoteId : hotelIds) {
			
			HotelResponseBean hotelResponseBean1 = new HotelResponseBean();
			hotelResponseBean1.setHotelId(hoteId);
			BeanUtils.copyProperties(hotelMap.get(hoteId).get(0), hotelResponseBean1);
			BeanUtils.copyProperties(hotelFacilityMap.get(hoteId), hotelResponseBean1);
			
			hotelResponseBean1.setAvailableRooms(hotelMap.get(hoteId).get(0).getNumberOfRooms());
			
			if(hotelRatingMapGroupByRating.size() > 0)
				hotelResponseBean1.setAverageRating((int) hotelRatingMapGroupByRating.get(hoteId).stream().mapToDouble(r -> 
				                                           r.getRating()).average().getAsDouble());	
			hotelResponseBeanList.add(hotelResponseBean1);			
		}
		return hotelResponseBeanList;
	}
	
	private List<HotelResponseBean> getHotelWithSomeBookedAndAvailableRooms(List<HotelRoomBooking> hotelRoomBookings, 
			Map<Integer, List<HotelEntity>> hotelMap, Map<Integer, List<HotelFaclity>> hotelFacilityMap, 
			Map<Integer, List<RatingEntity>> hotelRatingMapGroupByRating) {
		
		List<HotelResponseBean> hotelResponseBeanList = new ArrayList<HotelResponseBean>();		
		HotelResponseBean hotelResponseBean;
		
		if(hotelRoomBookings.size() > 0) {
			
			Map<Integer, List<HotelRoomBooking>> hotelRoomBookingMapListGroupByHotelId  = (hotelRoomBookings.stream().collect(Collectors.
					groupingBy(HotelRoomBooking :: getHotelId)));
			
			//for hotels which is having booking on given date or today
			for(Map.Entry<Integer, List<HotelRoomBooking>> entry : hotelRoomBookingMapListGroupByHotelId.entrySet()) {
				
				hotelResponseBean = new HotelResponseBean();
				hotelResponseBean.setHotelId(entry.getKey());
							
				BeanUtils.copyProperties(hotelMap.get(hotelResponseBean.getHotelId()).get(0), hotelResponseBean);
				BeanUtils.copyProperties(hotelFacilityMap.get(hotelResponseBean.getHotelId()).get(0), hotelResponseBean);
				
				//getting and setting available tooms
				hotelResponseBean.setAvailableRooms(hotelResponseBean.getNumberOfRooms() - (entry.getValue().stream().mapToInt(hrb -> hrb.getNumberOfRooms()).sum()));	
				
				//getting and setting rating
				if(hotelRatingMapGroupByRating.size() > 0)
				hotelResponseBean.setAverageRating((int) hotelRatingMapGroupByRating.get(entry.getKey()).stream().mapToDouble(r -> 
				                                           r.getRating()).average().getAsDouble());				
				hotelResponseBeanList.add(hotelResponseBean);			
			}
			}
		return hotelResponseBeanList;		
	}
}
