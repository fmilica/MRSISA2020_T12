package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import mrs.isa.team12.clinical.center.model.enums.Specialization;

@Entity
@Table(name="doctor")
public class Doctor extends MedicalPersonnel {
	
	@Column(name = "specialization", unique = false, nullable = false)
	private Specialization specialization;
	
	// u rasponu od 0 do 24
	@Column(name = "start_work", unique = false, nullable = false)
	private Integer startWork;
	
	@Column(name = "end_work", unique = false, nullable = false)
	private Integer endWork;
	
	@Column(name = "rating", unique = false, nullable = true)
	private Double rating;
	
	@ManyToOne
	@JoinColumn(name = "clinic_id", referencedColumnName = "id", nullable = false)
	@JsonBackReference
	private Clinic clinic;
	
	//@OneToMany(cascade = {ALL}, fetch = LAZY)
	@ManyToMany(mappedBy = "doctors")
	private Set<AppointmentType> appointmentTypes;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "doctor")
	private Set<Appointment> appointments;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "doctor")
	private Set<Rating> ratings;
	

	public Doctor() {}


	public Doctor(Integer startWork, Integer endWork, Double rating, Clinic clinic, Set<AppointmentType> appointmentTypes,
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


	public Integer getStartWork() {
		return startWork;
	}
	public void setStartWork(Integer startWork) {
		this.startWork = startWork;
	}
	public Integer getEndWork() {
		return endWork;
	}
	public void setEndWork(Integer endWork) {
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
		return null;
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
	public Specialization getSpecialization() {
		return specialization;
	}
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	public void addAppointment(Appointment appointment) {
		appointment.setDoctor(this);
		this.appointments.add(appointment);
	}

	@Override
	public String toString() {
		return super.toString() + "Doctor [specialization=" + specialization + ", startWork=" + startWork + ", endWork=" + endWork
				+ ", rating=" + rating + ", clinic=" + clinic + ", appointmentTypes=" + appointmentTypes
				+ ", appointments=" + appointments + ", ratings=" + ratings + "]";
	}

}
