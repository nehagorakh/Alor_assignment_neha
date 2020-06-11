package com.alor.HM.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Entity
@Data
@Table(name = "hotel_facility")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@DynamicUpdate
public class HotelFaclity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	
	private int hotelId; 
    private int wifi;
    private int resturant;
    private int ac;
    private int mealsIncluded;

}
