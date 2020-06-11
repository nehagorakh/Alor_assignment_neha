package com.alor.HM;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Demo {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
		DateFormat df = new SimpleDateFormat("dd-MM-yy");
		String da = "2-8-2020";
		Date d = df.parse(da);
		System.out.println(da);

	}

}
