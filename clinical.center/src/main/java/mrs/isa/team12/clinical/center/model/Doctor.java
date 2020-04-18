package mrs.isa.team12.clinical.center.model;

import java.util.Date;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Doctor extends MedicalPersonnel {
	
	@Column(name = "startWork", nullable = false, unique = false)
	private Date startWork;
	
	@Column(name = "endWork", nullable = false, unique = false)
	private Date endWork;
	
	@Column(name = "rating", nullable = true, unique = false)
	private Double rating;
	
	@ManyToOne
	@JoinColumn(name = "clinic_id", referencedColumnName = "id", nullable = false)
	private Clinic clinic;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY)
	private Set<AppointmentType> appointmentTypes;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "doctor")
	private Set<Appointment> appointments;

	public Doctor() {}

	public Doctor(String email, String password, String name, String suername, String address, String city,
			String country, String phoneNumber, Integer securityNumber, Set<Leave> leaveList, Set<Patient> patients,
			Date startWork, Date endWork, Double rating, Set<AppointmentType> appointmentTypes, Clinic clinic,
			Set<Appointment> appointments) {
		super(email, password, name, suername, address, city, country, phoneNumber, securityNumber, leaveList,
				patients);
		this.startWork = startWork;
		this.endWork = endWork;
		this.rating = rating;
		this.appointmentTypes = appointmentTypes;
		this.clinic = clinic;
		this.appointments = appointments;
	}

	public Set<AppointmentType> getAppointmentTypes() {
		return appointmentTypes;
	}

	public void setAppointmentTypes(Set<AppointmentType> appointmentTypes) {
		this.appointmentTypes = appointmentTypes;
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

	public Date getStartWork() {
		return startWork;
	}

	public void setStartWork(Date startWork) {
		this.startWork = startWork;
	}

	public Date getEndWork() {
		return endWork;
	}

	public void setEndWork(Date endWork) {
		this.endWork = endWork;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
}
