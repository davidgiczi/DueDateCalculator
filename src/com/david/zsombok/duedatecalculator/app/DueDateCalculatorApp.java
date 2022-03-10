package com.david.zsombok.duedatecalculator.app;

import javax.management.InvalidAttributeValueException;

import com.david.zsombok.duedatecalculator.domain.DueDateCalculator;
import com.david.zsombok.duedatecalculator.enums.Day;
import com.david.zsombok.duedatecalculator.enums.Month;
import com.david.zsombok.duedatecalculator.enums.PartOfDay;

public class DueDateCalculatorApp {

	public static void main(String[] args) throws InvalidAttributeValueException {
	
		DueDateCalculator dueDateCalculator = new DueDateCalculator();
		String dueDate = dueDateCalculator.calculateDueDate(2022, Month.JAN, 3, Day.MON, 9, 0, PartOfDay.AM, 1117);
		System.out.println("OUTPUT: " + dueDate);
	}
	
}
