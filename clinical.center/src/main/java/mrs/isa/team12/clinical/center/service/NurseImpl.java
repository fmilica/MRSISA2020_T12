package mrs.isa.team12.clinical.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Nurse findOneByEmail(String email) {
		return nurseRep.findOneByEmail(email);
	}

}
