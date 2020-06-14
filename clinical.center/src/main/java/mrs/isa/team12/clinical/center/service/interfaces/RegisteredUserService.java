package mrs.isa.team12.clinical.center.service.interfaces;

import mrs.isa.team12.clinical.center.model.RegisteredUser;

public interface RegisteredUserService {

	RegisteredUser findOneByEmail(String email);
	
	RegisteredUser findOneBySecurityNumber(String number);
	
	RegisteredUser updateVerificationToken(RegisteredUser user, String token) throws Exception;
	
	RegisteredUser findOneByVerificationToken(String token);
		
	RegisteredUser save(RegisteredUser ru);
	
	RegisteredUser emailExists(String email);
}
