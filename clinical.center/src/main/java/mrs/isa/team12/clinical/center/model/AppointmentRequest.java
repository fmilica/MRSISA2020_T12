package mrs.isa.team12.clinical.center.model;

import java.util.Date;

public class AppointmentRequest {
	
	private Appointment appointment;
	private Date requestDate;
	private Boolean approved;
	
	private Clinic clinic;

	public AppointmentRequest() {
		super();
	}

	public AppointmentRequest(Appointment appointment, Date requestDate, Boolean approved, Clinic clinic) {
		super();
		this.appointment = appointment;
		this.requestDate = requestDate;
		this.approved = approved;
		this.clinic = clinic;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	

}
