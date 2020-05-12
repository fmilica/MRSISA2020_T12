package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.RegistrationRequest;
import mrs.isa.team12.clinical.center.repository.RegistrationRequestRepository;
import mrs.isa.team12.clinical.center.service.interfaces.RegistrationRequestService;

@Service
public class RegistrationRequestServiceImpl implements RegistrationRequestService{

	private RegistrationRequestRepository requestRepository;
	
	@Autowired
	public RegistrationRequestServiceImpl(RegistrationRequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}
	
	@Override
	public RegistrationRequest findOneById(Long id) {
		return requestRepository.findOneById(id);
	}

	@Override
	public RegistrationRequest save(RegistrationRequest rr) {
		return requestRepository.save(rr);
	}

	@Override
	public List<RegistrationRequest> findAll() {
		return requestRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		requestRepository.deleteById(id);
	}
}
