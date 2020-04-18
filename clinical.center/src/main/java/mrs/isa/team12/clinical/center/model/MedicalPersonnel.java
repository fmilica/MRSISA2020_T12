package mrs.isa.team12.clinical.center.model;

import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public abstract class MedicalPersonnel extends RegisteredUser {
	
	@OneToMany(cascade = {ALL}, fetch = LAZY)
	private Set<Leave> leaveList;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "medical_personnel_patient", 
				joinColumns = @JoinColumn(name = "medical_personnel_id"),
				inverseJoinColumns = @JoinColumn(name = "patient_id"))
	private Set<Patient> patients;
	
	public MedicalPersonnel() {}

	public MedicalPersonnel(String email, String password, String name, String suername, String address, String city,
			String country, String phoneNumber, Integer securityNumber, Set<Leave> leaveList, Set<Patient> patients) {
		super(email, password, name, suername, address, city, country, phoneNumber, securityNumber);
		this.leaveList = leaveList;
		this.patients = patients;
	}

	public Set<Leave> getLeaveList() {
		return leaveList;
	}


	public void setLeaveList(Set<Leave> leaveList) {
		this.leaveList = leaveList;
	}


	public Set<Patient> getPatients() {
		return patients;
	}


	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}
}
