package com.david.zsombok.duedatecalculator.domain;

public class Year {

	public static boolean isValidYear(int yearValue) {
		return yearValue > 0;
	}
	
	public static boolean isLeapYear(int yearValue) {
		return (yearValue > 1582 && yearValue % 400 == 0) || (yearValue > 1582 && yearValue % 4 == 0 && yearValue % 100 != 0);
	}
	
}