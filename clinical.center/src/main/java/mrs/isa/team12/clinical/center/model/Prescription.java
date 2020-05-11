package mrs.isa.team12.clinical.center.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "prescription")
public class Prescription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "medicine", unique = false, nullable = false)
	private String medicine;
	
	@ManyToMany(mappedBy = "prescriptions")
	private Set<MedicalReport> medicalReports;
	
	public Prescription() {}

	public Prescription(String medicine) {
		super();
		this.medicine = medicine;
	}

	public String getMedicine() {
		return medicine;
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	
}
