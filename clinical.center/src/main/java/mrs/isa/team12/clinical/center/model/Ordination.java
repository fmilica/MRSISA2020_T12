package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import mrs.isa.team12.clinical.center.model.enums.OrdinationType;

@Entity
@Table(name = "ordination")
public class Ordination {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "ordination_type", unique = false, nullable = false)
	private OrdinationType type;
	
	@Column(name = "name", unique = false, nullable = false)
	private String name;
	
	@Column(name = "ordination_number", unique = false, nullable = false)
	private Integer ordinationNumber;
	
	@ManyToOne
	@JoinColumn(name = "clinic_id", referencedColumnName = "id", nullable = false)
	//@JsonBackReference
	@JsonBackReference("clinic-ordinations")
	private Clinic clinic;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "ordination")
	private Set<Appointment> appointments;

	public Ordination() {}
	
	public Ordination(OrdinationType type, String name, Integer ordinationNumber, Clinic clinic, Set<Appointment> appointments) {
		super();
		this.type = type;
		this.name = name;
		this.ordinationNumber = ordinationNumber;
		this.clinic = clinic;
		this.appointments = appointments;
	}

	public OrdinationType getType() {
		return type;
	}
	public void setType(OrdinationType type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Clinic getClinic() {
		return clinic;
	}
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	public Set<Appointment> getAppointments() {
		return appointments;
	}
	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getOrdinationNumber() {
		return ordinationNumber;
	}
	public void setOrdinationNumber(Integer ordinationNumber) {
		this.ordinationNumber = ordinationNumber;
	}
	
	public void addAppointment(Appointment a) {
		this.appointments.add(a);
	}
	
	@Override
	public String toString() {
		return "Ordination [id=" + id + ", type=" + type + ", name=" + name + ", clinic=" + clinic + ", appointments="
				+ appointments + "]";
	}
}
