package mrs.isa.team12.clinical.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.repository.AppointmentTypeRepository;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;

@Service
public class AppointmentTypeImpl implements AppointmentTypeService {

	private AppointmentTypeRepository appointmentTypeRep;
	
	@Autowired
	public AppointmentTypeImpl(AppointmentTypeRepository appointmentTypeRep) {
		this.appointmentTypeRep = appointmentTypeRep;
	}
	
	@Override
	public AppointmentType findOneByName(String name) {
		return appointmentTypeRep.findOneByName(name);
	}

	@Override
	public AppointmentType save(AppointmentType at) {
		return appointmentTypeRep.save(at);
	}

}
