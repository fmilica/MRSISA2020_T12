package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.Set;

import javax.persistence.Column;
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
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "clinicalCentre")
	private Set<Clinic> clinics;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "clinicalCentre")
	private Set<ClinicalCentreAdmin> admins;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY)
	private Set<RegistrationRequest> registrationRequests;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "clinicalCentre")
	private Set<Patient> patients;
	
	public ClinicalCentre() {}
	
	public ClinicalCentre(String name) {
		super();
		this.name = name;
	}
	
	public ClinicalCentre(Set<Clinic> clinics, Set<ClinicalCentreAdmin> admins,
			Set<RegistrationRequest> registrationRequests, Set<Patient> patients) {
		super();
		this.clinics = clinics;
		this.admins = admins;
		this.registrationRequests = registrationRequests;
		this.patients = patients;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Clinic> getClinics() {
		return clinics;
	}
	
	public void setClinics(Set<Clinic> clinics) {
		this.clinics = clinics;
	}
	
	public Set<ClinicalCentreAdmin> getAdmins() {
		return null;
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
	
	public void add(ClinicalCentreAdmin clinicalCentreAdmin) {
		if(!this.admins.contains(clinicalCentreAdmin)) {
			this.admins.add(clinicalCentreAdmin);
		}
	}

	@Override
	public String toString() {
		return "ClinicalCentre [id = "+ id + ", name=" + name + "]";
	}
}
