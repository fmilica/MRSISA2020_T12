package mrs.isa.team12.clinical.center.dto;

import java.util.ArrayList;

public class IncomeReportsDto {
	
	private ArrayList<String> dates;
	private ArrayList<Double> income;
	private int total;
	
	public IncomeReportsDto() {}
	
	public IncomeReportsDto(ArrayList<String> dates, ArrayList<Double> income, int total) {
		this.dates = dates;
		this.income = income;
		this.total = total;
	}
	
	public ArrayList<String> getDates() {
		return dates;
	}
	public void setDates(ArrayList<String> dates) {
		this.dates = dates;
	}
	public ArrayList<Double> getIncome() {
		return income;
	}
	public void setIncome(ArrayList<Double> income) {
		this.income = income;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
