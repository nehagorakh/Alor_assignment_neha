package com.alor.HM.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alor.HM.Entity.HotelEntity;

@Repository
public interface HotelRepository extends CrudRepository<HotelEntity, Integer>{

	List<HotelEntity> findByIdInAndCityAndIsActive(List<Integer> hotelIds, String city, int isActive);

	List<HotelEntity> findByIdInAndIsActive(Set<Integer> hotelIds, int isActive);

}
