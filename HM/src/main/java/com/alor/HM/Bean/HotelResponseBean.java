package com.alor.HM.Bean;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HotelResponseBean {
	
	private int hotelId;
	private String name;
	private int rating;
	private String city;
	private String costOfRoom;
	private int numberOfRooms;
	
	private int wifi;
	private int resturant;
	private int ac;
	private int mealsIncluded;
	private int availableRooms;
	private int averageRating;
	

}
