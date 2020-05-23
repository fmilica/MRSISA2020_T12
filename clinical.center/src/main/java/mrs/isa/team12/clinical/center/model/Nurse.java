package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "nurse")
public class Nurse extends MedicalPersonnel{
	
	/*nullable = false*/
	@ManyToOne
	@JoinColumn(name = "clinic_id", referencedColumnName = "id", nullable = true)
	private Clinic clinic;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "nurse")
	private Set<MedicalReport> medicalReports;

	public Nurse() {}

	public Nurse(String email, String password, String name, String suername, String gender,
			String dateOfBirth, String address, String city, String country, 
			String phoneNumber, String securityNumber, Set<Leave> leaveList, Set<Patient> patients,
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

	public Set<MedicalReport> getMedicalReports() {
		return medicalReports;
	}

	public void setMedicalReports(Set<MedicalReport> medicalReports) {
		this.medicalReports = medicalReports;
	}
}
