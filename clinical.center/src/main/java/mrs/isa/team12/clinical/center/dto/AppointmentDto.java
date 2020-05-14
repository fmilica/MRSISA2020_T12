package mrs.isa.team12.clinical.center.dto;

import mrs.isa.team12.clinical.center.model.Appointment;

public class AppointmentDto {
	private Long id;
	
	public AppointmentDto() {}
	
	public AppointmentDto(Appointment a) {
		this.id = a.getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
