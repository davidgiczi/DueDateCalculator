package com.david.zsombok.duedatecalculator.domain;

import javax.management.InvalidAttributeValueException;
import com.david.zsombok.duedatecalculator.enums.Day;
import com.david.zsombok.duedatecalculator.enums.Month;
import com.david.zsombok.duedatecalculator.enums.PartOfDay;

public class DueDateCalculator {

	private Date inputDate;
	private Date outputDate;
	public static final int WORKING_HOURS = 8;
	public static final int START_OF_WORKING_DAY_HOUR= 9;
	public static final int MIDDLE_OF_WORKING_DAY_HOUR= 12;
	public static final int END_OF_WORKING_DAY_HOUR = 5;
	
	public String calculateDueDate(int inputYear, Month inputMonth, int inputDayOfMonth, 
			Day inputDayName, int inputHour, int inputMinute, PartOfDay partOfDay, int turnAroundTime) 
					throws InvalidAttributeValueException {
		
		inputDate = new Date();
		if(isValidInputDateValue(inputYear, inputMonth, inputDayOfMonth, inputDayName, 
				inputHour, inputMinute, partOfDay, turnAroundTime)) {
			System.out.println("INPUT: " + getDateAsString(inputDate) + ", TurnAround: " + inputDate.getTurnAroundTime() + " HOUR" );
			calculate();
			return getDateAsString(outputDate);
		}
		
		return "-";
	}
	
