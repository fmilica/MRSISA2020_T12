package mrs.isa.team12.clinical.center.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrs.isa.team12.clinical.center.model.RegistrationRequest;
import mrs.isa.team12.clinical.center.repository.RegistrationRequestRepository;
import mrs.isa.team12.clinical.center.service.interfaces.RegistrationRequestService;

@Service
@Transactional(readOnly = true)
public class RegistrationRequestServiceImpl implements RegistrationRequestService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private RegistrationRequestRepository requestRepository;
	
	@Autowired
	public RegistrationRequestServiceImpl(RegistrationRequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}

	@Override
	@Transactional(readOnly = false)
	public RegistrationRequest save(RegistrationRequest rr) {
		logger.info("> create");
		RegistrationRequest r = requestRepository.save(rr);
		logger.info("< create");
		return r;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public RegistrationRequest update(Long reqId, boolean approved) {
		logger.info("> update id:{}", reqId);
		RegistrationRequest rr = this.findOneById(reqId);
		if(rr.getApproved() == true) {
			throw new ObjectOptimisticLockingFailureException(RegistrationRequest.class, rr);
		}
		rr.setApproved(true);
		rr.getUser().setActive(true);
		requestRepository.save(rr);
		logger.info("< update id:{}", rr.getId());
		return rr;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String delete(Long id) {
		logger.info("> delete id:{}", id);
		RegistrationRequest rr = requestRepository.findOneById(id);
		if(rr == null) {
			throw new NoSuchElementException();
		}
		rr.setActive(false);
		rr = requestRepository.save(rr);
		logger.info("< delete id:{}", id);
		return rr.getUser().getEmail();
	}
	
	@Override
	public List<RegistrationRequest> findAll() {
		logger.info("> findAll");
		List<RegistrationRequest> requests = requestRepository.findAll();
		logger.info("< findAll");
		return requests;
	}
	
	@Override
	public RegistrationRequest findOneById(Long id) {
		logger.info("> findOneById");
		RegistrationRequest rr = requestRepository.findOneById(id);
		logger.info("< findOneById");
		return rr;
	}
}
