package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.RegisteredUser;

public interface UserService {

	List<RegisteredUser> findAll();
	
	public RegisteredUser save(RegisteredUser ru);
}
