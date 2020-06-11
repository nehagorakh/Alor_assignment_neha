package com.alor.HM.Service;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.alor.HM.Bean.HotelRequestBean;
import com.alor.HM.Bean.HotelResponseBean;

public interface HotelService {
	
	public HotelRequestBean addHotel(HotelRequestBean hotelEntity);
	
	public void deleteHotel(int hotelId);
	
	public List<HotelResponseBean> getHotels(String city,  Integer numberOfRooms, String date, Integer wifi, Integer resturant, Integer ac, 
			Integer mealsIncluded, Integer rating) throws ParseException;
	
}
