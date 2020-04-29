package mrs.isa.team12.clinical.center.service.interfaces;

import mrs.isa.team12.clinical.center.model.Doctor;

public interface DoctorService {
	
	Doctor findOneByEmail(String email);
}