	private boolean isValidInputDateValue(int inputYear, Month inputMonth, int inputDayOfMonth, 
			Day inputDayName, int inputHour, int inputMinute, PartOfDay partOfDay, int turnAroundTime) {
		
		try {
			inputDate.setYear(inputYear);
			inputDate.setMonth(inputMonth);
			inputDate.setDayOfMonth(inputDayOfMonth);
			inputDate.setDayName(inputDayName);
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
		incrementDay();
		incrementHour();
		
		return outputDate;
	}
	
	private void incrementDay() throws InvalidAttributeValueException {
		
		int DAYS = inputDate.getTurnAroundTime() / WORKING_HOURS;
		int workingDayCounter = inputDate.getDayOfMonth();
		
		for(int i = 1; i <= DAYS; i++) {
			
			workingDayCounter++;
			
			if(workingDayCounter == Month.getDaysOfMonthByMonthName(inputDate.getYear(), inputDate.getMonth()) &&
					i != DAYS ) {
				
				inputDate.setMonth( Month.getNextMonth(inputDate.getMonth()) );
				workingDayCounter = 0;
			}
			else if(inputDate.getMonth() == Month.DEC && 
					workingDayCounter == Month.getDaysOfMonthByMonthName(inputDate.getYear(), inputDate.getMonth()) && i != DAYS ) {
				
				inputDate.setMonth( Month.getNextMonth(inputDate.getMonth()) );
				inputDate.setYear( inputDate.getYear() + 1 );
				workingDayCounter = 0;
			}
			else {
				
				inputDate.setDayName( Day.getNextWorkDay(inputDate.getDayName()) );
				inputDate.setDayOfMonth(workingDayCounter);
			}
			
		}
		
	}
	
	private void incrementHour() throws InvalidAttributeValueException {
	
		int HOURS = inputDate.getTurnAroundTime() % WORKING_HOURS == 0 ? 
				WORKING_HOURS * inputDate.getTurnAroundTime() / 8 : inputDate.getTurnAroundTime() % WORKING_HOURS;
		int hoursInMinute = 60 * HOURS + inputDate.getMinute();
		int startOfWorkingDayHourInMinute = 60 * START_OF_WORKING_DAY_HOUR;
		int middleOfWorkingDayHourInMinute = 60 * MIDDLE_OF_WORKING_DAY_HOUR;
		int endOfWorkingDayHourInMinute = 60 * END_OF_WORKING_DAY_HOUR;
		
		
		if(inputDate.getPartOfDay() == PartOfDay.AM && startOfWorkingDayHourInMinute + hoursInMinute < middleOfWorkingDayHourInMinute) {
			
			outputDate.setYear(inputDate.getYear());
			outputDate.setMonth(inputDate.getMonth());
			outputDate.setDayOfMonth(inputDate.getDayOfMonth());
			outputDate.setPartOfDay(inputDate.getPartOfDay());
			outputDate.setDayName(inputDate.getDayName());
			outputDate.setHour(START_OF_WORKING_DAY_HOUR + HOURS);
			outputDate.setMinute(inputDate.getMinute());
		}
		else if(inputDate.getPartOfDay() == PartOfDay.AM && startOfWorkingDayHourInMinute + hoursInMinute >= middleOfWorkingDayHourInMinute) {
		
			outputDate.setYear(inputDate.getYear());
			outputDate.setMonth(inputDate.getMonth());
			outputDate.setDayOfMonth(inputDate.getDayOfMonth());
			outputDate.setPartOfDay(PartOfDay.PM);
			outputDate.setDayName(inputDate.getDayName());
			outputDate.setHour(START_OF_WORKING_DAY_HOUR + HOURS - MIDDLE_OF_WORKING_DAY_HOUR == 0 ? 
					MIDDLE_OF_WORKING_DAY_HOUR : START_OF_WORKING_DAY_HOUR + HOURS - MIDDLE_OF_WORKING_DAY_HOUR);
			outputDate.setMinute(inputDate.getMinute());
		}
		else if(inputDate.getPartOfDay() == PartOfDay.PM && hoursInMinute < endOfWorkingDayHourInMinute) {
			
			outputDate.setYear(inputDate.getYear());
			outputDate.setMonth(inputDate.getMonth());
			outputDate.setDayOfMonth(inputDate.getDayOfMonth());
			outputDate.setPartOfDay(inputDate.getPartOfDay());
			outputDate.setDayName(inputDate.getDayName());
			outputDate.setHour(HOURS);
			outputDate.setMinute(inputDate.getMinute());
		}
		else if(inputDate.getPartOfDay() == PartOfDay.PM &&  hoursInMinute >= endOfWorkingDayHourInMinute) {
			
			incerementMonthOrYear();
			outputDate.setPartOfDay(START_OF_WORKING_DAY_HOUR + HOURS - END_OF_WORKING_DAY_HOUR == MIDDLE_OF_WORKING_DAY_HOUR ? PartOfDay.PM : PartOfDay.AM);
			outputDate.setDayName( Day.getNextWorkDay(inputDate.getDayName()) );
			outputDate.setHour(START_OF_WORKING_DAY_HOUR + HOURS - END_OF_WORKING_DAY_HOUR);
			outputDate.setMinute(inputDate.getMinute());
		} 
	}
	

	private void incerementMonthOrYear() throws InvalidAttributeValueException {
		
		if(inputDate.getMonth() == Month.JAN && inputDate.getDayOfMonth() == Month.getDaysOfMonthByMonthName(inputDate.getYear(), Month.JAN)) {
			outputDate.setMonth(Month.getNextMonth(Month.JAN));
			outputDate.setDayOfMonth(1);
		}
		else if(inputDate.getMonth() == Month.FEB && inputDate.getDayOfMonth() == Month.getDaysOfMonthByMonthName(inputDate.getYear(), Month.FEB)) {
			outputDate.setMonth(Month.getNextMonth(Month.FEB));
			outputDate.setDayOfMonth(1);
		}
		else if(inputDate.getMonth() == Month.MAR && inputDate.getDayOfMonth() == Month.getDaysOfMonthByMonthName(inputDate.getYear(), Month.MAR)) {
			outputDate.setMonth(Month.getNextMonth(Month.MAR));
			outputDate.setDayOfMonth(1);
		}
		else if(inputDate.getMonth() == Month.APR && inputDate.getDayOfMonth() == Month.getDaysOfMonthByMonthName(inputDate.getYear(), Month.APR)) {
			outputDate.setMonth(Month.getNextMonth(Month.APR));
			outputDate.setDayOfMonth(1);
		}
		else if(inputDate.getMonth() == Month.MAJ && inputDate.getDayOfMonth() == Month.getDaysOfMonthByMonthName(inputDate.getYear(), Month.MAJ)) {
			outputDate.setMonth(Month.getNextMonth(Month.MAJ));
			outputDate.setDayOfMonth(1);
		}
		else if(inputDate.getMonth() == Month.JUN && inputDate.getDayOfMonth() == Month.getDaysOfMonthByMonthName(inputDate.getYear(), Month.JUN)) {
			outputDate.setMonth(Month.getNextMonth(Month.JUN));
			outputDate.setDayOfMonth(1);
		}
		else if(inputDate.getMonth() == Month.JUL && inputDate.getDayOfMonth() == Month.getDaysOfMonthByMonthName(inputDate.getYear(), Month.JUL)) {
			outputDate.setMonth(Month.getNextMonth(Month.JUL));
			outputDate.setDayOfMonth(1);
		}
		else if(inputDate.getMonth() == Month.AUG && inputDate.getDayOfMonth() == Month.getDaysOfMonthByMonthName(inputDate.getYear(), Month.AUG)) {
			outputDate.setMonth(Month.getNextMonth(Month.AUG));
			outputDate.setDayOfMonth(1);
		}
		else if(inputDate.getMonth() == Month.SEP && inputDate.getDayOfMonth() == Month.getDaysOfMonthByMonthName(inputDate.getYear(), Month.SEP)) {
			outputDate.setMonth(Month.getNextMonth(Month.SEP));
			outputDate.setDayOfMonth(1);
		}
		else if(inputDate.getMonth() == Month.OCT && inputDate.getDayOfMonth() == Month.getDaysOfMonthByMonthName(inputDate.getYear(), Month.OCT)) {
			outputDate.setMonth(Month.getNextMonth(Month.OCT));
			outputDate.setDayOfMonth(1);
		}
		else if(inputDate.getMonth() == Month.NOV && inputDate.getDayOfMonth() == Month.getDaysOfMonthByMonthName(inputDate.getYear(), Month.NOV)) {
			outputDate.setMonth(Month.getNextMonth(Month.NOV));
			outputDate.setDayOfMonth(1);
		}
		else if(inputDate.getMonth() == Month.DEC && inputDate.getDayOfMonth() == Month.getDaysOfMonthByMonthName(inputDate.getYear(), Month.DEC)) {
			outputDate.setMonth(Month.getNextMonth(Month.DEC));
			outputDate.setDayOfMonth(1);
			outputDate.setYear(inputDate.getYear() + 1);
		}
		else {
			outputDate.setYear( inputDate.getYear() );
			outputDate.setMonth( inputDate.getMonth() );
		}
	}
	
	private String getDateAsString(Date date) {
	return	date.getYear() + " " + date.getMonth() + " "
				+ (date.getDayOfMonth() < 10 ? "0"+date.getDayOfMonth() : date.getDayOfMonth())
				+ " " + date.getDayName() + " " + date.getHour() + ":" 
				+ (date.getMinute() < 10 ? "0" + date.getMinute() : date.getMinute()) + date.getPartOfDay();
	}
	
}
