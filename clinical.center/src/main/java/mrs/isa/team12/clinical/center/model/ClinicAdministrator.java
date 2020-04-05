package mrs.isa.team12.clinical.center.model;

public class ClinicAdministrator extends RegisteredUser {
	private int clinicID;
	
	public ClinicAdministrator() {}

	public ClinicAdministrator(String username, String password, Role role, String name, String surname, String address,
			String city, String country, String phoneNumber, String securityNumber, int clinicID) {
		super(username, password, role, name, surname, address, city, country, phoneNumber, securityNumber);
		this.clinicID = clinicID;
	}

	public int getClinicID() {
		return clinicID;
	}

	public void setClinicID(int clinicID) {
		this.clinicID = clinicID;
	}
}
