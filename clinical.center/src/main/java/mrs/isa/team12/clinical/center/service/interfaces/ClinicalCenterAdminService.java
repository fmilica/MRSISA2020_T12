
package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import org.springframework.scheduling.annotation.Async;

import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.model.RegisteredUser;

public interface ClinicalCenterAdminService {

	ClinicalCentreAdmin findOneByEmail(String email);
	
	List<ClinicalCentreAdmin> findAll();
	
	public ClinicalCentreAdmin save(ClinicalCentreAdmin cca);
	
	@Async
	public void sendNotificaitionAsync(ClinicalCentreAdmin admin, RegisteredUser user, String description, boolean acceptance);
}
