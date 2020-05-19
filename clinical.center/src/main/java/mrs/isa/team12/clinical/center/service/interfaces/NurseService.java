package mrs.isa.team12.clinical.center.service.interfaces;

import mrs.isa.team12.clinical.center.dto.NursePersonalInformationDto;
import mrs.isa.team12.clinical.center.model.Nurse;

public interface NurseService {

	Nurse findOneById(Long id);
	
	Nurse findOneByEmail(String email);
	
	Nurse updatePassword(Long id, String newPassword);
	
	Nurse update(NursePersonalInformationDto editedProfile);
	
}
