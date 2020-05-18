package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import org.springframework.scheduling.annotation.Async;

import mrs.isa.team12.clinical.center.dto.ClinicAdminPersonalInformationDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Patient;

public interface ClinicAdminService {
	
	ClinicAdmin updatePassword(Long id, String newPassword);
	
	ClinicAdmin update(ClinicAdminPersonalInformationDto editedProfile);
	
	List<ClinicAdmin> findAll();
	
	List<ClinicAdmin> findAllByClinicId(Long clinicId);
	
	ClinicAdmin save(ClinicAdmin ca);
	
	ClinicAdmin findOneByEmail(String email);
	
	ClinicAdmin findOneById(Long id);
	
	@Async
	public void sendNotificaitionAsync(ClinicAdmin admin, Patient patient,Appointment appointment, boolean acceptance);
}
