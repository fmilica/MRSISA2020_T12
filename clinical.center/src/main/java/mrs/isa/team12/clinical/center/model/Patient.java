package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient extends RegisteredUser {
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "patient")
	private Set<Appointment> appointments;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "medical_record_id")
	private MedicalRecords medicalRecords;
	
	@ManyToOne
	@JoinColumn(name = "clinical_centre_id", referencedColumnName = "id", nullable = true)
	private ClinicalCentre clinicalCentre;
	
	@OneToOne(cascade = {ALL}, fetch = LAZY)
	@JoinColumn(name = "registration_request_id")
	private RegistrationRequest registrationRequest;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "patient")
	private Set<Rating> ratings;
	
	public Patient() {}

	public Patient(Set<Appointment> appointments, MedicalRecords medicalRecords, ClinicalCentre clinicalCentre,
			Set<Rating> ratings) {
		super();
		this.appointments = appointments;
		this.medicalRecords = medicalRecords;
		this.clinicalCentre = clinicalCentre;
		this.ratings = ratings;
	}
	
	public Patient(Set<Appointment> appointments, MedicalRecords medicalRecords, ClinicalCentre clinicalCentre,
			RegistrationRequest registrationRequest, Set<Rating> ratings) {
		super();
		this.appointments = appointments;
		this.medicalRecords = medicalRecords;
		this.clinicalCentre = clinicalCentre;
		this.registrationRequest = registrationRequest;
		this.ratings = ratings;
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
	public Set<Rating> getRatings() {
		return ratings;
	}
	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}
	public RegistrationRequest getRegistrationRequest() {
		return registrationRequest;
	}
	public void setRegistrationRequest(RegistrationRequest registrationRequest) {
		this.registrationRequest = registrationRequest;
	}
	public void addAppointment(Appointment app) {
		if (!appointments.contains(app)) {
			appointments.add(app);
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appointments == null) ? 0 : appointments.hashCode());
		result = prime * result + ((clinicalCentre == null) ? 0 : clinicalCentre.hashCode());
		result = prime * result + ((medicalRecords == null) ? 0 : medicalRecords.hashCode());
		result = prime * result + ((ratings == null) ? 0 : ratings.hashCode());
		result = prime * result + ((registrationRequest == null) ? 0 : registrationRequest.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
