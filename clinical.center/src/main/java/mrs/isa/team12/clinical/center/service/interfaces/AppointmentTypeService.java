package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;
import java.util.Set;

import mrs.isa.team12.clinical.center.model.AppointmentType;

public interface AppointmentTypeService {

	AppointmentType findOneByName(String name);
	
	AppointmentType save(AppointmentType at);
	
	List<AppointmentType> findAll();
	
	List<AppointmentType> findAllByName(String name);
	
	List<AppointmentType> findAllByClinicId(Long clinicId);
	
	AppointmentType findOneByNameAndClinicId(String name, Long id);
	
	Set<AppointmentType> findAllByClinicIdAndNameIn(Long clinicId, List<String> appTypeNames);
}
