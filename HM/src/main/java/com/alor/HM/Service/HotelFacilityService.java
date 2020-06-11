package com.alor.HM.Service;

import java.util.List;

import com.alor.HM.Entity.HotelFaclity;

public interface HotelFacilityService {
	
	public HotelFaclity addHotelFacility(HotelFaclity hotelFacility);

	public List<HotelFaclity> getHotelFacilityByAllGivenFacilities(int wifi, int resturant, int ac,
			int mealsIncluded);

}
