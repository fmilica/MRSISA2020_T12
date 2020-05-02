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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "appointment_type")
public class AppointmentType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@Column(name = "duration", unique = false, nullable = false )
	private Integer duration;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "type")
	private Set<Appointment> appointments;
	
	/*mislim da appointment type po svom kreiranju mora da dobije neku pocetnu cenu*/
	/*takodje ovde ce kasnije biti one to many zbog razlicitih vremena vazenja cena*/
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "pricelist_item_id", referencedColumnName = "id", nullable = false)
	private PricelistItem pricelistItem;

	@ManyToOne
	@JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = true)
	private Doctor doctor;
	
	public AppointmentType() {}
	
	public AppointmentType(String name, Integer duration) {
		super();
		this.name = name;
		this.duration = duration;
	}

	public AppointmentType(Long id, String name, Integer duration, Set<Appointment> appointments,
			PricelistItem pricelistItem, Doctor doctor) {
		super();
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.appointments = appointments;
		this.pricelistItem = pricelistItem;
		this.doctor = doctor;
	}

	public AppointmentType(Long id, String name, Set<Appointment> appointments, PricelistItem pricelistItem,
			Doctor doctor) {
		super();
		this.id = id;
		this.name = name;
		this.appointments = appointments;
		this.pricelistItem = pricelistItem;
		this.doctor = doctor;
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

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public PricelistItem getPricelistItem() {
		return pricelistItem;
	}

	public void setPricelistItem(PricelistItem pricelistItem) {
		this.pricelistItem = pricelistItem;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}


	public Integer getDuration() {
		return duration;
	}


	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}
