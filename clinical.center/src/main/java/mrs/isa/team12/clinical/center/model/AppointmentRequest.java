package mrs.isa.team12.clinical.center.model;

import static javax.persistence.FetchType.LAZY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "appointment_request")
public class AppointmentRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "appointment_id")
	private Appointment appointment;
	
	@Column(name = "request_date", unique = false, nullable = false)
	private Date requestDate;
	
	@Column(name = "approved", unique = false, nullable = false)
	private Boolean approved;
	
	@ManyToOne
	@JoinColumn(name = "clinic_id", referencedColumnName = "id", nullable = false)
	@JsonBackReference
	private Clinic clinic;

	public AppointmentRequest() {}

	public AppointmentRequest(Appointment appointment, Date requestDate, Boolean approved, Clinic clinic) {
		this.appointment = appointment;
		this.requestDate = requestDate;
		this.approved = approved;
		this.clinic = clinic;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	@Override
	public String toString() {
		return "AppointmentRequest [id=" + id + ", appointment=" + appointment + ", requestDate=" + requestDate
				+ ", approved=" + approved + ", clinic=" + clinic + "]";
	}
}
