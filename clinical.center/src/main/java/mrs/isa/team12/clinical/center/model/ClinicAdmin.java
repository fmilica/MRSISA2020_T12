package mrs.isa.team12.clinical.center.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ClinicAdmin extends RegisteredUser {
	
	@ManyToOne
	@JoinColumn(name="clinic_id", referencedColumnName="id", nullable=false)
	private Clinic clinic;

	public ClinicAdmin() {}

	public ClinicAdmin(String email, String password, String name, String surname, String address, String city,
			String country, String phoneNumber, Integer securityNumber) {
		super(email, password, name, surname, address, city, country, phoneNumber, securityNumber);
	}
	
	public ClinicAdmin(String email, String password, String name, String surname, String address, String city,
			String country, String phoneNumber, Integer securityNumber, Clinic clinic) {
		super(email, password, name, surname, address, city, country, phoneNumber, securityNumber);
		this.clinic = clinic;
	}
	
	public Clinic getClinic() {
		return clinic;
	}
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
}
