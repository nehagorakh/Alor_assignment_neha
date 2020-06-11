package com.alor.HM.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alor.HM.Entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer>{

	List<UserEntity> findByIdIn(List<Integer> userIds);
}
