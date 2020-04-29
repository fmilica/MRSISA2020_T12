package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.repository.ClinicalCentreAdminRepository;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterAdminService;

@Service
public class ClinicalCentreAdminServiceImpl implements ClinicalCenterAdminService {

	private ClinicalCentreAdminRepository clinicCentreAdminRep;
	
	@Autowired
	public ClinicalCentreAdminServiceImpl(ClinicalCentreAdminRepository clinicCentreAdminRep) {
		this.clinicCentreAdminRep = clinicCentreAdminRep;
	}
	
	@Override
	public ClinicalCentreAdmin findOneByEmail(String email) {
		return clinicCentreAdminRep.findOneByEmail(email);
	}

	@Override
	public List<ClinicalCentreAdmin> findAll() {
		return clinicCentreAdminRep.findAll();
	}

	@Override
	public ClinicalCentreAdmin save(ClinicalCentreAdmin cca) {
		return clinicCentreAdminRep.save(cca);
	}

}