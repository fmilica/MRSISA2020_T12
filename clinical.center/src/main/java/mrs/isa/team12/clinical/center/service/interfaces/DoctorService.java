package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.Doctor;

public interface DoctorService {
	
	Doctor findOneByEmail(String email);
	
	Doctor save(Doctor d);
	
	List<Doctor> findAll();
	
	List<Doctor> findAllByClinicId(Long id);
	
	Doctor findOneById(Long id);
	
	List<Doctor> findAllByAppointmentTypes(AppointmentType a);
	
	List<Doctor> findAllByAppointmentTypesIn(List<AppointmentType> types);
	
	List<Doctor> findAllByClinicAndAppointmentTypesIn(Clinic clinic, List<AppointmentType> types);
}
