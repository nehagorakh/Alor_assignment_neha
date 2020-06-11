package com.alor.HM.ServiceImpl;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alor.HM.Bean.RatingBean;
import com.alor.HM.Entity.RatingEntity;
import com.alor.HM.Entity.UserEntity;
import com.alor.HM.Repository.RatingRepository;
import com.alor.HM.Service.RatingService;
import com.alor.HM.Service.UserService;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class RatingServiceImpl implements RatingService{
	
	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired 
	UserService userService;

	@Override
	public RatingEntity addReview(RatingEntity ratingEntity) {
		
		if(ratingEntity.getUserId() == 0 || ratingEntity.getHotelId() ==0)
			throw new BadRequestException("hotel id and user id can't be 0");

		return ratingRepository.save(ratingEntity);
	}

	@Override
	public void deleteUser(int ratingId) {
	
		if(ratingId <= 0 )
			throw new BadRequestException("Rating Id can not be zero");

		try {
			ratingRepository.deleteById(ratingId);
			
		}catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public List<RatingBean> getAllReviews(int hotelId, String gender, String city) {
		
		if(hotelId <= 0)  
			throw new BadRequestException();
		try {
		List<RatingEntity> ratingList = ratingRepository.findByHotelId(hotelId);
			
		if(ratingList == null || ratingList.size() == 0)
			return new ArrayList<RatingBean>(); 
		
		List<Integer> userIds = ratingList.stream().map(rating->rating.getUserId()).collect(Collectors.toList());
		List<UserEntity> users = userService.getUserByIdIn(userIds);
		
		List<RatingBean> ratingBeanList = new ArrayList<RatingBean>();
		
		ratingList.stream().forEach(rating->{
			RatingBean ratingBean = new RatingBean();
			BeanUtils.copyProperties(rating, ratingBean);
			UserEntity user = users.stream().filter(u -> u.getId() == ratingBean.getUserId()).collect(Collectors.toList()).get(0);
			BeanUtils.copyProperties(user, ratingBean);
			ratingBeanList.add(ratingBean);
		});
		if((gender == null || gender.equalsIgnoreCase("") || (!gender.equalsIgnoreCase(UserEntity.gender.MALE)) || (!gender.equalsIgnoreCase(UserEntity.gender.MALE))
				) && (city == null || city.equalsIgnoreCase(""))) 		
		return ratingBeanList;
		
		if(gender != null && !gender.equalsIgnoreCase("") && (!gender.equalsIgnoreCase(UserEntity.gender.MALE)) || (!gender.equalsIgnoreCase(UserEntity.gender.FEMALE)))
			return ratingBeanList.stream().filter(rating->rating.getGender().equalsIgnoreCase(gender)).collect(Collectors.toList());
		
		if(city != null && !city.equalsIgnoreCase(""))
			return ratingBeanList.stream().filter(rating->rating.getCity().equalsIgnoreCase(city)).collect(Collectors.toList());
		
		}catch (Exception e) {
			throw e;
		}
		return null;
		
	}

	@Override
	public List<RatingEntity> getRatingsByHotelIdsIn(Set<Integer> hotelIds) {
		
		if(hotelIds == null || hotelIds.size() <= 0)
			throw new BadRequestException("Hotel id list can not be null !!");
		
		return ratingRepository.findByHotelIdIn(new ArrayList<>(hotelIds));
	}



}
