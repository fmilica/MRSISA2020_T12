package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.repository.ClinicAdminRepository;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicAdminService;

@Service
public class ClinicAdminImpl implements ClinicAdminService {
	
	private ClinicAdminRepository clinicAdminRep;
	
	@Autowired
	public void setUserRepository(ClinicAdminRepository repository) {
		this.clinicAdminRep = repository;
	}
	
	@Override
	public List<ClinicAdmin> findAll() {
		return this.clinicAdminRep.findAll();
	}

	@Override
	public ClinicAdmin save(ClinicAdmin ca) {
		return this.clinicAdminRep.save(ca);
	}

}