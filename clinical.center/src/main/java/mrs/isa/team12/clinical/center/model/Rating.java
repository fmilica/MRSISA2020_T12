package mrs.isa.team12.clinical.center.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "rating")
@Where(clause="is_active=true")
public class Rating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Version
	private Long version;
	
	@Column(name="is_active")
	private Boolean active;
	
	@Column(name = "rating", unique = false, nullable = false )
	private Integer rating;
	
	@ManyToOne
	@JoinColumn(name="doctor_id", referencedColumnName = "id", nullable = true)
	private Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name="clinic_id", referencedColumnName = "id", nullable = true)
	private Clinic clinic;
	
	@ManyToOne
	@JoinColumn(name="patient_id", referencedColumnName = "id", nullable = false)
	private Patient patient;
	
	public Rating() {}

	public Rating(Integer rating, Doctor doctor, Clinic clinic, Patient patient) {
		this.rating = rating;
		this.doctor = doctor;
		this.clinic = clinic;
		this.patient = patient;
		this.active = true;
	}
	
	public Rating(Long id, Integer rating, Doctor doctor, Clinic clinic, Patient patient) {
		this.id = id;
		this.rating = rating;
		this.doctor = doctor;
		this.clinic = clinic;
		this.patient = patient;
		this.active = true;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public Clinic getClinic() {
		return clinic;
	}
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
}
