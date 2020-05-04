package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.AppointmentType;

public interface AppointmentTypeService {

	AppointmentType findOneByName(String name);
	
	AppointmentType save(AppointmentType at);
	
	List<AppointmentType> findAll();
	
	List<AppointmentType> findAllByClinicId(Long clinicId);
	
	AppointmentType findOneByNameAndClinicId(String name, Long id);
}
