package mrs.isa.team12.clinical.center.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrs.isa.team12.clinical.center.dto.AppointmentTypeDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.repository.AppointmentTypeRepository;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;

@Service
@Transactional(readOnly = true)
public class AppointmentTypeImpl implements AppointmentTypeService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private AppointmentTypeRepository appointmentTypeRep;
		
	@Autowired
	public AppointmentTypeImpl(AppointmentTypeRepository appointmentTypeRep) {
		this.appointmentTypeRep = appointmentTypeRep;
	}
	
	//cuvanje u bazu treba da bude pesimisticko je l ? posto on jos ne postoji, to da je pitamo jer ja ne znam kako to
	// bavo :3
	/*@Transactional(readOnly = false)
	@Override
	public AppointmentType save(AppointmentType at) {
		logger.info("> create");
		AppointmentType appType = appointmentTypeRep.save(at);
		logger.info("< create");
		return appType;
	}*/

	@Override
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
	public AppointmentType save(AppointmentType appType, Clinic c) {
		logger.info("> create");
		AppointmentType at = findOneByNameAndClinicId(appType.getName(), c.getId());
		if (at != null) {
			logger.info("< EntityExistsException");
			throw new EntityExistsException();
		}
		appType.setClinic(c);
		appType.setActive(true);
		AppointmentType saved = appointmentTypeRep.save(appType);
		logger.info("< create");
		return saved;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public AppointmentType delete(Long appTypeId) {
		logger.info("> delete id:{}", appTypeId);
		AppointmentType appType = this.findOneById(appTypeId);
		if (appType == null) {
			throw new NoSuchElementException();
		}
		// provera da li postoji zakazan pregled za ovaj tip
		for(Appointment a : appType.getAppointments()) {
			if(!a.getFinished()) {
				return null;
			}
		}
		appType.setActive(false);
		appType = appointmentTypeRep.save(appType);
		logger.info("< delete id:{}", appType.getId());
		return appType;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public AppointmentType update(AppointmentTypeDto edited, Long clinicId) {
		logger.info("> update id:{}", edited.getId());
		AppointmentType forUpdate = appointmentTypeRep.findOneById(edited.getId());
		AppointmentType newAppType = this.findOneByNameAndClinicId(edited.getName(), clinicId);
		if(newAppType == null || forUpdate.getName().equals(newAppType.getName())) {
			forUpdate.setName(edited.getName());
			forUpdate.setDuration(edited.getDuration());
			forUpdate.setPrice(edited.getPrice());
			AppointmentType updated = appointmentTypeRep.save(forUpdate);
			logger.info("< update id:{}", forUpdate.getId());
			return updated;
		}
		throw new EntityExistsException();
	}

	@Override
	public AppointmentType findOneById(Long id) {
		logger.info("> findOneById id:{}", id);
		AppointmentType appType = appointmentTypeRep.findOneById(id);
		logger.info("< findOneById id:{}", id);
		return appType;
	}
	
	@Override
	public AppointmentType findOneByName(String name) {
		logger.info("> findOneByName name:{}", name);
		AppointmentType appType = appointmentTypeRep.findOneByName(name);
		logger.info("< findOneByName name:{}", name);
		return appType;
	}
	
	@Override
	public AppointmentType findOneByNameAndClinicId(String name, Long id) {
		logger.info("> findOneByNameAndClinicId name:{},{}", name, id);
		AppointmentType appType = appointmentTypeRep.findOneByNameAndClinicId(name, id);
		logger.info("< findOneByNameAndClinicId name:{},{}", name, id);
		return appType;
	}


	@Override
	public List<AppointmentType> findAll() {
		logger.info("> findAll");
		List<AppointmentType> appTypes = appointmentTypeRep.findAll();
		logger.info("< findAll");
		return appTypes;
	}
	
	
	@Override
	public List<AppointmentType> findAllByName(String name) {
		logger.info("> findAllByName");
		List<AppointmentType> appTypes = appointmentTypeRep.findAllByName(name);
		logger.info("< findAllByName");
		return appTypes;
	}

	
	@Override
	public List<AppointmentType> findAllByClinicId(Long clinicId) {
		logger.info("> findAllByClinicId");
		List<AppointmentType> appTypes = appointmentTypeRep.findAllByClinicId(clinicId);
		logger.info("< findAllByClinicId");
		return appTypes;
	}
	
	@Override
	public Set<AppointmentType> findAllByClinicIdAndNameIn(Long clinicId, List<String> appTypeNames) {
		logger.info("> findAllByClinicIdAndNameIn");
		Set<AppointmentType> appTypes = appointmentTypeRep.findAllByClinicIdAndNameIn(clinicId, appTypeNames);
		logger.info("< findAllByClinicIdAndNameIn");
		return appTypes;
	}
	
}
