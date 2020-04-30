package mrs.isa.team12.clinical.center.service.interfaces;

import mrs.isa.team12.clinical.center.model.AppointmentType;

public interface AppointmentTypeService {

	AppointmentType findOneByName(String name);
	
	AppointmentType save(AppointmentType at);
}
