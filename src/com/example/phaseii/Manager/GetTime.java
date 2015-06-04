package com.example.phaseii.Manager;

import java.util.Calendar;

/**
 * @author Tony, Andrew, Joseph, Kim, Dante
 * Get the current time, which includes the year
 * the month, the date, and the time.
 */
public class GetTime {

	private Calendar cal;
	
	/**
	 * Constructs a GetTime with all null fields.
	 */
	public GetTime(){
		cal = Calendar.getInstance();

	}
	
	/**
	 * Get today's date.
	 * @return today's date in year/month/date.
	 */	
	public String getTodayDate(){
		int monthNow = cal.get(Calendar.MONTH) + 1;
        return(cal.get(Calendar.YEAR) +"/"+monthNow+"/"+cal.get(Calendar.DATE));
        
	}
	
	/**
	 * Get the current time.
	 * @return the current time, in hour/minute/second
	 */
	public String getNowTime(){
		
		return(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND));
	}
	
	/**
	 * Get the current year from the calendar.
	 * @return the current year.
	 */
	public int getYear(){
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * Get the current month from the calendar.
	 * @return the current month.
	 */
	public int getMonth(){
		return cal.get(Calendar.MONTH)+1;
	}
	
	/**
	 * Get the current date from the calendar.
	 * @return the current date.
	 */
	public int getDay(){
		return cal.get(Calendar.DATE);
	}
	
	public String welcome(){

		String hello;
		if ((cal.get(Calendar.HOUR) > 3) && (cal.get(Calendar.HOUR) < 12) && (cal.get(Calendar.AM_PM) == 0)){
			hello = "Good Morning!";			
		}else if((cal.get(Calendar.HOUR) > 0) && (cal.get(Calendar.HOUR) < 6) && (cal.get(Calendar.AM_PM) == 1)){
			hello = "Good Afternoon!";
			
		}else{
			hello = "Good Evening!";
		}
		return hello;
	}
	
	/**
	 * Get a patient's arrival time.
	 * @return today's date and the current time.
	 */
	public String getArrivalTime(){
		return (getTodayDate()+" "+getNowTime());
	}
}
