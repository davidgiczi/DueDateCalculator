package com.david.zsombok.duedatecalculator.domain;

import javax.management.InvalidAttributeValueException;
import com.david.zsombok.duedatecalculator.enums.Day;
import com.david.zsombok.duedatecalculator.enums.Month;
import com.david.zsombok.duedatecalculator.enums.PartOfDay;

public class DueDateCalculator {

	private Date inputDate;
	private Date outputDate;
	public static final int WORKING_HOURS = 8;
	public static final int START_OF_WORKING_DAY = 9;
	public static final int MIDDLE_OF_WORKING_DAY = 12;
	public static final int END_OF_WORKING_DAY = 5;
	
	public String calculateDueDate(int inputYear, Month inputMonth, int inputDayOfMonth, 
			Day inputDayName, int inputHour, int inputMinute, PartOfDay partOfDay, int turnAroundTime) 
					throws InvalidAttributeValueException {
		
		inputDate = new Date();
		if(isValidInputValue(inputYear, inputMonth, inputDayOfMonth, inputDayName, 
				inputHour, inputMinute, partOfDay, turnAroundTime)) {
			System.out.println(getDateAsString(inputDate));
			calculate();
			return getDateAsString(outputDate);
		}
		
		return "";
	}
	
	private boolean isValidInputValue(int inputYear, Month inputMonth, int inputDayOfMonth, 
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
		incrementHour();
		
		return outputDate;
	}
	
	private void incrementHour() throws InvalidAttributeValueException {
	
		int HOURS = inputDate.getTurnAroundTime() % WORKING_HOURS;
		int hoursInMinute = 60 * HOURS + inputDate.getMinute();
		int startOfWorkingDayInMinute = 60 * START_OF_WORKING_DAY;
		int middleOfWorkingDayInMinute = 60 * MIDDLE_OF_WORKING_DAY;
		int endOfWorkingDayInMinute = 60 * END_OF_WORKING_DAY;
		
		outputDate.setYear(inputDate.getYear());
		outputDate.setMonth(inputDate.getMonth());
		
		if(inputDate.getPartOfDay() == PartOfDay.AM && middleOfWorkingDayInMinute - startOfWorkingDayInMinute - hoursInMinute >= 0) {
			
			outputDate.setDayOfMonth(inputDate.getDayOfMonth());
			outputDate.setDayName(inputDate.getDayName());
			outputDate.setPartOfDay(inputDate.getPartOfDay());
			outputDate.setHour(START_OF_WORKING_DAY + HOURS);
			outputDate.setMinute(inputDate.getMinute());
		}
		else if(inputDate.getPartOfDay() == PartOfDay.AM && endOfWorkingDayInMinute - hoursInMinute < 0) {
			
			outputDate.setDayOfMonth(inputDate.getDayOfMonth());
			outputDate.setDayName(inputDate.getDayName());
			outputDate.setPartOfDay(PartOfDay.PM);
			outputDate.setHour(START_OF_WORKING_DAY - MIDDLE_OF_WORKING_DAY + HOURS);
			outputDate.setMinute(inputDate.getMinute());	
		}
		else if(inputDate.getPartOfDay() == PartOfDay.PM && endOfWorkingDayInMinute - hoursInMinute > 0) {
			
			outputDate.setDayOfMonth(inputDate.getDayOfMonth());
			outputDate.setDayName(inputDate.getDayName());
			outputDate.setPartOfDay(PartOfDay.PM);
			//outputDate.setHour();
			outputDate.setMinute(inputDate.getMinute());	
		}
		

	}
	

	private String getDateAsString(Date date) {
	return	date.getYear() + " " + date.getMonth() + " "
				+ (date.getDayOfMonth() < 10 ? "0"+date.getDayOfMonth() : date.getDayOfMonth())
				+ " " + date.getDayName() + " " + date.getHour() + ":" 
				+ (date.getMinute() < 10 ? "0" + date.getMinute() : date.getMinute()) + date.getPartOfDay();
	}
	
}
