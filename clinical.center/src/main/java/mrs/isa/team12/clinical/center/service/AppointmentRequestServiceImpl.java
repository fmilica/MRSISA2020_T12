package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.AppointmentRequest;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.repository.AppointmentRequestRepository;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentRequestService;

@Service
public class AppointmentRequestServiceImpl implements AppointmentRequestService{

	private AppointmentRequestRepository appointmentRequestRep;
	
	@Autowired
	public AppointmentRequestServiceImpl(AppointmentRequestRepository atr) {
		this.appointmentRequestRep = atr;
	}
	
	/*@Override
	public AppointmentRequest findOneByName(String name) {
		return appointmentRequestRep.findOneByName(name);
	}*/

	@Override
	public AppointmentRequest save(AppointmentRequest ar) {
		return appointmentRequestRep.save(ar);
	}

	@Override
	public AppointmentRequest findOneById(Long id) {
		return appointmentRequestRep.findOneById(id);
	}

	@Override
	public List<AppointmentRequest> findAllByClinic(Clinic clinic) {
		return appointmentRequestRep.findAllByClinic(clinic);
	}
}
