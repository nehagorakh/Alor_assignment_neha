package com.alor.HM.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alor.HM.Entity.HotelFaclity;

@Repository
public interface HotelFacilityRepository extends CrudRepository<HotelFaclity, Integer>{

	List<HotelFaclity> findByWifiAndResturantAndAcAndMealsIncluded(Integer wifi, Integer resturant, Integer ac,
			Integer mealsIncluded);

}
