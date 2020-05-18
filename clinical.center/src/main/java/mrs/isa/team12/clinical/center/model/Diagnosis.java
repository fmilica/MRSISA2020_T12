package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "diagnosis")
public class Diagnosis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Version
	private Long version;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "diagnosis_perscription", 
				joinColumns = @JoinColumn(name = "diagnosis_id"),
				inverseJoinColumns = @JoinColumn(name = "perscription_id"))
	private Set<Prescription> prescriptions;
	
	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="diagnosis")
	private Set<MedicalReport> medicalReports;
	
	@ManyToOne
	@JoinColumn(name = "sifarnik_id", referencedColumnName = "id", nullable = false)
	private DiagnosePerscription diagnosePerscription;

	public Diagnosis() {}

	public Diagnosis(Long id, String name, Set<Prescription> prescriptions, Set<MedicalReport> medicalReports) {
		super();
		this.id = id;
		this.name = name;
		this.prescriptions = prescriptions;
		this.medicalReports = medicalReports;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public Set<MedicalReport> getMedicalReports() {
		return medicalReports;
	}

	public void setMedicalReports(Set<MedicalReport> medicalReports) {
		this.medicalReports = medicalReports;
	}

	public DiagnosePerscription getDiagnosePerscription() {
		return diagnosePerscription;
	}

	public void setDiagnosePerscription(DiagnosePerscription diagnosePerscription) {
		this.diagnosePerscription = diagnosePerscription;
	}

	@Override
	public String toString() {
		return "Diagnosis [id=" + id + ", name=" + name + ", diagnosePerscription=" + diagnosePerscription + "]";
	}
}
