package mrs.isa.team12.clinical.center.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "nurse")
public class Nurse extends MedicalPersonnel{
	
	/*nullable = false*/
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "clinic_id", referencedColumnName = "id", nullable = true)
	private Clinic clinic;

	public Nurse() {}

	public Nurse(String email, String password, String name, String suername, String gender,
			String dateOfBirth, String address, String city, String country, 
			String phoneNumber, Integer securityNumber, Set<Leave> leaveList, Set<Patient> patients,
			Clinic clinic) {
		super(email, password, name, suername, gender, dateOfBirth, address, city, country, phoneNumber, securityNumber, leaveList,
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
