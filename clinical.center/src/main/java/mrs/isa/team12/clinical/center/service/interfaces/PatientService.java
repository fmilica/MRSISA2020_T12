package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Patient;

public interface PatientService {

	Patient findOneByEmail(String email);
	
	Patient save(Patient p);
	
	public void sendNotificaitionAsync(ClinicAdmin admin, Patient patient);
	
	public List<Patient> findAll();
	
	public List<Patient> filter(String name, String surname, String secNum);
	
	Patient findOneBySecurityNumber(String securityNumber);
	
	void deleteById(Long id);
}
