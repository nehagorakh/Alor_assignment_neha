package com.alor.HM.rating.api;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alor.HM.Bean.RatingBean;
import com.alor.HM.Entity.RatingEntity;
import com.alor.HM.Service.RatingService;


@RequestMapping("/rating")
@RestController
public class RatingController {

	
	@Autowired 
	RatingService ratingService;
	
	/**
	 * to add review
	 */
	
	@RequestMapping(value = "/review", method = RequestMethod.POST)
	public ResponseEntity<RatingEntity> addReview(@RequestBody RatingEntity ratingEntity) {
		try {
			ratingEntity= ratingService.addReview(ratingEntity);
		}catch (Exception e) {
			throw e;
		}
		return new ResponseEntity<RatingEntity>(ratingEntity, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/review/{ratingId}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteReview(@PathVariable int ratingId) {
		try {
			ratingService.deleteUser(ratingId);
		}catch (Exception e) {
			throw e;
		} 
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/hotel/{hotelId}/review", method = RequestMethod.GET)
	public ResponseEntity<List<RatingBean>> getAllReviewForHotel(@PathVariable int hotelId, @QueryParam(value = "") String gender,  @QueryParam(value = "") String city) {
		List<RatingBean> ratingBeanList;
		try {
			ratingBeanList = ratingService.getAllReviews(hotelId, gender, city);
		}catch (Exception e) {
			throw e;
		}
		return new ResponseEntity<List<RatingBean>>(ratingBeanList, HttpStatus.OK);
		
	}

}
