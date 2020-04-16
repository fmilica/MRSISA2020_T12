package mrs.isa.team12.clinical.center.model;

import java.util.Date;
import java.util.Set;

public class Doctor extends MedicalPersonnel {
	
	private Date startWork;
	private Date endWork;
	private Double rating;
	
	private Set<AppointmentType> appointmentTypes;
	
	private Clinic clinic;
	
	private Set<Appointment> appointments;

	public Doctor() {
		// TODO Auto-generated constructor stub
	}

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
