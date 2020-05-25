package mrs.isa.team12.clinical.center.dto;

import java.sql.Date;

import mrs.isa.team12.clinical.center.model.Appointment;

public class DoctorsAppointmentDto {
	private String id;
	private String patient;
	private String ordination;
	private Date date;
	private String startTime;
	private String endTime;
	private String type;
	
	public DoctorsAppointmentDto() {
		super();
	}

	public DoctorsAppointmentDto(String id, String patient, String ordination, Date date, String startTime,
			String endTime, String type) {
		super();
		this.id = id;
		this.patient = patient;
		this.ordination = ordination;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.type = type;
	}

	public DoctorsAppointmentDto(Appointment a) {
		this.id = a.getPatient().getSecurityNumber();
		this.patient = a.getPatient().getName() + " " + a.getPatient().getSurname();
		this.ordination = a.getOrdination().getOrdinationNumber() + " - " +a.getOrdination().getName();
		this.date = a.getDate();
		if(a.getStartTime()<=9) {
			this.startTime = "0"+a.getStartTime();
		}else {
			this.startTime = a.getStartTime()+"";
		}
		if(a.getEndTime() <=9) {
			this.endTime = "0"+a.getEndTime();
		}else {
			this.endTime = a.getEndTime()+"";
		}
		if(a.getType() == null) {
			this.type = "Appointment";
		}else {
			this.type = "Operation";
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getOrdination() {
		return ordination;
	}

	public void setOrdination(String ordination) {
		this.ordination = ordination;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
