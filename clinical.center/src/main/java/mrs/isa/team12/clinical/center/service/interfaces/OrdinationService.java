package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.Ordination;

public interface OrdinationService {

	Ordination findOneByName(String name);
	
	List<Ordination> findAllByClinicId(Long clinicId);
	
	Ordination save(Ordination o);
	
}
