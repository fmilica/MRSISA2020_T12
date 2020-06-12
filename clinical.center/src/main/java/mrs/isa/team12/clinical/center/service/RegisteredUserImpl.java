package mrs.isa.team12.clinical.center.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.repository.RegisteredUserRepository;
import mrs.isa.team12.clinical.center.service.interfaces.RegisteredUserService;

@Service
@Transactional(readOnly = true)
public class RegisteredUserImpl implements RegisteredUserService {

	//private AppointmentRepository appointmentRepository;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private RegisteredUserRepository rep;
	
	@Autowired
	public RegisteredUserImpl(RegisteredUserRepository rep) {
		super();
		this.rep = rep;
	}

	@Override
	public RegisteredUser findOneByEmail(String email) {
		logger.info("> findOneByEmail");
		RegisteredUser ru = rep.findOneByEmail(email);
		logger.info("< findOneByEmail");
		return ru;
		
	}

	@Override
	public RegisteredUser findOneBySecurityNumber(String number) {
		logger.info("> findOneBySecurityNumber");
		RegisteredUser ru = rep.findOneBySecurityNumber(number);
		logger.info("< findOneBySecurityNumber");
		return ru;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public RegisteredUser updateVerificationToken(RegisteredUser user, String token) throws Exception {
		logger.info("< updateVerificationToken id:{}", user.getId());
		RegisteredUser toUpdate = rep.findOneById(user.getId());
		toUpdate.setVerificationToken(token);
		RegisteredUser updatedPatient = rep.save(toUpdate);
		logger.info("> updateVerificationToken id:{}", user.getId());
		return updatedPatient;
	}


	@Override
	public RegisteredUser findOneByVerificationToken(String token) {
		logger.info("> findOneByVerificationToken");
		Patient p = rep.findOneByToken(token);
		logger.info("< findOneByVerificationToken");
		return p;
	}

	@Transactional(readOnly = false)
	@Override
	public RegisteredUser save(RegisteredUser ru) {
		RegisteredUser r = rep.save(ru);
		return r;
	}
}
