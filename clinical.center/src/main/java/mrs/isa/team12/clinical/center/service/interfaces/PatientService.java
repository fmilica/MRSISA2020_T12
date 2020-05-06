package mrs.isa.team12.clinical.center.service.interfaces;

import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Patient;

public interface PatientService {

	Patient findOneByEmail(String email);
	
	Patient save(Patient p);
	
	public void sendNotificaitionAsync(ClinicAdmin admin, Patient patient);
}
