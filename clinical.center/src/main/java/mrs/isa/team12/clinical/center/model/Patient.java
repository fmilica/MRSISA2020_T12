package mrs.isa.team12.clinical.center.model;

import java.util.Set;

public class Patient extends RegisteredUser {
	
	private Set<Appointment> appointments;
	
	private MedicalRecords medicalRecords;
	
	private ClinicalCentre clinicalCentre;
	
	private Clinic clinic;

	public Patient() {}

	public Patient(Set<Appointment> appointments, MedicalRecords medicalRecords, ClinicalCentre clinicalCentre,
			Clinic clinic) {
		super();
		this.appointments = appointments;
		this.medicalRecords = medicalRecords;
		this.clinicalCentre = clinicalCentre;
		this.clinic = clinic;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public MedicalRecords getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(MedicalRecords medicalRecords) {
		this.medicalRecords = medicalRecords;
	}

	public ClinicalCentre getClinicalCentre() {
		return clinicalCentre;
	}

	public void setClinicalCentre(ClinicalCentre clinicalCentre) {
		this.clinicalCentre = clinicalCentre;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	

}
