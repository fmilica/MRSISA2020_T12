package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Clinic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="name", unique=false, nullable=false)
	private String name;
	
	@Column(name="address", unique=false, nullable=false)
	private String address;
	
	@Column(name="city", unique=false, nullable=false)
	private String city;
	
	@Column(name="country", unique=false, nullable=false)
	private String country;
	
	@Column(name="description", unique=false, nullable=false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "clinical_center_id", referencedColumnName = "id", nullable = false)
	private ClinicalCentre clinicalCentre;
		
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "pricelist_id")
	private Pricelist priceList;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "report_id")
	private Report report;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "diagnose_perscription_id")
	private DiagnosePerscription diagnosePerscription;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "clinic")
	private Set<Doctor> doctors;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "clinic")
	private Set<Nurse> nurses;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY)
	private Set<Patient> patients;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "clinic")
	private Set<Appointment> appointments;
	
	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="clinic")
	private Set<ClinicAdmin> admins;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "clinic")
	private Set<Ordination> ordinations;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "clinic")
	private Set<AppointmentRequest> appointmentRequests;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY)
	private Set<LeaveRequest> leaveRequests;
	
	public void add(ClinicAdmin clinicAdmin) {
		if (clinicAdmin.getClinic() != null) {
			clinicAdmin.getClinic().getAdmins().remove(clinicAdmin);
		}
		clinicAdmin.setClinic(this);
		this.getAdmins().add(clinicAdmin);
	}

	public Clinic() {}

	public Clinic(String name, String address, String city, String country, String description, Pricelist priceList,
			Set<Doctor> doctors, Set<Nurse> nurses, Set<Patient> patients, Set<Appointment> appointments,
			Set<Ordination> ordinations, Report report, DiagnosePerscription diagnosePerscription,
			Set<LeaveRequest> leaveRequests, Set<AppointmentRequest> appointmentRequests, ClinicalCentre clinicalCentre,
			Set<ClinicAdmin> admins) {
		super();
		this.name = name;
		this.address = address;
		this.city = city;
		this.country = country;
		this.description = description;
		this.priceList = priceList;
		this.doctors = doctors;
		this.nurses = nurses;
		this.patients = patients;
		this.appointments = appointments;
		this.ordinations = ordinations;
		this.report = report;
		this.diagnosePerscription = diagnosePerscription;
		this.leaveRequests = leaveRequests;
		this.appointmentRequests = appointmentRequests;
		this.clinicalCentre = clinicalCentre;
		this.admins = admins;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Pricelist getPriceList() {
		return priceList;
	}

	public void setPriceList(Pricelist priceList) {
		this.priceList = priceList;
	}

	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Set<Nurse> getNurses() {
		return nurses;
	}

	public void setNurses(Set<Nurse> nurses) {
		this.nurses = nurses;
	}

	public Set<Patient> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Set<Ordination> getOrdinations() {
		return ordinations;
	}

	public void setOrdinations(Set<Ordination> ordinations) {
		this.ordinations = ordinations;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public DiagnosePerscription getDiagnosePerscription() {
		return diagnosePerscription;
	}

	public void setDiagnosePerscription(DiagnosePerscription diagnosePerscription) {
		this.diagnosePerscription = diagnosePerscription;
	}

	public Set<LeaveRequest> getLeaveRequests() {
		return leaveRequests;
	}

	public void setLeaveRequests(Set<LeaveRequest> leaveRequests) {
		this.leaveRequests = leaveRequests;
	}

	public Set<AppointmentRequest> getAppointmentRequests() {
		return appointmentRequests;
	}

	public void setAppointmentRequests(Set<AppointmentRequest> appointmentRequests) {
		this.appointmentRequests = appointmentRequests;
	}

	public ClinicalCentre getClinicalCentre() {
		return clinicalCentre;
	}

	public void setClinicalCentre(ClinicalCentre clinicalCentre) {
		this.clinicalCentre = clinicalCentre;
	}
	public Set<ClinicAdmin> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<ClinicAdmin> admins) {
		this.admins = admins;
	}
	
	

}
