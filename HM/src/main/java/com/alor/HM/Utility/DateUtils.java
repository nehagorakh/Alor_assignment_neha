package com.alor.HM.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String getCurrentDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dateobj = new Date();
		return df.format(dateobj);
	}
	
	public static Date convertStringToDate(String date) throws ParseException {
	 return new SimpleDateFormat("yyyy-MM-dd").parse(date);  
	}
}
