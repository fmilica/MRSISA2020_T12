package mrs.isa.team12.clinical.center.model;

import java.util.Set;

public class MedicalReport {
	
	private String diagnosis;
	private String description;
	
	private Appointment appointment;
	
	private Set<Prescription> prescriptions;
	

	public MedicalReport() {
		super();
	}

	public MedicalReport(String diagnosis, String description, Appointment appointment,
			Set<Prescription> prescriptions) {
		super();
		this.diagnosis = diagnosis;
		this.description = description;
		this.appointment = appointment;
		this.prescriptions = prescriptions;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Set<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}
	
	

}
