package com.alor.HM.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.alor.HM.Entity.HotelRoomBooking;

public interface HotelRoomBookingRepository extends CrudRepository<HotelRoomBooking, Integer>{

	List<HotelRoomBooking> findByHotelIdInAndBookingDate(List<Integer> hotelIds, Date date);

}
