package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.Ordination;
import mrs.isa.team12.clinical.center.model.enums.OrdinationType;
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
	public Ordination findOneByNameAndOrdinationNumber(String name, Integer ordinationNumber) {
		return ordinationRep.findOneByNameAndOrdinationNumber(name, ordinationNumber);
	}
	
	@Override
	public List<Ordination> findAllByClinicId(Long clinicId) {
		return ordinationRep.findAllByClinicId(clinicId);
	}
	
	@Override
	public List<Ordination> findAllByClinicIdAndType(Long clinicId, OrdinationType type) {
		return ordinationRep.findAllByClinicIdAndType(clinicId, type);
	}
	
	@Override
	public List<Ordination> findAllByClinicIdAndNameContainingIgnoreCaseAndType(Long clinicId, String name,
			OrdinationType type) {
		return ordinationRep.findAllByClinicIdAndNameContainingIgnoreCaseAndType(clinicId, name, type);
	}

	@Override
	public List<Ordination> findAllByClinicIdAndOrdinationNumberAndType(Long clinicId, Integer ordinationNumber,
			OrdinationType type) {
		return ordinationRep.findAllByClinicIdAndOrdinationNumberAndType(clinicId, ordinationNumber, type);
	}

	@Override
	public List<Ordination> findAllByClinicIdAndNameContainingIgnoreCaseAndOrdinationNumberAndType(Long clinicId, String name,
			Integer ordinationNumber, OrdinationType type) {
		return ordinationRep.findAllByClinicIdAndNameContainingIgnoreCaseAndOrdinationNumberAndType(clinicId, name, ordinationNumber, type);
	}

	@Override
	public Ordination save(Ordination o) {
		return ordinationRep.save(o);
	}

	@Override
	public Ordination findOneById(Long id) {
		return ordinationRep.findOneById(id);
	}

}
