package com.david.zsombok.duedatecalculator.domain;

import javax.management.InvalidAttributeValueException;

import com.david.zsombok.duedatecalculator.enums.Day;
import com.david.zsombok.duedatecalculator.enums.Month;
import com.david.zsombok.duedatecalculator.enums.PartOfDay;

public class Date {

	private int year;
	private Month month;
	private int dayOfMonth;
	private Day dayName;
	private int hour;
	private int minute;
	private PartOfDay partOfDay;
	private int turnAroundTime;
	private boolean nextDay;
	
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) throws InvalidAttributeValueException {
		
		if(Year.isValidYear(year)) {
		this.year = year;
	}
		else {
			throw new InvalidAttributeValueException("The Year value is not a valid value: " + year 
					+ ", should be: 0 < Year value");
		}
	}
	
	public Month getMonth() {
		return month;
	}
	public void setMonth(Month month) {
		this.month = month;
	}
	
	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(int dayOfMonth) throws InvalidAttributeValueException {
		
		if(Month.isValidDayOfMonth(year, month, dayOfMonth)) {
			this.dayOfMonth = dayOfMonth;
		}
		else {
			throw new InvalidAttributeValueException("The Day of Month value is not a valid value: " + dayOfMonth 
					+ ", should be: 0 < Day of Month value <= " + Month.getDaysOfMonthByMonthName(year, month));
		}
		
	}

	public Day getDayName() {
		return dayName;
	}


	public void setDayName(Day dayName) {
		this.dayName = dayName;
	}
	
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) throws InvalidAttributeValueException {
		
		if(HourMinute.isValidHour(hour) && HourMinute.isValidWorkingHour(hour,  minute, partOfDay)) {
			this.hour = hour;
		}
		else {
			throw new InvalidAttributeValueException("The Hour value is not a valid value: " + hour + partOfDay 
					+ ", should be: 8AM <= Hour value < 5PM" );
		}
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) throws InvalidAttributeValueException {
		
		if(HourMinute.isValidMinute(minute)) {
			this.minute = minute;
		}
		else {
			throw new InvalidAttributeValueException("The Minute value is not a valid value: " + minute + 
					", should be: 0 <= Minute value <= 59");
		}
	}
	
	public PartOfDay getPartOfDay() {
		return partOfDay;
	}


	public void setPartOfDay(PartOfDay partOfDay) {
		this.partOfDay = partOfDay;
	}


	public int getTurnAroundTime() {
		return turnAroundTime;
	}
	public void setTurnAroundTime(int turnAroundTime) throws InvalidAttributeValueException {
		
		if( turnAroundTime > 0 ) {
			this.turnAroundTime = turnAroundTime;
		}
		else {
			throw new InvalidAttributeValueException("The Turnaround Time value is not a valid value: " + turnAroundTime + 
					", should be: 0 < Turnaround value");
		}
		
	}
	public boolean isNextDay() {
		return nextDay;
	}
	public void setNextDay(boolean nextDay) {
		this.nextDay = nextDay;
	}
	
}
