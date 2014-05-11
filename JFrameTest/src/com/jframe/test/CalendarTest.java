package com.jframe.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar canlendar = new GregorianCalendar();
		System.out.println("Curent time is "+ new Date());
		System.out.println("Year:\t "+canlendar.get(Calendar.YEAR));
		System.out.println("Month:\t "+canlendar.get(Calendar.MONDAY));
		System.out.println("Date:\t "+canlendar.get(Calendar.DATE));
		System.out.println("Hour\t "+canlendar.get(Calendar.HOUR));
		System.out.println("Hour_OF_DAY:\t "+canlendar.get(Calendar.HOUR_OF_DAY));
		System.out.println("MINUTE:\t "+canlendar.get(Calendar.MINUTE));
		System.out.println("SECOND:\t "+canlendar.get(Calendar.SECOND));
		System.out.println("DAY_OF_WEEK:\t "+canlendar.get(Calendar.DAY_OF_WEEK));
		System.out.println("DAY_OF_MONTH:\t "+canlendar.get(Calendar.DAY_OF_MONTH));
		System.out.println("DAY_OF_YEAR:\t "+canlendar.get(Calendar.DAY_OF_YEAR));
		System.out.println("WEEK_OF_MONTH:\t "+canlendar.get(Calendar.WEEK_OF_MONTH));
		System.out.println("WEEK_OF_YEAR:\t "+canlendar.get(Calendar.WEEK_OF_YEAR));
		System.out.println("AM_PM:\t "+canlendar.get(Calendar.AM_PM));
		
		
	}

}
