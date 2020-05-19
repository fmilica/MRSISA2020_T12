package mrs.isa.team12.clinical.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.dto.NursePersonalInformationDto;
import mrs.isa.team12.clinical.center.model.Nurse;
import mrs.isa.team12.clinical.center.repository.NurseRepository;
import mrs.isa.team12.clinical.center.service.interfaces.NurseService;

@Service
public class NurseImpl implements NurseService {

	private NurseRepository nurseRep;
	
	@Autowired
	public NurseImpl(NurseRepository nurseRep) {
		this.nurseRep = nurseRep;
	}
	
	@Override
	public Nurse findOneById(Long id) {
		return nurseRep.findOneById(id);
	}

	@Override
	public Nurse findOneByEmail(String email) {
		return nurseRep.findOneByEmail(email);
	}

	@Override
	public Nurse updatePassword(Long id, String newPassword) {
		Nurse nurseToUpdate = nurseRep.findOneById(id);
		nurseToUpdate.setPassword(newPassword);
		
		return nurseRep.save(nurseToUpdate);
	}

	@Override
	public Nurse update(NursePersonalInformationDto editedProfile) {
		Nurse nurseToUpdate = nurseRep.findOneById(editedProfile.getId());
		nurseToUpdate.setName(editedProfile.getName());
		nurseToUpdate.setSurname(editedProfile.getSurname());
		nurseToUpdate.setGender(editedProfile.getGender());
		nurseToUpdate.setDateOfBirth(editedProfile.getDateOfBirth());
		nurseToUpdate.setPhoneNumber(editedProfile.getPhoneNumber());
		nurseToUpdate.setAddress(editedProfile.getAddress());
		nurseToUpdate.setCity(editedProfile.getCity());
		nurseToUpdate.setCountry(editedProfile.getCountry());
		//da li treba da se snimi mozda moze i bez snimanja tj sam da snimi ?
		return nurseRep.save(nurseToUpdate);
	}

}
