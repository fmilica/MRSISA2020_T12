package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Patient extends RegisteredUser {
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "patient")
	private Set<Appointment> appointments;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "medical_record_id")
	private MedicalRecords medicalRecords;
	
	@ManyToOne
	@JoinColumn(name = "clinical_centre_id", referencedColumnName = "id", nullable = false)
	private ClinicalCentre clinicalCentre;
	
	public Patient() {}

	public Patient(Set<Appointment> appointments, MedicalRecords medicalRecords, ClinicalCentre clinicalCentre) {
		super();
		this.appointments = appointments;
		this.medicalRecords = medicalRecords;
		this.clinicalCentre = clinicalCentre;
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
}
