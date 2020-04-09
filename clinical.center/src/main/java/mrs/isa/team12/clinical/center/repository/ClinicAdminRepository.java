package mrs.isa.team12.clinical.center.repository;

import java.util.Collection;

import mrs.isa.team12.clinical.center.model.ClinicAdministrator;

//Interface for Clinic Admin database access
public interface ClinicAdminRepository {
	
	Collection<ClinicAdministrator> findAll();

	ClinicAdministrator create(ClinicAdministrator clinicAdmin);

	ClinicAdministrator findOne(String username);
	
	ClinicAdministrator update(ClinicAdministrator clinicAdmin);

	boolean delete(String username);
}
