package mrs.isa.team12.clinical.center.model;

import java.util.Date;

public class Appointment {
	
	private Date date;
	private Date time;
	private AppointmentType type;
	private Integer duration;
	private Double price;
	private Double discount;
	private Boolean confirmed;
	
	private Patient patient;
	private MedicalReport medicalReport;
	private Ordination ordination;
	private Doctor doctor;

	public Appointment() {
		// TODO Auto-generated constructor stub
	}
	
	public Appointment(Date date, Date time, AppointmentType type, Integer duration, Double price, Double discount,
			Boolean confirmed, Patient patient, MedicalReport medicalReport, Ordination ordination, Doctor doctor) {
		super();
		this.date = date;
		this.time = time;
		this.type = type;
		this.duration = duration;
		this.price = price;
		this.discount = discount;
		this.confirmed = confirmed;
		this.patient = patient;
		this.medicalReport = medicalReport;
		this.ordination = ordination;
		this.doctor = doctor;
	}




	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public AppointmentType getType() {
		return type;
	}

	public void setType(AppointmentType type) {
		this.type = type;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public MedicalReport getMedicalReport() {
		return medicalReport;
	}

	public void setMedicalReport(MedicalReport medicalReport) {
		this.medicalReport = medicalReport;
	}

	public Ordination getOrdination() {
		return ordination;
	}

	public void setOrdination(Ordination ordination) {
		this.ordination = ordination;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	
}
