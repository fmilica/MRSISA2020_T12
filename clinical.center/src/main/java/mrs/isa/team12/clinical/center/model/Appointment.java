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

@Entity
@Table(name = "appointment")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "date", unique = false, nullable = false )
	private Date date;
	
	@Column(name = "time", unique = false, nullable = false )
	private Date time;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "type_id")
	private AppointmentType type;
	
	@Column(name = "discount", unique = false, nullable = true )
	private Double discount;
	
	@Column(name = "confirmed", unique = false, nullable = false )
	private Boolean confirmed;

	@ManyToOne
	@JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = true)
	private Patient patient;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "medical_report_id")
	private MedicalReport medicalReport;
	
	@ManyToOne
	@JoinColumn(name="ordination_id", referencedColumnName = "id", nullable = true)
	private Ordination ordination;
	
	@ManyToOne
	@JoinColumn(name = "clinic_id", referencedColumnName = "id", nullable = false)
	private Clinic clinic;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
	private Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name= "medical_record_id", referencedColumnName = "id", nullable = false)
	private MedicalRecords medicalRecords;

	public Appointment() {}
	
	public Appointment(Date date, Date time, AppointmentType type, Double discount,
			Boolean confirmed, Patient patient, MedicalReport medicalReport, Ordination ordination, Doctor doctor) {
		super();
		this.date = date;
		this.time = time;
		this.type = type;
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
