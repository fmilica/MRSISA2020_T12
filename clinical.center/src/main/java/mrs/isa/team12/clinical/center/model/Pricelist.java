package mrs.isa.team12.clinical.center.model;

import java.util.Set;

public class Pricelist {
	
	private Set<AppointmentType> appointmentTypes;

	public Pricelist() {
		// TODO Auto-generated constructor stub
	}

	public Pricelist(Set<AppointmentType> appointmentTypes) {
		super();
		this.appointmentTypes = appointmentTypes;
	}

	public Set<AppointmentType> getAppointmentTypes() {
		return appointmentTypes;
	}

	public void setAppointmentTypes(Set<AppointmentType> appointmentTypes) {
		this.appointmentTypes = appointmentTypes;
	}
	
}
