package mrs.isa.team12.clinical.center.service;

import java.util.Collection;

import mrs.isa.team12.clinical.center.model.RegisteredUser;

public interface UserService {

	Collection<RegisteredUser> findAll();
	
	RegisteredUser findOne(String username);
	
	RegisteredUser create(RegisteredUser user);
	
	RegisteredUser update(RegisteredUser user);
	
	boolean delete(String username);
}
