package mrs.isa.team12.clinical.center.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.Patient;

public class PatientProfileDto {
	
	private String name;
	private String surname;
	private String email;
	private String gender;
	private String dateOfBirth;
	private String phoneNumber;
	private String securityNumber;
	private String address;
	private String city;
	private String country;
	private MedicalRecordDto medicalRecords;
	private AppointmentDto appointment;
	
	
	public PatientProfileDto(Patient patient) {
		this.name = patient.getName();
		this.surname = patient.getSurname();
		this.email = patient.getEmail();
		this.gender = patient.getGender();
		this.dateOfBirth = patient.getDateOfBirth();
		this.phoneNumber = patient.getPhoneNumber();
		this.securityNumber = patient.getSecurityNumber();
		this.address = patient.getAddress();
		this.city = patient.getCity();
		this.country = patient.getCountry();
		this.medicalRecords = null;
		for (Appointment a : patient.getAppointments()) {
			if(currentApp(a)) {
				this.appointment = new AppointmentDto(a);
				break;
			}
		}
	}

	public PatientProfileDto(Patient patient, MedicalRecordDto medicalRecords) {
		this.name = patient.getName();
		this.surname = patient.getSurname();
		this.email = patient.getEmail();
		this.gender = patient.getGender();
		this.dateOfBirth = patient.getDateOfBirth();
		this.phoneNumber = patient.getPhoneNumber();
		this.securityNumber = patient.getSecurityNumber();
		this.address = patient.getAddress();
		this.city = patient.getCity();
		this.country = patient.getCountry();
		this.medicalRecords = medicalRecords;
		for (Appointment a : patient.getAppointments()) {
			if(currentApp(a)) {
				this.appointment = new AppointmentDto(a);
				break;
			}
		}
	}
	
	private boolean currentApp(Appointment a) {
		Date current = new Date();
		if(current.getDate() == a.getDate().getDate() && (current.getHours()+1 >= a.getStartTime() ) && (current.getHours()+1 <= a.getEndTime())) {
			return true;
		}
		return false;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSecurityNumber() {
		return securityNumber;
	}
	public void setSecurityNumber(String securityNumber) {
		this.securityNumber = securityNumber;
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
	public MedicalRecordDto getMedicalRecords() {
		return medicalRecords;
	}
	

	public AppointmentDto getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentDto appointment) {
		this.appointment = appointment;
	}

	public void setMedicalRecords(MedicalRecordDto medicalRecords) {
		this.medicalRecords = medicalRecords;
	}

	@Override
	public String toString() {
		return "PatientProfileDto [name=" + name + ", surname=" + surname + ", email=" + email + ", gender=" + gender
				+ ", dateOfBirth=" + dateOfBirth + ", phoneNumber=" + phoneNumber + ", securityNumber=" + securityNumber
				+ ", address=" + address + ", city=" + city + ", country=" + country + "]";
	}
}
