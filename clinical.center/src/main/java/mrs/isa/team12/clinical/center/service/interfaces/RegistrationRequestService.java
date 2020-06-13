package mrs.isa.team12.clinical.center.service.interfaces;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

import mrs.isa.team12.clinical.center.model.RegistrationRequest;

public interface RegistrationRequestService {
	
	RegistrationRequest save(RegistrationRequest rr);
	
	RegistrationRequest update(Long reqId, boolean approved) throws NoSuchElementException, ObjectOptimisticLockingFailureException;

	String delete(Long id) throws NoSuchElementException, ObjectOptimisticLockingFailureException;

	List<RegistrationRequest> findAll();
	
	RegistrationRequest findOneById(Long id);
}
