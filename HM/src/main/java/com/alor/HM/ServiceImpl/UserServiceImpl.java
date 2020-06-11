package com.alor.HM.ServiceImpl;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alor.HM.Entity.UserEntity;
import com.alor.HM.Repository.UserRepository;
import com.alor.HM.Service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserEntity addUser(UserEntity userEntity) {		
		try {
		userEntity = userRepository.save(userEntity);
		}catch (ConstraintViolationException e) {
			throw e;
		}		
		return userEntity;
	}

	@Override
	public void deleteUser(int userId) {
	
		if(userId <= 0)
			throw new BadRequestException("userId id can not be zero");
		try {
		UserEntity userEntity = userRepository.findById(userId).get();
		if(userEntity != null) {
			userEntity.setIsActive(0);
			userRepository.save(userEntity);
		}
		
		}catch (Exception e) { 
			throw e;
		}
		
	}

	@Override
	public List<UserEntity> getUserByIdIn(List<Integer> userIds) {
		// TODO Auto-generated method stub
		if(userIds == null || userIds.size() == 0)
			throw new BadRequestException("Empty List provided");
		try {
		return userRepository.findByIdIn(userIds);
		}catch (Exception e) {
			throw e;
		}
	}	

}
