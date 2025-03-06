package project;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DateInfo implements Serializable{
	private int startMonth;
	private int endMonth;
	private int startYear;
	private int endYear;
	
	public DateInfo(int startMonth, int startYear) {
		this.startMonth = startMonth;
		this.startYear = startYear;
		if(startMonth==1) {
			endMonth = 12;
			endYear= startYear;
		}else {
			endMonth = startMonth-1;
			endYear = startYear+1;
		}
		
	}
	//getter & setter methods
	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	
	
}
