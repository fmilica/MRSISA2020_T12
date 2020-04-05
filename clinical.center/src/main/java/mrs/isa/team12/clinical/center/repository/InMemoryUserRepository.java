package mrs.isa.team12.clinical.center.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import mrs.isa.team12.clinical.center.model.RegisteredUser;

@Repository
public class InMemoryUserRepository implements UserRepository {

	private static ConcurrentMap<String, RegisteredUser> users = new ConcurrentHashMap<String, RegisteredUser>();
	
	@Override
	public Collection<RegisteredUser> findAll() {
		return InMemoryUserRepository.users.values();
	}

	@Override
	public RegisteredUser create(RegisteredUser user) {
		if (InMemoryUserRepository.users.containsKey(user.getUsername())) {
			return null;
		}
		
		InMemoryUserRepository.users.put(user.getUsername(), user);
		return user;
	}

	@Override
	public RegisteredUser findOne(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegisteredUser update(RegisteredUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String username) {
		// TODO Auto-generated method stub
		return false;
	}

}
