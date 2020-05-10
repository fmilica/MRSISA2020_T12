package mrs.isa.team12.clinical.center.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "clinic_admin")
public class ClinicAdmin extends RegisteredUser {
	
	//@JsonManagedReference
	/*Treba da bude false*/
	@ManyToOne
	@JoinColumn(name="clinic_id", referencedColumnName="id", nullable=false)
	//@JsonBackReference
	@JsonBackReference("clinic-admins")
	private Clinic clinic;

	public ClinicAdmin() {}

	public ClinicAdmin(String email, String password, String name, String surname, String gender, 
			String dateOfBirth, String address, String city, String country, 
			String phoneNumber, String securityNumber) {
		super(email, password, name, surname, gender, dateOfBirth, address, city, country, phoneNumber, securityNumber);
	}
	
	public ClinicAdmin(String email, String password, String name, String surname, String gender,
			String dateOfBirth, String address, String city, String country, 
			String phoneNumber, String securityNumber, Clinic clinic) {
		super(email, password, name, surname, gender, dateOfBirth, address, city, country, phoneNumber, securityNumber);
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
		return "ClinicAdmin [clinic=" + clinic + "]";
	}

}
