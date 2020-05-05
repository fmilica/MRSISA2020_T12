package mrs.isa.team12.clinical.center.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "clinical_centre_admin")
public class ClinicalCentreAdmin extends RegisteredUser{
	
	/*Nullable sam promenila na true samo da bih mogla nesto da probam inace treba da bude false*/
	@ManyToOne
	@JoinColumn(name = "clinical_centre_id", referencedColumnName = "id", nullable = false)
	@JsonBackReference
	private ClinicalCentre clinicalCentre;
	
	public ClinicalCentreAdmin() {}

	public ClinicalCentreAdmin(String email, String password, String name, String surname, String gender, 
			String dateOfBirth, String address, String city,String country, 
			String phoneNumber, Integer securityNumber, ClinicalCentre clinicalCentre) {
		super(email, password, name, surname, gender, dateOfBirth, address, city, country, phoneNumber, securityNumber);
		this.clinicalCentre = clinicalCentre;
	}

	public ClinicalCentre getClinicalCentre() {
		return clinicalCentre;
	}

	public void setClinicalCentre(ClinicalCentre clinicalCentre) {
		this.clinicalCentre = clinicalCentre;
	}
}
