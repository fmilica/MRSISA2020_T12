package mrs.isa.team12.clinical.center.service.interfaces;

import mrs.isa.team12.clinical.center.model.Nurse;

public interface NurseService {

	Nurse findOneByEmail(String email);
	
}
