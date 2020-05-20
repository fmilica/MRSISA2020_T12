package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.Clinic;

public interface ClinicService {
	
	Clinic findOneById(Long id);
	
	Clinic save(Clinic c);
	
	List<Clinic> findAll();
	
	Clinic findOneByName(String name);
	
	List<Clinic> findAllByAppointmentTypeId(Long appTypeId);
	
	List<Clinic> findAllByAppointmentTypeName(String appTypeName);	
}
