package mrs.isa.team12.clinical.center.model;

public class ClinicAdmin extends RegisteredUser {
	
	private Clinic clinic;

	public ClinicAdmin() {
		super();
	}

	public ClinicAdmin(String email, String password, String name, String suername, String address, String city,
			String country, String phoneNumber, Integer securityNumber, Clinic clinic) {
		super(email, password, name, suername, address, city, country, phoneNumber, securityNumber);
		this.clinic = clinic;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	

}
