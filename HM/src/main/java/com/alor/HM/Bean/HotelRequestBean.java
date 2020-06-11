package com.alor.HM.Bean;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HotelRequestBean {
		
	// fields related to hotel table
	private String name;
    private int createdBy;
    private int updatedBy;    
    private int numberOfRooms;
    private String city;
    private int isActive;
    
    //fields related to hotel faclity table
    private int wifi;
    private int resturant;
    private int ac;
    private int MealsIncluded;
    private long costOfStay;

}
