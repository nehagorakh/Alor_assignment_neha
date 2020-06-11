package com.alor.HM.Service;

import java.util.List;

import com.alor.HM.Entity.UserEntity;

public interface UserService {
	
	public UserEntity addUser(UserEntity userEntity);

	public void deleteUser(int userId);

	public List<UserEntity> getUserByIdIn(List<Integer> userIds);
}
