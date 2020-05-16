package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.Prescription;

public interface PrescriptionService {
	List<Prescription> findAll();
	
	Prescription save(Prescription p);
}
