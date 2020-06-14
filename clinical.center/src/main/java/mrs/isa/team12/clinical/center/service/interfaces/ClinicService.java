package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

import mrs.isa.team12.clinical.center.dto.ClinicDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.Patient;

public interface ClinicService {
	
	Clinic findOneById(Long id);
	
	Clinic save(Clinic c);
	
	Clinic update(ClinicDto editedClinic) throws ObjectOptimisticLockingFailureException, EntityExistsException;
	
	Clinic update(Clinic c, Patient p);
	
	Clinic update(Long clinicId, Appointment a);
	
	Clinic updateRating(Clinic c);
	
	List<Clinic> findAll();
	
	Clinic findOneByName(String name);
	
	List<Clinic> findAllByAppointmentTypeId(Long appTypeId);
	
	List<Clinic> findAllByAppointmentTypeName(String appTypeName);	
}
