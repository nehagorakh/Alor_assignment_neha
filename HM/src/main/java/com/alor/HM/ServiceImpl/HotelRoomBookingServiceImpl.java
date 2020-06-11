package com.alor.HM.ServiceImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alor.HM.Entity.HotelRoomBooking;
import com.alor.HM.Repository.HotelRoomBookingRepository;
import com.alor.HM.Service.HotelRoomBookingService;
import com.alor.HM.Utility.DateUtils;

@Service
public class HotelRoomBookingServiceImpl implements HotelRoomBookingService{
	
	@Autowired
	HotelRoomBookingRepository hotelRoomBookingRepository;

	@Override
	public List<HotelRoomBooking> getHotelRoomBookingRecords(Set<Integer> hotelIds, String date) throws ParseException {
		
		if(hotelIds == null || hotelIds.size() ==0 || date == null)
			throw new BadRequestException("Empty hotel id list or date is not provided!");
		
		List<Integer> hIds = new ArrayList<Integer>(hotelIds);
		return hotelRoomBookingRepository.findByHotelIdInAndBookingDate(hIds, DateUtils.convertStringToDate(date));
	}

}
