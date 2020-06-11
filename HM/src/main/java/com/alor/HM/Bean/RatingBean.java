package com.alor.HM.Bean;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RatingBean {
	
private int hotelId;
	
    private int id;
	private int userId;	
	private Timestamp createdAt;	
	private Timestamp updatedAt;	
    private String rating;	
    private String comments;
    
    private String name;
    private String city;
    private String gender;
    private String email;

}
