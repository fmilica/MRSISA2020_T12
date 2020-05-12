package mrs.isa.team12.clinical.center.service.interfaces;
import java.util.List;

import mrs.isa.team12.clinical.center.model.RegistrationRequest;

public interface RegistrationRequestService {
	
	List<RegistrationRequest> findAll();
	
	RegistrationRequest findOneById(Long id);
	
	RegistrationRequest save(RegistrationRequest rr);
	
	void deleteById(Long id);
}
