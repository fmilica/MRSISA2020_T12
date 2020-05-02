package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.Doctor;

public interface DoctorService {
	
	Doctor findOneByEmail(String email);
	
	Doctor save(Doctor d);
	
	List<Doctor> findAll();
	
	List<Doctor> findAllByClinicId(Long id);
	
	Doctor getDoctorById(Long id);
}
