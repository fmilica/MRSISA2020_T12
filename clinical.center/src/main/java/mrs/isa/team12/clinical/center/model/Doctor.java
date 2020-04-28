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
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "doctor")
	private Set<Rating> ratings;
	

	public Doctor() {}


	public Doctor(Date startWork, Date endWork, Double rating, Clinic clinic, Set<AppointmentType> appointmentTypes,
			Set<Appointment> appointments, Set<Rating> ratings) {
		super();
		this.startWork = startWork;
		this.endWork = endWork;
		this.rating = rating;
		this.clinic = clinic;
		this.appointmentTypes = appointmentTypes;
		this.appointments = appointments;
		this.ratings = ratings;
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


	public Clinic getClinic() {
		return clinic;
	}


	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}


	public Set<AppointmentType> getAppointmentTypes() {
		return appointmentTypes;
	}


	public void setAppointmentTypes(Set<AppointmentType> appointmentTypes) {
		this.appointmentTypes = appointmentTypes;
	}


	public Set<Appointment> getAppointments() {
		return appointments;
	}


	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}


	public Set<Rating> getRatings() {
		return ratings;
	}


	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}
	
}
