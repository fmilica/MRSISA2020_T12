package mrs.isa.team12.clinical.center.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ClinicalCentreAdmin extends RegisteredUser{
	
	@ManyToOne
	@JoinColumn(name = "clinical_centre_id", referencedColumnName = "id", nullable = false)
	private ClinicalCentre clinicalCentre;
	
	public ClinicalCentreAdmin() {}

	public ClinicalCentreAdmin(String email, String password, String name, String suername, String address, String city,
			String country, String phoneNumber, Integer securityNumber, ClinicalCentre clinicalCentre) {
		super(email, password, name, suername, address, city, country, phoneNumber, securityNumber);
		this.clinicalCentre = clinicalCentre;
	}

	public ClinicalCentre getClinicalCentre() {
		return clinicalCentre;
	}

	public void setClinicalCentre(ClinicalCentre clinicalCentre) {
		this.clinicalCentre = clinicalCentre;
	}
}
