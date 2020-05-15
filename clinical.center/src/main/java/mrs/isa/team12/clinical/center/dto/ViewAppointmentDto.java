package mrs.isa.team12.clinical.center.dto;

import java.util.Date;

import mrs.isa.team12.clinical.center.model.Appointment;

//mora da bude confirmed=true i finished = false
public class ViewAppointmentDto {
	
	private Long id;
	private String appType;
	private String doctor;
	private String patient;
	private String ordination;
	private Date date;
	private Integer startTime;
	private Integer endTime;
	
	
	public ViewAppointmentDto(Appointment app) {
		this.id = app.getId();
		this.appType = app.getAppType().getName();
		this.doctor = app.getDoctor().getName() + " " + app.getDoctor().getSurname();
		this.patient = app.getPatient().getName() + " " + app.getPatient().getSurname();
		this.ordination = app.getOrdination().getName() + " " + app.getOrdination().getOrdinationNumber();
		this.date = app.getDate();
		this.startTime = app.getStartTime();
		this.endTime = app.getEndTime();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
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
	public Integer getStartTime() {
		return startTime;
	}
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	public Integer getEndTime() {
		return endTime;
	}
	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
	
	


}
