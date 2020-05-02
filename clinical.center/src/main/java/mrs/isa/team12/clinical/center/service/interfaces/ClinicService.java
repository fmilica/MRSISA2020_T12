package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.Clinic;

public interface ClinicService {
	
	public List<Clinic> findAll();
	
	public Clinic findOneById(Long id);
	
	public Clinic findOneByName(String name);

	public Clinic save(Clinic c);
}
