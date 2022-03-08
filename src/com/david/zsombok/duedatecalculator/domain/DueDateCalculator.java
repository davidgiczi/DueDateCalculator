package com.david.zsombok.duedatecalculator.domain;

import javax.management.InvalidAttributeValueException;

import com.david.zsombok.duedatecalculator.enums.Day;
import com.david.zsombok.duedatecalculator.enums.Month;
import com.david.zsombok.duedatecalculator.enums.PartOfDay;

public class DueDateCalculator {

	private int inputYear;
	private Month inputMonth;
	private int inputDayOfMonth;
	private Day inputDayName;
	private int inputHour;
	private int inputMinute;
	private PartOfDay partOfDay;
	private int turnAroundTime;
	
	
	public String calculateDueDate(int inputYear, Month inputMonth, int inputDayOfMonth, 
			Day inputDayName, int inputHour, int inputMinute, PartOfDay partOfDay, int turnAroundTime) {
		
		if(isValidInputValue(inputYear, inputMonth, inputDayOfMonth, inputDayName, 
				inputHour, inputMinute, partOfDay, turnAroundTime)) {
			return calculate(inputYear, inputMonth, inputDayOfMonth, inputDayName, 
					inputHour, inputMinute, partOfDay, turnAroundTime);
		}
		
		return "";
	}
	
	private boolean isValidInputValue(int inputYear, Month inputMonth, int inputDayOfMonth, 
			Day inputDayName, int inputHour, int inputMinute, PartOfDay partOfDay, int turnAroundTime) {
		
		try {
			setInputYear(inputYear);
			setInputMonth(inputMonth);
			setInputDayOfMonth(inputDayOfMonth);
			setInputDayName(inputDayName);
			setInputMinute(inputMinute);
			setPartOfDay(partOfDay);
			setInputHour(inputHour);
			setTurnAroundTime(turnAroundTime);
			
		} catch (InvalidAttributeValueException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	private String calculate(int inputYear, Month inputMonth, int inputDayOfMonth, 
			Day inputDayName, int inputHour, int inputMinute, PartOfDay partOfDay, int turnAroundTime) {
		
		
		
		return inputYear + " " + inputMonth + " "
		+ (inputDayOfMonth < 10 ? "0"+inputDayOfMonth : inputDayOfMonth)
		+ " " + inputDayName + " " + inputHour + ":" 
		+ (inputMinute < 10 ? "0" + inputMinute : inputMinute) + partOfDay;
	}
	
	
	
	
	public int getInputYear() {
		return inputYear;
	}
	public void setInputYear(int inputYear) throws InvalidAttributeValueException {
		
		if(Year.isValidYear(inputYear)) {
		this.inputYear = inputYear;
	}
		else {
			throw new InvalidAttributeValueException("The input Year value is not a valid value: " + inputYear 
					+ ", should be: 0 < Year value");
		}
	}
	
	public Month getInputMonth() {
		return inputMonth;
	}
	public void setInputMonth(Month inputMonth) {
		this.inputMonth = inputMonth;
	}
	
	public int getInputDayOfMonth() {
		return inputDayOfMonth;
	}

	public void setInputDayOfMonth(int inputDayOfMonth) throws InvalidAttributeValueException {
		
		if(Month.isValidDayOfMonth(inputYear, inputMonth, inputDayOfMonth)) {
			this.inputDayOfMonth = inputDayOfMonth;
		}
		else {
			throw new InvalidAttributeValueException("The input Day of Month value is not a valid value: " + inputDayOfMonth 
					+ ", should be: 0 < Day of Month value <= " + Month.getDaysOfMonthByMonthName(inputYear, inputMonth));
		}
		
	}

	public Day getInputDayName() {
		return inputDayName;
	}


	public void setInputDayName(Day inputDayName) {
		this.inputDayName = inputDayName;
	}
	
	public int getInputHour() {
		return inputHour;
	}
	public void setInputHour(int inputHour) throws InvalidAttributeValueException {
		
		if(HourMinute.isValidHour(inputHour) && HourMinute.isValidWorkingHour(inputHour,  inputMinute, partOfDay)) {
			this.inputHour = inputHour;
		}
		else {
			throw new InvalidAttributeValueException("The input Hour value is not a valid value: " + inputHour + partOfDay 
					+ ", should be: 8AM <= Hour value <= 5PM" );
		}
	}
	public int getInputMinute() {
		return inputMinute;
	}
	public void setInputMinute(int inputMinute) throws InvalidAttributeValueException {
		
		if(HourMinute.isValidMinute(inputMinute)) {
			this.inputMinute = inputMinute;
		}
		else {
			throw new InvalidAttributeValueException("The input Minute value is not a valid value: " + inputMinute + 
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
			throw new InvalidAttributeValueException("The input Turnaround Time value is not a valid value: " + turnAroundTime + 
					", should be: 0 < Turnaround value");
		}
		
	}
	
}
