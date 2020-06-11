package com.alor.HM.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import com.alor.HM.Entity.HotelRoomBooking;

public interface HotelRoomBookingService {

	List<HotelRoomBooking> getHotelRoomBookingRecords(Set<Integer> hotelIds, String date) throws ParseException;

}
