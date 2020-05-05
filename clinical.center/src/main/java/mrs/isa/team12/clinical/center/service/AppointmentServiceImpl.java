package mrs.isa.team12.clinical.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.repository.AppointmentRepository;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService{

	private AppointmentRepository appointmentRepository;
	
	@Autowired
	public AppointmentServiceImpl(AppointmentRepository atr) {
		this.appointmentRepository = atr;
	}
	
	@Override
	public Appointment save(Appointment a) {
		return appointmentRepository.save(a);
	}

}
