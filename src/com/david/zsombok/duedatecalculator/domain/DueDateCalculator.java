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
	public static final int END_OF_WORKING_DAY_IN_HOUR= 17;
	
	public String calculateDueDate(int inputYear, Month inputMonth, int inputDayOfMonth, 
			Day inputDay, int inputHour, int inputMinute, PartOfDay partOfDay, int turnAroundTime) 
					throws InvalidAttributeValueException {
		
		inputDate = new Date();
		if(isValidInputDateValue(inputYear, inputMonth, inputDayOfMonth, inputDay, 
				inputHour, inputMinute, partOfDay, turnAroundTime)) {
			System.out.println("INPUT: " + getDateAsString(inputDate) + ", TURNAROUND: " + inputDate.getTurnAroundTime() + " Hour" );
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
		int DAYS = inputDate.getTurnAroundTime() / WORKING_HOURS;
		
		for(int i = 0; i < DAYS; i++) {
			int year = inputDate.getYear();
			Month month = inputDate.getMonth();
			int dayOfMonth = inputDate.getDayOfMonth();
			incrementDay(year, month, dayOfMonth);
		}
		
		int HOURS = inputDate.getTurnAroundTime() % WORKING_HOURS;
		for(int i = 0; i < HOURS; i++) {
			addResidualHour();	
		}
		
		outputDate.setYear(inputDate.getYear());
		outputDate.setMonth(inputDate.getMonth());
		outputDate.setDayOfMonth(inputDate.getDayOfMonth());
		outputDate.setDay(inputDate.getDay());
		outputDate.setPartOfDay(inputDate.getPartOfDay());
		outputDate.setHour(inputDate.getHour());
		outputDate.setMinute(inputDate.getMinute());
		
		return outputDate;
	}
	
	private void incrementDay(int year, Month month, int dayOfMonth) throws InvalidAttributeValueException {
		
		if( inputDate.getMonth() == Month.DEC && inputDate.getDayOfMonth() == 31 && inputDate.getDay() != Day.FRI ) {
			year += 1;
		}
		else if( inputDate.getMonth() == Month.DEC && inputDate.getDayOfMonth() > 28 && inputDate.getDay() == Day.FRI ) {
			year += 1;
		}
		
		if( inputDate.getDay() == Day.FRI ) {
			dayOfMonth += 3;
		}
		else {
			dayOfMonth += 1;
		}
		
		if( dayOfMonth > Month.getDaysOfMonthByMonthName(inputDate.getYear(), inputDate.getMonth()) ) {
			month = Month.getNextMonth(inputDate.getMonth());
			dayOfMonth = dayOfMonth - Month.getDaysOfMonthByMonthName(inputDate.getYear(), inputDate.getMonth());
		}
		else {
			month = inputDate.getMonth();
		}
		inputDate.setYear(year);
		inputDate.setDayOfMonth(dayOfMonth);
		inputDate.setMonth(month);
		inputDate.setDay(Day.getNextWorkDay(inputDate.getDay()));
	}
	
	private void addResidualHour() throws InvalidAttributeValueException { 
		
		int eventTimePlusOneHourInMinutes = 60 * (inputDate.getHour() + 1) + inputDate.getMinute();
		
		if( eventTimePlusOneHourInMinutes < 60 * MIDDLE_OF_WORKING_DAY_IN_HOUR 
				&& inputDate.getPartOfDay() == PartOfDay.AM ) {
			
			inputDate.setHour(inputDate.getHour() + 1);
		}
		else if( eventTimePlusOneHourInMinutes >= 60 * MIDDLE_OF_WORKING_DAY_IN_HOUR 
				&& eventTimePlusOneHourInMinutes < 13 *60 
				&& inputDate.getPartOfDay() == PartOfDay.AM ) {
			
			inputDate.setPartOfDay(PartOfDay.PM);
			inputDate.setHour(inputDate.getHour() + 1);
		}
		else if( eventTimePlusOneHourInMinutes >= 13 * 60 
				&& eventTimePlusOneHourInMinutes < END_OF_WORKING_DAY_IN_HOUR * 60 
				&& inputDate.getPartOfDay() == PartOfDay.AM ) {
			
			inputDate.setPartOfDay(PartOfDay.PM);
			inputDate.setHour(inputDate.getHour() + 1 - MIDDLE_OF_WORKING_DAY_IN_HOUR);
		}
		else if( eventTimePlusOneHourInMinutes >= 13 * 60 
				&& eventTimePlusOneHourInMinutes < END_OF_WORKING_DAY_IN_HOUR * 60 
				&& inputDate.getPartOfDay() == PartOfDay.PM ) {
			
			inputDate.setHour(inputDate.getHour() + 1 - MIDDLE_OF_WORKING_DAY_IN_HOUR);
		}
		else if( eventTimePlusOneHourInMinutes >= 60
				&& eventTimePlusOneHourInMinutes < 5 * 60
				&& inputDate.getPartOfDay() == PartOfDay.PM ) {
			
			inputDate.setHour(inputDate.getHour() + 1);
		}
		else if( eventTimePlusOneHourInMinutes >= 60
				&& eventTimePlusOneHourInMinutes >= 5 * 60
				&& inputDate.getPartOfDay() == PartOfDay.PM ) {
				
				int year = inputDate.getYear();
				Month month = inputDate.getMonth();
				int dayOfMonth = inputDate.getDayOfMonth();
				incrementDay(year, month, dayOfMonth);
				int residualHourForTomorrow = 4 - inputDate.getHour();
				inputDate.setPartOfDay(residualHourForTomorrow < 3 ? PartOfDay.AM : PartOfDay.PM);
				inputDate.setHour(residualHourForTomorrow < 4 ? 
						START_OF_WORKING_DAY_IN_HOUR + residualHourForTomorrow : 
						START_OF_WORKING_DAY_IN_HOUR + residualHourForTomorrow - MIDDLE_OF_WORKING_DAY_IN_HOUR);
		}
	}
	
	private String getDateAsString(Date date) {
	return	date.getYear() + " " + date.getMonth() + " "
				+ (date.getDayOfMonth() < 10 ? "0"+date.getDayOfMonth() : date.getDayOfMonth())
				+ " " + date.getDay() + " " + date.getHour() + ":" 
				+ (date.getMinute() < 10 ? "0" + date.getMinute() : date.getMinute()) + date.getPartOfDay();
	}
	
}
