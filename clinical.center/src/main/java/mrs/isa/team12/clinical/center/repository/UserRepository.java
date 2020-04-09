package mrs.isa.team12.clinical.center.repository;

import java.util.Collection;

import mrs.isa.team12.clinical.center.model.RegisteredUser;

//Interface for User database access
public interface UserRepository {

	Collection<RegisteredUser> findAll();

	RegisteredUser create(RegisteredUser user);

	RegisteredUser findOne(String username);
	
	RegisteredUser update(RegisteredUser user);
	
	boolean delete(String username);
}
