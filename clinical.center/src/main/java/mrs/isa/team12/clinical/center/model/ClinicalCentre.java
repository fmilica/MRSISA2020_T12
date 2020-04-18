package mrs.isa.team12.clinical.center.model;

import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ClinicalCentre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "clinicalCentre")
	private Set<Clinic> clinics;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "clinicalCentre")
	private Set<ClinicalCentreAdmin> admins;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY)
	private Set<RegistrationRequest> registrationRequests;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "clinicalCentre")
	private Set<Patient> patients;
	
	public ClinicalCentre() {}
	
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
