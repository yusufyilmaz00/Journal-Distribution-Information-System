package project;

import junit.framework.TestCase;

public class TestDateInfo extends TestCase{
	DateInfo date;
	
	protected void setUp() {
		date = new DateInfo(7,2023);
	}
	
	public void testgetStartYearShouldReturn2024() {
		date.setStartYear(2024);
		assertEquals(2024,date.getStartYear());
	}
	
	public void testgetStartMonthShouldReturn7() {
		assertEquals(7,date.getStartMonth());
	}
	
	public void testgetEndMonthShouldReturn10(){
		date.setStartMonth(11);
		date.setEndMonth(10);
		assertEquals(10,date.getEndMonth());
	}
}