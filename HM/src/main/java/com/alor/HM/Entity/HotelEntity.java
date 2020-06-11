package com.alor.HM.Entity;


import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Entity
@Data
@Table(name = "hotel")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@DynamicUpdate
public class HotelEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	String name;

	@CreationTimestamp
	private Timestamp createdAt;

	@UpdateTimestamp
	private Timestamp updatedAt;

	private int createdBy;
	private int updatedBy;
	private int numberOfRooms;
	private String city;
	private long costOfStay;

	@Column(name = "is_active")
	private int isActive;

}
