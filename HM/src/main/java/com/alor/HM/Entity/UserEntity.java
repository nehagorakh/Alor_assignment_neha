package com.alor.HM.Entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Entity
@Data
@Table(name = "user_account")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@DynamicUpdate
public class UserEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	String name;
	
	private String password;
	
	@CreationTimestamp
	private Timestamp createdAt;
	
	@UpdateTimestamp
	private Timestamp updatedAt;
	
    private String city;
    private String type;
    
    @Email(message = "Please provide valid Email address")
    private String email;
    
    @Column(name = "is_active")
    private int isActive;
    
    private String gender;
    
    
    public interface userType{
    	String USER = "user";
    	String HOTEL_ADMIN = "hotel_admin";
    }
   public interface gender {
	   String MALE = "male";
	   String FEMALE ="female";
   }
}
