package com.alor.HM.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alor.HM.Entity.HotelEntity;

@Repository
public interface HotelRepository extends CrudRepository<HotelEntity, Integer>{

	List<HotelEntity> findByIdInAndCity(List<Integer> hotelIds, String city);

	List<HotelEntity> findByIdIn(Set<Integer> hotelIds);

}
