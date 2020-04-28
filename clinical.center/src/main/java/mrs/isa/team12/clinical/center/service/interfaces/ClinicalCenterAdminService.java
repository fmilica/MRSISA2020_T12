package mrs.isa.team12.clinical.center.service.interfaces;

import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;

public interface ClinicalCenterAdminService {

	ClinicalCentreAdmin findOneByEmail(String email);
	
}
