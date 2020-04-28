package mrs.isa.team12.clinical.center.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "clinic_admin")
public class ClinicAdmin extends RegisteredUser {
	
	/*Treba da bude false*/
	@ManyToOne
	@JoinColumn(name="clinic_id", referencedColumnName="id", nullable=true)
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

	@Override
	public String toString() {
		return "ClinicAdmin [clinic=" + clinic + ", getClinic()=" + getClinic() + ", getEmail()=" + getEmail()
				+ ", getPassword()=" + getPassword() + ", getName()=" + getName() + ", getSurname()=" + getSurname()
				+ ", getAddress()=" + getAddress() + ", getCity()=" + getCity() + ", getCountry()=" + getCountry()
				+ ", getPhoneNumber()=" + getPhoneNumber() + ", getSecurityNumber()=" + getSecurityNumber()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}
