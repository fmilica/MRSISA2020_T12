package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.AppointmentRequest;
import mrs.isa.team12.clinical.center.model.Clinic;

public interface AppointmentRequestService {

	AppointmentRequest findOneById(Long id);
	
	AppointmentRequest save(AppointmentRequest ar);
	
	List<AppointmentRequest> findAllByClinic(Clinic clinic);
}
