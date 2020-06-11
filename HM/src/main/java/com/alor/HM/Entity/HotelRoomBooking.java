package com.alor.HM.Entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Entity
@Data
@Table(name = "hotel_room_booking")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@DynamicUpdate
public class HotelRoomBooking {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		
	@CreationTimestamp
	private Timestamp date;
	
	private int hotelId;
	
    private int userId;
    private int numberOfRooms;    
    
    private Date bookingDate;
}
