package mrs.isa.team12.clinical.center.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.repository.InMemoryUserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private /*final*/ InMemoryUserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(InMemoryUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public Collection<RegisteredUser> findAll() {
		Collection<RegisteredUser> users = userRepository.findAll();
		return users;
	}

	@Override
	public RegisteredUser findOne(String username) {
		RegisteredUser user = userRepository.findOne(username);
		return user;
	}

	@Override
	public RegisteredUser create(RegisteredUser user) {
		RegisteredUser newUser = userRepository.create(user);
		if (newUser == null) {
			return null;
		}
		return newUser;
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
