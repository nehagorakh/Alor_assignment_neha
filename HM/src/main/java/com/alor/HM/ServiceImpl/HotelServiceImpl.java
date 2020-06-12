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
import com.alor.HM.Entity.UserEntity;
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
	public List<HotelResponseBean> getHotels(String city, Integer numberOfRooms, String date, Integer wifi,
			Integer resturant, Integer ac, Integer mealsIncluded, Integer rating) throws ParseException {

		int numberOfRoomsInt = numberOfRooms != null ? numberOfRooms : 0;
		int ratingInt = rating != null ? rating : 0;
		int wifiInt = wifi != null ? wifi : 0;
		int resturantInt = resturant != null ? resturant : 0;
		int acInt = ac != null ? ac : 0;
		int mealsIncludedInt = mealsIncluded != null ? mealsIncluded : 0;

		List<HotelEntity> hotelEntities;
		List<HotelResponseBean> hotelResponseBeanList = new ArrayList<HotelResponseBean>();

		// first getting hotelIds for given facilities
		List<HotelFaclity> hotelFacilityList = hotelFacilityService.getHotelFacilityByAllGivenFacilities(wifiInt,
				resturantInt, acInt, mealsIncludedInt);
		if(hotelFacilityList.isEmpty())
			return hotelResponseBeanList;

		Set<Integer> hotelIds = hotelFacilityList.stream().map(hf -> hf.getHotelId()).collect(Collectors.toSet());

		// getting hotel id's with given city
		if (city != null && !city.equalsIgnoreCase("")) {
			hotelEntities = hotelRepository.findByIdInAndCityAndIsActive(new ArrayList<Integer>(hotelIds), city, 1);
			hotelIds = hotelEntities.stream().map(hf -> hf.getId()).collect(Collectors.toSet());
		}
		// if date is null : generating current Date
		if (date == null)
			date = DateUtils.getCurrentDate();

		// getting rating, facility and hotel related data
		List<RatingEntity> hotelRatings = ratingService.getRatingsByHotelIdsIn(hotelIds);

		Map<Integer, List<RatingEntity>> hotelRatingMapGroupByRating = hotelRatings.stream()
				.collect(Collectors.groupingBy(RatingEntity::getHotelId));

		Map<Integer, List<HotelFaclity>> hotelFacilityMap = hotelFacilityList.stream()
				.collect(Collectors.groupingBy(HotelFaclity::getHotelId));

		List<HotelRoomBooking> hotelRoomBookings = hotelRoomBookingService.getHotelRoomBookingRecords(hotelIds, date);

		Map<Integer, List<HotelRoomBooking>> hotelRoomBookingMapListGroupByHotelId = (hotelRoomBookings.stream()
				.collect(Collectors.groupingBy(HotelRoomBooking::getHotelId)));

		// get all the hotels with given filters
		List<HotelEntity> hotelEntityList = hotelRepository.findByIdInAndIsActive(hotelIds, 1);

		hotelResponseBeanList = getHotelWithAllAvailbaleRooms(hotelEntityList, hotelFacilityMap,
				hotelRoomBookingMapListGroupByHotelId, hotelRatingMapGroupByRating);

		if (ratingInt != 0)
			hotelResponseBeanList = hotelResponseBeanList.stream().filter(hrb -> hrb.getRating() == rating)
					.collect(Collectors.toList());

		if (numberOfRoomsInt != 0)
			hotelResponseBeanList = hotelResponseBeanList.stream()
					.filter(hrb -> hrb.getAvailableRooms() >= numberOfRoomsInt).collect(Collectors.toList());

		return hotelResponseBeanList.stream().sorted(Comparator.comparingInt(HotelResponseBean::getRating))
				.collect(Collectors.toList());
	}
	/**
	 * 
	 * @param hotelEntityList
	 * @param hotelFacilityMap
	 * @param hotelRoomBookingMapListGroupByHotelId
	 * @param hotelRatingMapGroupByRating
	 * @return
	 * setting hotel properties and average rating and available rooms.
	 */
		
	private List<HotelResponseBean> getHotelWithAllAvailbaleRooms(List<HotelEntity> hotelEntityList, Map<Integer, 
			List<HotelFaclity>> hotelFacilityMap, Map<Integer, List<HotelRoomBooking>> hotelRoomBookingMapListGroupByHotelId, Map<Integer, List<RatingEntity>> hotelRatingMapGroupByRating) {

		List<HotelResponseBean> hotelResponseBeanList = new ArrayList<HotelResponseBean>();
		for (HotelEntity hotelEntity : hotelEntityList) {

			HotelResponseBean hotelResponseBean = new HotelResponseBean();
			BeanUtils.copyProperties(hotelEntity, hotelResponseBean);
			BeanUtils.copyProperties(hotelFacilityMap.get(hotelEntity.getId()).get(0), hotelResponseBean);
            
			// getting and setting available rooms
			if(!hotelRoomBookingMapListGroupByHotelId.isEmpty() && hotelRoomBookingMapListGroupByHotelId.get(hotelEntity.getId()) != null)
				hotelResponseBean.setAvailableRooms(hotelResponseBean.getNumberOfRooms() - (hotelRoomBookingMapListGroupByHotelId.
						get(hotelEntity.getId()).stream().mapToInt(hrb -> hrb.getNumberOfRooms()).sum()));
					
			else
				hotelResponseBean.setAvailableRooms(hotelEntity.getNumberOfRooms());
         
			if (!hotelRatingMapGroupByRating.isEmpty() && hotelRatingMapGroupByRating.get(hotelEntity.getId()) != null)
				hotelResponseBean.setAverageRating((int) hotelRatingMapGroupByRating.get(hotelEntity.getId()).stream()
						.mapToDouble(r -> r.getRating()).average().getAsDouble());
			hotelResponseBeanList.add(hotelResponseBean);
		}
		return hotelResponseBeanList;
	}
}
