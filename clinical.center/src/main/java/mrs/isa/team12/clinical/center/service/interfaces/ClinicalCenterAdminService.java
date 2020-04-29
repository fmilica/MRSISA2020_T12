
package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;

public interface ClinicalCenterAdminService {

	ClinicalCentreAdmin findOneByEmail(String email);
	
	List<ClinicalCentreAdmin> findAll();
	
	public ClinicalCentreAdmin save(ClinicalCentreAdmin cca);
}
