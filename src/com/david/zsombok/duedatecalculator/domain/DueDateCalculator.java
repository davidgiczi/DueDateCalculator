package com.david.zsombok.duedatecalculator.domain;

import javax.management.InvalidAttributeValueException;
import com.david.zsombok.duedatecalculator.enums.Day;
import com.david.zsombok.duedatecalculator.enums.Month;
import com.david.zsombok.duedatecalculator.enums.PartOfDay;

public class DueDateCalculator {

	private Date inputDate;
	private Date outputDate;
	public static final int WORKING_HOURS = 8;
	public static final int START_OF_WORKING_DAY_IN_HOUR= 9;
	public static final int MIDDLE_OF_WORKING_DAY_IN_HOUR= 12;
	public static final int END_OF_WORKING_DAY_IN_HOUR = 17;
	public static final int ONE_DAY_IN_HOUR = 24;
	
	public String calculateDueDate(int inputYear, Month inputMonth, int inputDayOfMonth, 
			Day inputDay, int inputHour, int inputMinute, PartOfDay partOfDay, int turnAroundTime) 
					throws InvalidAttributeValueException {
		
		inputDate = new Date();
		if(isValidInputDateValue(inputYear, inputMonth, inputDayOfMonth, inputDay, 
				inputHour, inputMinute, partOfDay, turnAroundTime)) {
			System.out.println("INPUT: " + getDateAsString(inputDate) + ", TurnAround: " + inputDate.getTurnAroundTime() + " HOUR" );
			calculate();
			return getDateAsString(outputDate);
		}
		
		return "-";
	}
	
	private boolean isValidInputDateValue(int inputYear, Month inputMonth, int inputDayOfMonth, 
			Day inputDay, int inputHour, int inputMinute, PartOfDay partOfDay, int turnAroundTime) {
		
		try {
			inputDate.setYear(inputYear);
			inputDate.setMonth(inputMonth);
			inputDate.setDayOfMonth(inputDayOfMonth);
			inputDate.setDay(inputDay);
			inputDate.setMinute(inputMinute);
			inputDate.setPartOfDay(partOfDay);
			inputDate.setHour(inputHour);
			inputDate.setTurnAroundTime(turnAroundTime);
			
		} catch (InvalidAttributeValueException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	private Date calculate() throws InvalidAttributeValueException {
		
		outputDate = new Date();
		//incrementDay();
		addResidualHour();
		
		return outputDate;
	}
	
	private void incrementDay() throws InvalidAttributeValueException {
		
		int DAYS = inputDate.getTurnAroundTime() / WORKING_HOURS;
		int dayOfMonthCounter = inputDate.getDayOfMonth();
		
		for(int i = 1; i <= DAYS; i++) {
			
				
		}
		
	}
	
	private void addResidualHour() throws InvalidAttributeValueException {
	
		int HOURS = inputDate.getTurnAroundTime() % WORKING_HOURS;
		int hourCounter = inputDate.getHour();
		
		for(int i = 1; i <= HOURS; i++) {
			
			
			
		}
	}
	
	private String getDateAsString(Date date) {
	return	date.getYear() + " " + date.getMonth() + " "
				+ (date.getDayOfMonth() < 10 ? "0"+date.getDayOfMonth() : date.getDayOfMonth())
				+ " " + date.getDay() + " " + date.getHour() + ":" 
				+ (date.getMinute() < 10 ? "0" + date.getMinute() : date.getMinute()) + date.getPartOfDay();
	}
	
}
