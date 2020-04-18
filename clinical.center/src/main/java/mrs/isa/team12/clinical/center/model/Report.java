package mrs.isa.team12.clinical.center.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "clinicRating", unique = false, nullable = true)
	private Double clinicRating;
	
	@Column(name = "income", unique = false, nullable = true)
	private Double income;

	public Report() {}

	public Report(Double clinicRating, Double income) {
		super();
		this.clinicRating = clinicRating;
		this.income = income;
	}

	public Double getClinicRating() {
		return clinicRating;
	}

	public void setClinicRating(Double clinicRating) {
		this.clinicRating = clinicRating;
	}
	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}
}
