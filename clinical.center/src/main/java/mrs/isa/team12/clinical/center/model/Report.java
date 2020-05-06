package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "report")
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "clinicRating", unique = false, nullable = true)
	private Double clinicRating;
	
	@Column(name = "graph", unique = false, nullable = true)
	private Double graph;
	
	@Column(name = "income", unique = false, nullable = true)
	private Double income;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "clinic_id", referencedColumnName = "id", nullable = true)
	@JsonBackReference
	private Clinic clinic;
	
	@ManyToMany(cascade = {ALL}, fetch = LAZY)
	@JoinColumn(name = "rating_id", referencedColumnName = "id", nullable = true)
	private Set<Report> ratings;
	
	public Report() {}

	public Report(Long id, Double clinicRating, Double graph, Double income, Clinic clinic, Set<Report> ratings) {
		super();
		this.id = id;
		this.clinicRating = clinicRating;
		this.graph = graph;
		this.income = income;
		this.clinic = clinic;
		this.ratings = ratings;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getClinicRating() {
		return clinicRating;
	}

	public void setClinicRating(Double clinicRating) {
		this.clinicRating = clinicRating;
	}

	public Double getGraph() {
		return graph;
	}

	public void setGraph(Double graph) {
		this.graph = graph;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Set<Report> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Report> ratings) {
		this.ratings = ratings;
	}
}
