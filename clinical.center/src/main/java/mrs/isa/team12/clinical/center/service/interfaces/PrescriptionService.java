package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.Prescription;

public interface PrescriptionService {
	
	List<Prescription> findAll();
	
	List<Prescription> findAllByDiagnosisId(Long id);
	
	Prescription save(Prescription p);
	
	Prescription findOneById(Long id);
	
	Prescription findOneByMedicine(String medicine);
}
