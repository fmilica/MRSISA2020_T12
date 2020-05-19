package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "prescription")
public class Prescription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Version
	private Long version;
	
	@Column(name = "medicine", unique = false, nullable = false)
	private String medicine;
	
	@ManyToMany(mappedBy = "prescriptions")
	private Set<MedicalReport> medicalReports;
	
	//CascadeType.PERSIST, CascadeType.MERGE
	@ManyToMany(cascade={ALL}, mappedBy = "prescriptions")
	private Set<Diagnosis> diagnosis;
	
	public Prescription() {}

	public Prescription(String medicine) {
		super();
		this.medicine = medicine;
	}
	
	public Prescription(Long id, String medicine, Set<MedicalReport> medicalReports, Set<Diagnosis> diagnosis) {
		super();
		this.id = id;
		this.medicine = medicine;
		this.medicalReports = medicalReports;
		this.diagnosis = diagnosis;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMedicine() {
		return medicine;
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	
	public Set<Diagnosis> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(Set<Diagnosis> diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	public void addDiagnosis(Diagnosis diagnosis) {
		this.diagnosis.add(diagnosis);
	}
}
