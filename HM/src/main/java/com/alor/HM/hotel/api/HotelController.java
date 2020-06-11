package com.alor.HM.hotel.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alor.HM.Bean.HotelRequestBean;
import com.alor.HM.Bean.HotelResponseBean;
import com.alor.HM.Service.HotelService;

@RequestMapping("/hotel")
@RestController
public class HotelController {

	@Autowired
	HotelService hotelService;

	/**
	 * to add hotel
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<HotelRequestBean> addHotel(@RequestBody HotelRequestBean hotelRequestBean) {
		return new ResponseEntity<HotelRequestBean>(hotelService.addHotel(hotelRequestBean), HttpStatus.OK);
	}

	@RequestMapping(value = "/{hotelId}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteHotel(@PathVariable int hotelId) {
		try {
			hotelService.deleteHotel(hotelId);
		} catch (Exception e) {
			throw e;
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<HotelResponseBean>> getAllHotels(@QueryParam(value = "") String city,
			@QueryParam(value = "") Integer numberOfRooms, @QueryParam(value = "") Integer rating,
			@QueryParam(value = "") String date, @QueryParam(value = "") Integer wifi,
			@QueryParam(value = "") Integer resturant, @QueryParam(value = "") Integer ac,
			@QueryParam(value = "") Integer mealsIncluded) throws ParseException {

		try {
			return new ResponseEntity<List<HotelResponseBean>>(
					hotelService.getHotels(city, numberOfRooms, date, wifi, resturant, ac, mealsIncluded, rating),
					HttpStatus.OK);

		} catch (Exception e) {
			throw e;
		}
	}

}
