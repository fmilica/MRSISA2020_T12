package mrs.isa.team12.clinical.center.model;

import java.util.Set;

public abstract class MedicalPersonnel extends RegisteredUser {

	private Set<Leave> leaveList;
	private Set<Patient> patients;
	
	public MedicalPersonnel() {
		// TODO Auto-generated constructor stub
	}

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
