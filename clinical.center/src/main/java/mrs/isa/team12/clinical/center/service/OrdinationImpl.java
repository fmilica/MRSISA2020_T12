package mrs.isa.team12.clinical.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.Ordination;
import mrs.isa.team12.clinical.center.repository.OrdinationRepository;
import mrs.isa.team12.clinical.center.service.interfaces.OrdinationService;

@Service
public class OrdinationImpl implements OrdinationService {

	private OrdinationRepository ordinationRep;
	
	@Autowired
	public OrdinationImpl(OrdinationRepository ordinationRep) {
		this.ordinationRep = ordinationRep;
	}

	@Override
	public Ordination findOneByName(String name) {
		return ordinationRep.findOneByName(name);
	}

	@Override
	public Ordination save(Ordination o) {
		return ordinationRep.save(o);
	}

}
