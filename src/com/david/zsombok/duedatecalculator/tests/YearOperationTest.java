package com.david.zsombok.duedatecalculator.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import com.david.zsombok.duedatecalculator.domain.Year;


public class YearOperationTest {

	
	@Test
	void isLeapYearTest1(){
		assertFalse("The year value must be higher than 1582", Year.isLeapYear(1582));
	}
	
	@Test
	void isLeapYearTest2(){
		assertTrue("The year value must be higher than 1582", Year.isLeapYear(1584));
	}

	@Test
	void isLeapYearTest3(){
		assertFalse("The year value cannot be divisible by 100", Year.isLeapYear(1900));
	}
	
	@Test
	void isLeapYearTest4(){
		assertTrue("The year value must be higher than 1582 and it can be divisible by 4 and cannot be divisible by 100", Year.isLeapYear(1904));
	}
	
	@Test
	void isLeapYearTest5(){
		assertTrue("The year value must be higher than 1582 and it can be divisible by 4 and cannot be divisible by 100", Year.isLeapYear(1996));
	}
	
	@Test
	void isLeapYearTest6(){
		assertTrue("The year value must be higher than 1582 and it can be divisible by 400", Year.isLeapYear(2000));
	}
	
	@Test
	void isLeapYearTest7(){
		assertTrue("The year value must be higher than 1582 and it can be divisible by 4 and cannot be divisible by 100", Year.isLeapYear(2004));
	}
	
}

