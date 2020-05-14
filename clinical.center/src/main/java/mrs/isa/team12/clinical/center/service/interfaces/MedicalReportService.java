package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.MedicalReport;

public interface MedicalReportService {
	
	MedicalReport save(MedicalReport mr);
	
	List<MedicalReport> findAll();
}
