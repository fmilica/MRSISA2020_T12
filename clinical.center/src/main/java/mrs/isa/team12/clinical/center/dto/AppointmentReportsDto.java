package mrs.isa.team12.clinical.center.dto;

import java.util.ArrayList;

public class AppointmentReportsDto {
	
	private ArrayList<String> dates;
	private ArrayList<Integer> count;
	
	public AppointmentReportsDto() {}
	
	public AppointmentReportsDto(ArrayList<String> dates, ArrayList<Integer> count) {
		this.dates = dates;
		this.count = count;
	}

	public ArrayList<String> getDates() {
		return dates;
	}
	public void setDates(ArrayList<String> dates) {
		this.dates = dates;
	}
	public ArrayList<Integer> getCount() {
		return count;
	}
	public void setCount(ArrayList<Integer> count) {
		this.count = count;
	}
	
	

}
