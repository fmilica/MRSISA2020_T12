package mrs.isa.team12.clinical.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.repository.PatientRepository;
import mrs.isa.team12.clinical.center.service.interfaces.PatientService;

@Service
public class PatientImpl implements PatientService {

	private PatientRepository patientRep;
	
	@Autowired
	public PatientImpl(PatientRepository patientRep) {
		this.patientRep = patientRep;
	}

	@Override
	public Patient findOneByEmail(String email) {
		return patientRep.findOneByEmail(email);
	}

}
