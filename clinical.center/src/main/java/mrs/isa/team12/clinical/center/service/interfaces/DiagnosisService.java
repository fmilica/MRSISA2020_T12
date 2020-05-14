package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.Diagnosis;

public interface DiagnosisService {
	
	List<Diagnosis> findAll();
	
	Diagnosis findOneByName(String name);
	
	Diagnosis save(Diagnosis d);
}
