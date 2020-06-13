package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

import mrs.isa.team12.clinical.center.dto.AppointmentTypeDto;
import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;

public interface AppointmentTypeService {
	
	// ne treba da postoji
	//AppointmentType save(AppointmentType at);
	
	AppointmentType save(AppointmentType at, Clinic c);
	
	AppointmentType delete(Long appTypeId) throws ObjectOptimisticLockingFailureException, NoSuchElementException;
	
	AppointmentType update(AppointmentTypeDto edited, Long clinicId) throws ObjectOptimisticLockingFailureException, EntityExistsException;
	
	AppointmentType findOneById(Long id);
	
	AppointmentType findOneByName(String name);
	
	AppointmentType findOneByNameAndClinicId(String name, Long id);
	
	List<AppointmentType> findAll();
	
	List<AppointmentType> findAllByName(String name);
	
	List<AppointmentType> findAllByClinicId(Long clinicId);
	
	Set<AppointmentType> findAllByClinicIdAndNameIn(Long clinicId, List<String> appTypeNames);
}
