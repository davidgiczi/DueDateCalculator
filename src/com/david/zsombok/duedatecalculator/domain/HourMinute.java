package com.david.zsombok.duedatecalculator.domain;

import com.david.zsombok.duedatecalculator.enums.PartOfDay;

public class HourMinute {

	
	public static boolean isValidWorkingHour(int hourValue, int minuteValue, PartOfDay partOfDay) {
		
		if(hourValue >= DueDateCalculator.START_OF_WORKING_DAY_HOUR &&  
				DueDateCalculator.MIDDLE_OF_WORKING_DAY_HOUR > hourValue && partOfDay == PartOfDay.AM) {
			return true;
		}
		else if(hourValue == DueDateCalculator.MIDDLE_OF_WORKING_DAY_HOUR && 
				isValidMinute(minuteValue) && partOfDay == PartOfDay.PM){
			return true;
		}
		else if(hourValue > 0 &&  DueDateCalculator.END_OF_WORKING_DAY_HOUR > hourValue && partOfDay == PartOfDay.PM) {
			return true;
		}
		else if(hourValue == DueDateCalculator.END_OF_WORKING_DAY_HOUR &&  minuteValue == 0 && partOfDay == PartOfDay.PM) {
			return false;
		}
		
		return false;
	}
	
	public static boolean isValidMinute(int minute) {
		return minute >= 0 &&  59 >= minute;
	}
	
	public static boolean isValidHour(int hour) {
		return hour >= 0 && 12 >= hour;
	}
}
