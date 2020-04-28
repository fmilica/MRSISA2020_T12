package mrs.isa.team12.clinical.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.repository.ClinicalCenterAdminRepository;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterAdminService;

@Service
public class ClinicalCenterAdminImpl implements ClinicalCenterAdminService {

	private ClinicalCenterAdminRepository clinicCentreAdminRep;
	
	@Autowired
	public ClinicalCenterAdminImpl(ClinicalCenterAdminRepository clinicCentreAdminRep) {
		this.clinicCentreAdminRep = clinicCentreAdminRep;
	}
	
	@Override
	public ClinicalCentreAdmin findOneByEmail(String email) {
		return clinicCentreAdminRep.findOneByEmail(email);
	}

}
