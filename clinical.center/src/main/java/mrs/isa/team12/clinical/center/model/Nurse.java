package mrs.isa.team12.clinical.center.model;

import java.util.Set;

public class Nurse extends MedicalPersonnel{
	
	private Clinic clinic;

	public Nurse() {
		// TODO Auto-generated constructor stub
	}

	public Nurse(String email, String password, String name, String suername, String address, String city,
			String country, String phoneNumber, Integer securityNumber, Set<Leave> leaveList, Set<Patient> patients,
			Clinic clinic) {
		super(email, password, name, suername, address, city, country, phoneNumber, securityNumber, leaveList,
				patients);
		this.clinic = clinic;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	

}
