package mrs.isa.team12.clinical.center.service.interfaces;

import mrs.isa.team12.clinical.center.model.AppointmentRequest;

public interface AppointmentRequestService {

	AppointmentRequest findOneById(Long id);
	
	AppointmentRequest save(AppointmentRequest ar);
}
