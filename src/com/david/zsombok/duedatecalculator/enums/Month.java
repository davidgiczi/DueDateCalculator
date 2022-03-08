package com.david.zsombok.duedatecalculator.enums;

import com.david.zsombok.duedatecalculator.domain.Year;

public enum Month {

	JAN(31),
	FEB(28),
	MAR(31),
	APR(30),
	MAJ(31),
	JUN(30),
	JUL(31),
	AUG(31),
	SEP(30),
	OCT(31),
	NOV(30),
	DEC(31);
	
	private final int daysNumberOfMonth;
	
	private Month(int numberOfDays) {
		this.daysNumberOfMonth = numberOfDays;
	}
	
	public static int getDaysOfMonthByMonthName(int yearValue, Month name) {
		if(Year.isLeapYear(yearValue) && name == Month.FEB) {
			return 29;
		}
		return name.daysNumberOfMonth;
	}
	
	public static boolean isValidDayOfMonth(int yearValue, Month month, int dayOfMonth) {
		
		return dayOfMonth > 0 && getDaysOfMonthByMonthName(yearValue, month) >= dayOfMonth;
	}
	
	public static Month getNextMonth(Month name) {
		
		switch (name) {
		case JAN:
			return Month.FEB;
		case FEB:
			return Month.MAR;
		case MAR:
			return Month.APR;
		case APR:
			return Month.MAJ;
		case MAJ:
			return Month.JUN;
		case JUN:
			return Month.JUL;
		case JUL:
			return Month.AUG;
		case AUG:
			return Month.SEP;
		case SEP:
			return Month.OCT;
		case OCT:
			return Month.NOV;
		case NOV:
			return Month.DEC;
		default:
			return Month.JAN;
	}
}
}