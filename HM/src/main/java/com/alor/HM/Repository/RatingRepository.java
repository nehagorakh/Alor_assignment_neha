package com.alor.HM.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alor.HM.Entity.RatingEntity;

@Repository
public interface RatingRepository extends CrudRepository<RatingEntity, Integer>{
	
	  List<RatingEntity> findByHotelId(int hotelId);

	  List<RatingEntity> findByHotelIdIn(List<Integer> hotelIds);
}
