package mrs.isa.team12.clinical.center.service.interfaces;

import mrs.isa.team12.clinical.center.model.MedicalRecords;

public interface MedicalRecordsService {
	
	MedicalRecords save(MedicalRecords mr);
	
	MedicalRecords findOneById(Long id);
}
