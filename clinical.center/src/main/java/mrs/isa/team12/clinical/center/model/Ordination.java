package mrs.isa.team12.clinical.center.model;

import java.util.Set;

import mrs.isa.team12.clinical.center.model.enums.OrdinationType;

public class Ordination {
	
	private Integer number;
	private OrdinationType type;
	private String name;
	
	private Clinic clinic;
	
	private Set<Appointment> appointments;

	public Ordination() {
		// TODO Auto-generated constructor stub
	}
	
	public Ordination(Integer number, OrdinationType type, String name, Clinic clinic, Set<Appointment> appointments) {
		super();
		this.number = number;
		this.type = type;
		this.name = name;
		this.clinic = clinic;
		this.appointments = appointments;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public OrdinationType getType() {
		return type;
	}

	public void setType(OrdinationType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	

}
