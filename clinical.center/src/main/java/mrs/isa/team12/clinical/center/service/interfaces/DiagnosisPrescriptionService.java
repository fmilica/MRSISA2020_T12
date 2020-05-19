package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.DiagnosePerscription;

public interface DiagnosisPrescriptionService {
	
	List<DiagnosePerscription> findAll();
	
	DiagnosePerscription save(DiagnosePerscription ds);
	
	DiagnosePerscription findOneById(Long id);
}
