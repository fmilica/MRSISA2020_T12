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
import javax.persistence.ManyToOne;

@Entity
public class MedicalReport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "description", unique = false, nullable = false)
	private String description;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "medicalReport_perscription", 
				joinColumns = @JoinColumn(name = "medical_report_id"),
				inverseJoinColumns = @JoinColumn(name = "prescription_id"))
	private Set<Prescription> prescriptions;
	
	@ManyToOne
	@JoinColumn(name = "diagnosis_id", referencedColumnName = "id", nullable = false)
	private Diagnosis diagnosis;
	

	public MedicalReport() {}


	public MedicalReport(Long id, String description, Set<Prescription> prescriptions, Diagnosis diagnosis) {
		super();
		this.id = id;
		this.description = description;
		this.prescriptions = prescriptions;
		this.diagnosis = diagnosis;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public Diagnosis getDiagnosis() {
		return diagnosis;
	}


	public void setDiagnosis(Diagnosis diagnosis) {
		this.diagnosis = diagnosis;
	}
	
}
