package com.david.zsombok.duedatecalculator.domain;

public class ParseTurnAroundTime {
	
	private int turnAroundTime;
	private int plusHours;
	private int plusDays;
	private int plusMonth;
	private int plusYear;
	private static final int WORKING_HOURS = 8;
	
	
	public ParseTurnAroundTime(int turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}
	
	
	public int getPlusHours() {
		return plusHours;
	}
	public int getPlusDays() {
		return plusDays;
	}
	public int getPlusMonth() {
		return plusMonth;
	}
	public int getPlusYear() {
		return plusYear;
	}
	
	public void parse() {
		
		plusHours = turnAroundTime % WORKING_HOURS;
		plusDays = turnAroundTime / WORKING_HOURS;
	
	}
}
