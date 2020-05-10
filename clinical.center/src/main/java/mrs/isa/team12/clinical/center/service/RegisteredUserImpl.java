package mrs.isa.team12.clinical.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.repository.RegisteredUserRepository;
import mrs.isa.team12.clinical.center.service.interfaces.RegisteredUserService;

@Service
public class RegisteredUserImpl implements RegisteredUserService {

	private RegisteredUserRepository rep;
	
	@Autowired
	public RegisteredUserImpl(RegisteredUserRepository rep) {
		super();
		this.rep = rep;
	}

	@Override
	public RegisteredUser findOneByEmail(String email) {
		return rep.findOneByEmail(email);
	}

	@Override
	public RegisteredUser findOneBySecurityNumber(String number) {
		return rep.findOneBySecurityNumber(number);
	}

}
