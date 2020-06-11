package com.alor.HM.Service;

import java.util.List;
import java.util.Set;

import com.alor.HM.Bean.RatingBean;
import com.alor.HM.Entity.RatingEntity;

public interface RatingService {
	
	public RatingEntity addReview(RatingEntity ratingEntity);

	public void deleteUser(int ratingId);


	List<RatingBean> getAllReviews(int hotelId, String gender, String city);

	public List<RatingEntity> getRatingsByHotelIdsIn(Set<Integer> keySet);

}
