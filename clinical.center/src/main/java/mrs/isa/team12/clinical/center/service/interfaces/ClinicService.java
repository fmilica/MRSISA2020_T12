package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.Clinic;

public interface ClinicService {
	
	public List<Clinic> findAll();
	
	public List<Clinic> findAllByAppointmentTypeId(Long appTypeId);
	
	public List<Clinic> findAllByAppointmentTypeName(String appTypeName);
	
	public Clinic findOneById(Long id);
	
	public Clinic findOneByName(String name);

	public Clinic save(Clinic c);
}
