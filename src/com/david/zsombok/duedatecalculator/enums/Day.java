package com.david.zsombok.duedatecalculator.enums;

public enum Day {

	MON(1),
	TUE(2),
	WED(3),
	THU(4),
	FRI(5),
	SAT(6),
	SUN(7);
	
	private final int index;
	private final static Day[] days = Day.values();
	
	private Day(int index) {
	 this.index = index;
	}
	
	public static int getDayIndexByDayName(Day name) {
		return name.index;
	}
	
	public static Day getDayNameByIndex(int index) {
		return days[index - 1];
	}
	
public static Day getNextWorkDay(Day name) {
		
		switch (name) {
		case MON:
			return Day.TUE;
		case TUE:
			return Day.WED;
		case WED:
			return Day.THU;
		case THU:
			return Day.FRI;
		default:
			return Day.MON;
	}
}
}
