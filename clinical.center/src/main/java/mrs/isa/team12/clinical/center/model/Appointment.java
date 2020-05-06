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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "appointment")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "app_date", unique = false, nullable = false )
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(name = "app_start_time", unique = false, nullable = false )
	@Temporal(TemporalType.TIME)
	private Date startTime;
	
	@Column(name = "app_end_time", unique = false, nullable = false)
	@Temporal(TemporalType.TIME)
	private Date endTime;
	
	//type
	
	@Column(name = "discount", unique = false, nullable = true )
	private Double discount;
	
	@Column(name = "confirmed", unique = false, nullable = false )
	private Boolean confirmed;

	@ManyToOne
	@JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = true)
	//@JsonBackReference
	@JsonBackReference("patient-apps")
	private Patient patient;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "medical_report_id")
	@JsonBackReference
	private MedicalReport medicalReport;
	
	@ManyToOne
	@JoinColumn(name="ordination_id", referencedColumnName = "id", nullable = true)
	@JsonBackReference
	private Ordination ordination;
	
	@ManyToOne
	@JoinColumn(name = "clinic_id", referencedColumnName = "id", nullable = false)
	//@JsonBackReference
	@JsonBackReference("clinic-apps")
	private Clinic clinic;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
	@JsonBackReference("doctor-apps")
	private Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name= "medical_record_id", referencedColumnName = "id", nullable = true)
	@JsonBackReference
	private MedicalRecords medicalRecords;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name= "app_type", referencedColumnName = "id", nullable = true)
	@JsonBackReference("appType-apps")
	private AppointmentType appType;
	/*
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "appointment_id")
	@JsonBackReference("appReq-app")
	private AppointmentRequest request;
*/
	public Appointment() {}
	/*
	public Appointment(Long id, Date date, Date startTime, Double discount, Boolean confirmed, Patient patient,
			MedicalReport medicalReport, Ordination ordination, Clinic clinic, Doctor doctor,
			MedicalRecords medicalRecords, AppointmentType appType, AppointmentRequest request) {
		super();
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.endTime = new Date(startTime.getTime() + appType.getDuration()*3600*1000);
		this.discount = discount;
		this.confirmed = confirmed;
		this.patient = patient;
		this.medicalReport = medicalReport;
		this.ordination = ordination;
		this.clinic = clinic;
		this.doctor = doctor;
		this.medicalRecords = medicalRecords;
		this.appType = appType;
		this.request = request;
	}*/

	public Appointment(Date date, Date startTime, AppointmentType type, Double discount,
			Boolean confirmed, Patient patient, MedicalReport medicalReport, Ordination ordination, Doctor doctor) {
		super();
		this.date = date;
		this.startTime = startTime;
		this.endTime = new Date(startTime.getTime() + appType.getDuration()*3600*1000);
		this.appType = type;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public AppointmentType getAppType() {
		return appType;
	}

	public void setAppType(AppointmentType appType) {
		this.appType = appType;
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

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MedicalRecords getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(MedicalRecords medicalRecords) {
		this.medicalRecords = medicalRecords;
	}
/*
	public AppointmentRequest getRequest() {
		return request;
	}

	public void setRequest(AppointmentRequest request) {
		this.request = request;
	}
*/
	@Override
	public String toString() {
		return "Appointment [id=" + id + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", discount=" + discount + ", confirmed=" + confirmed + ", patient=" + patient + ", medicalReport="
				+ medicalReport + ", ordination=" + ordination + ", clinic=" + clinic + ", doctor=" + doctor
				+ ", medicalRecords=" + medicalRecords + ", appType=" + appType + "]";
	}
}
