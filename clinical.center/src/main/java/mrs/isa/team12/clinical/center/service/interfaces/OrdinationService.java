package mrs.isa.team12.clinical.center.service.interfaces;

import mrs.isa.team12.clinical.center.model.Ordination;

public interface OrdinationService {

	Ordination findOneByName(String name);
	
	Ordination save(Ordination o);
	
}
