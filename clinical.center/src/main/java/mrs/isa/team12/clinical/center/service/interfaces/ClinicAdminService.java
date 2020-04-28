package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.ClinicAdmin;

public interface ClinicAdminService {
	
	List<ClinicAdmin> findAll();
	
	ClinicAdmin save(ClinicAdmin ca);
	
	ClinicAdmin findOneByEmail(String email);
}
