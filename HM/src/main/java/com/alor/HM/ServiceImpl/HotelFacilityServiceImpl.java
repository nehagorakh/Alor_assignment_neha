package com.alor.HM.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alor.HM.Entity.HotelFaclity;
import com.alor.HM.Repository.HotelFacilityRepository;
import com.alor.HM.Service.HotelFacilityService;

@Service
public class HotelFacilityServiceImpl implements HotelFacilityService{
	
	@Autowired
	HotelFacilityRepository hotelFacilityRepository;

	@Override
	public HotelFaclity addHotelFacility(HotelFaclity hotelFacility) {
		
		return hotelFacilityRepository.save(hotelFacility);
	}

	@Override
	public List<HotelFaclity> getHotelFacilityByAllGivenFacilities(int wifi, int resturant, int ac,
			int mealsIncluded) {
		try {
		return hotelFacilityRepository.findByWifiAndResturantAndAcAndMealsIncluded(wifi, resturant, ac, mealsIncluded);
		}catch (Exception e) {
			throw e;
		}
	}
	
	

}
