package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.repository.UserRepository;
import mrs.isa.team12.clinical.center.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	public void setUserRepository(UserRepository ur) {
		this.userRepository = ur;
	}

	@Override
	public List<RegisteredUser> findAll() {
		return userRepository.findAll();
	}
	
	@Override
	public RegisteredUser save(RegisteredUser ru) {
		return userRepository.save(ru);
	}

}
