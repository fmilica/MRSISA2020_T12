package mrs.isa.team12.clinical.center.service;

import java.util.List;

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
	public List<AppointmentType> findAllByName(String name) {
		return appointmentTypeRep.findAllByName(name);
	}

	@Override
	public AppointmentType save(AppointmentType at) {
		return appointmentTypeRep.save(at);
	}

	@Override
	public List<AppointmentType> findAll() {
		return appointmentTypeRep.findAll();
	}
	
	@Override
	public List<AppointmentType> findAllByClinicId(Long clinicId) {
		return appointmentTypeRep.findAllByClinicId(clinicId);
	}

	@Override
	public AppointmentType findOneByNameAndClinicId(String name, Long id) {
		return appointmentTypeRep.findOneByNameAndClinicId(name, id);
	}

}
