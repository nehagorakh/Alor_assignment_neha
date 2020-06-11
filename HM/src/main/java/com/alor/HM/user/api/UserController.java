package com.alor.HM.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alor.HM.Entity.UserEntity;
import com.alor.HM.Service.UserService;

@RequestMapping("/user")
@RestController
public class UserController {
	
	@Autowired
	UserService userService;
			
	/**
	 * to Add user
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity userEntity) {
		try {
			userEntity= userService.addUser(userEntity);
		}catch (Exception e) {
			throw e;
		}
		return new ResponseEntity<UserEntity>(userEntity, HttpStatus.OK);
	}
	
	/**
	 * to delete user
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteHotel(@PathVariable int userId) {
		try {
			userService.deleteUser(userId);
		}catch (Exception e) {
			throw e;
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	

}
