package mrs.isa.team12.clinical.center.model;

import java.util.Set;

public class ClinicalCentre {
	
	private Set<Clinic> clinics;
	private Set<ClinicalCentreAdmin> admins;
	private Set<RegistrationRequest> registrationRequests;
	private Set<Patient> patients;
	
	public ClinicalCentre() {
		super();
	}
	public ClinicalCentre(Set<Clinic> clinics, Set<ClinicalCentreAdmin> admins,
			Set<RegistrationRequest> registrationRequests, Set<Patient> patients) {
		super();
		this.clinics = clinics;
		this.admins = admins;
		this.registrationRequests = registrationRequests;
		this.patients = patients;
	}
	public Set<Clinic> getClinics() {
		return clinics;
	}
	public void setClinics(Set<Clinic> clinics) {
		this.clinics = clinics;
	}
	public Set<ClinicalCentreAdmin> getAdmins() {
		return admins;
	}
	public void setAdmins(Set<ClinicalCentreAdmin> admins) {
		this.admins = admins;
	}
	public Set<RegistrationRequest> getRegistrationRequests() {
		return registrationRequests;
	}
	public void setRegistrationRequests(Set<RegistrationRequest> registrationRequests) {
		this.registrationRequests = registrationRequests;
	}
	public Set<Patient> getPatients() {
		return patients;
	}
	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}
	
	

}
