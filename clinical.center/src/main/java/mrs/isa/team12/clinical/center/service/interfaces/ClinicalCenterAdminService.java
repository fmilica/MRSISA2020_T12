
package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import org.springframework.scheduling.annotation.Async;

import mrs.isa.team12.clinical.center.dto.ClinicalCentreAdminPersonalInformationDto;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.model.RegisteredUser;

public interface ClinicalCenterAdminService {
	
	ClinicalCentreAdmin findOneById(Long id);

	ClinicalCentreAdmin findOneByEmail(String email);
	
	ClinicalCentreAdmin update(ClinicalCentreAdminPersonalInformationDto editedProfile);
	
	List<ClinicalCentreAdmin> findAll();
	
	public ClinicalCentreAdmin save(ClinicalCentreAdmin cca);
	
	@Async
	public void sendNotificaitionAsync(ClinicalCentreAdmin admin, RegisteredUser user, String description, boolean acceptance);
}
