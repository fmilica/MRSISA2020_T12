package mrs.isa.team12.clinical.center.model;

import java.util.HashMap;

public class Report {
	
	private Double clinicRating;
	private HashMap<Doctor, Double> doctorsRatings;
	private Double income;

	public Report() {
		// TODO Auto-generated constructor stub
	}

	public Report(Double clinicRating, HashMap<Doctor, Double> doctorsRatings, Double income) {
		super();
		this.clinicRating = clinicRating;
		this.doctorsRatings = doctorsRatings;
		this.income = income;
	}

	public Double getClinicRating() {
		return clinicRating;
	}

	public void setClinicRating(Double clinicRating) {
		this.clinicRating = clinicRating;
	}

	public HashMap<Doctor, Double> getDoctorsRatings() {
		return doctorsRatings;
	}

	public void setDoctorsRatings(HashMap<Doctor, Double> doctorsRatings) {
		this.doctorsRatings = doctorsRatings;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}
	
	

}
