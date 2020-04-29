package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.ClinicalCentre;
import mrs.isa.team12.clinical.center.repository.ClinicalCentreRepository;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterService;

@Service
public class ClinicalCentreServiceImpl implements ClinicalCenterService{

private ClinicalCentreRepository clinicalCentreRepository;
	
	@Autowired
	public ClinicalCentreServiceImpl(ClinicalCentreRepository ccr) {
		this.clinicalCentreRepository = ccr;
	}
	
	@Override
	public List<ClinicalCentre> findAll() {
		return this.clinicalCentreRepository.findAll();
	}

	@Override
	public ClinicalCentre findOneByName(String name) {
		return this.clinicalCentreRepository.findOneByName(name);
	}

	@Override
	public ClinicalCentre save(ClinicalCentre cc) {
		return this.clinicalCentreRepository.save(cc);
	}
}
