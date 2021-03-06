package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.dto.NursePersonalInformationDto;
import mrs.isa.team12.clinical.center.model.Nurse;

public interface NurseService {
	
	Nurse save(Nurse nurse);

	Nurse findOneById(Long id);
	
	Nurse findOneByEmail(String email);
	
	List<Nurse> findAll();
	
	Nurse updatePassword(Long id, String newPassword) throws Exception;
	
	Nurse update(NursePersonalInformationDto editedProfile) throws Exception;
	
	List<Nurse> findAllByClinicId(Long id);
	
	void delete(Nurse n);
}
