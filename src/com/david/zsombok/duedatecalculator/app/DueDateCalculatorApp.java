package com.david.zsombok.duedatecalculator.app;

import javax.management.InvalidAttributeValueException;
import com.david.zsombok.duedatecalculator.domain.DueDateCalculator;
import com.david.zsombok.duedatecalculator.enums.Day;
import com.david.zsombok.duedatecalculator.enums.Month;
import com.david.zsombok.duedatecalculator.enums.PartOfDay;

public class DueDateCalculatorApp {

	public static void main(String[] args) throws InvalidAttributeValueException {
		
		final String ANSI_RED = "\033[0;31m";
		DueDateCalculator dueDateCalculator = new DueDateCalculator();
		String dueDate = dueDateCalculator.calculateDueDate(2001, Month.DEC, 28, Day.THU, 2, 36, PartOfDay.PM, 11);
		System.out.println(ANSI_RED + "OUTPUT: " + dueDate);
	}
	
}
