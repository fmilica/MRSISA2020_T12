package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "diagnose_perscription")
public class DiagnosePerscription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Version
	private Long version;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY)
	private Set<Diagnosis> diagnosis;

	public DiagnosePerscription() {}

	public DiagnosePerscription(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Diagnosis> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(Set<Diagnosis> diagnosis) {
		this.diagnosis = diagnosis;
	}

	@Override
	public String toString() {
		return "[id=" + id + "]";
	}
}
