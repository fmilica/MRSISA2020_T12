package mrs.isa.team12.clinical.center.repository;

import java.util.Collection;

import mrs.isa.team12.clinical.center.model.ClinicAdministrator;

public interface ClinicAdminRepository {
	
	Collection<ClinicAdministrator> findAll();

	ClinicAdministrator create(ClinicAdministrator clinicAdmin);

	ClinicAdministrator findOne(String username);
	
	ClinicAdministrator update(ClinicAdministrator clinicAdmin);

	boolean delete(String username);
}
