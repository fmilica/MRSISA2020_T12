package mrs.isa.team12.clinical.center.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class MedicalReport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "diagnosis", unique = false, nullable = false)
	private String diagnosis;
	
	@Column(name = "description", unique = false, nullable = false)
	private String description;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "medicalReport_perscription", 
				joinColumns = @JoinColumn(name = "medical_report_id"),
				inverseJoinColumns = @JoinColumn(name = "prescription_id"))
	private Set<Prescription> prescriptions;

	public MedicalReport() {}

	public MedicalReport(String diagnosis, String description,
			Set<Prescription> prescriptions) {
		super();
		this.diagnosis = diagnosis;
		this.description = description;
		this.prescriptions = prescriptions;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public Set<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}
}
