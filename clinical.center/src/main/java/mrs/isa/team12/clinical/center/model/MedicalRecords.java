package mrs.isa.team12.clinical.center.model;

import java.util.Set;

public class MedicalRecords {
	
	private Integer height;
	private Integer weight;
	private Integer bloodPressure;
	private String bloodType;
	private String allergies;
	private String medicalHistory;
	
	private Patient patient;
	
	private Set<Appointment> appointments;
	
	public MedicalRecords() {
		super();
	}

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
