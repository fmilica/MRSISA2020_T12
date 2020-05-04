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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "medical_records")
public class MedicalRecords {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "height", unique = false, nullable = false)
	private Integer height;
	
	@Column(name = "weight", unique = false, nullable = false)
	private Integer weight;
	
	@Column(name = "blood_pressure", unique = false, nullable = false)
	private Integer bloodPressure;
	
	@Column(name = "blood_type", unique = false, nullable = false)
	private String bloodType;
	
	@Column(name = "allergies", unique = false, nullable = false)
	private String allergies;
	
	@Column(name = "medical_history", unique = false, nullable = false)
	private String medicalHistory;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, orphanRemoval = true)
	@JoinColumn(name = "medical_record_id")
	private Set<Appointment> appointments;
	
	public MedicalRecords() {}

	public MedicalRecords(Integer height, Integer weight, Integer bloodPressure, String bloodType, String allergies,
			String medicalHistory, Patient patient, Set<Appointment> appointments) {
		super();
		this.height = height;
		this.weight = weight;
		this.bloodPressure = bloodPressure;
		this.bloodType = bloodType;
		this.allergies = allergies;
		this.medicalHistory = medicalHistory;
		this.patient = patient;
		this.appointments = appointments;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(Integer bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
}
