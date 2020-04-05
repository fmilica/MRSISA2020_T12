package mrs.isa.team12.clinical.center.repository;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import mrs.isa.team12.clinical.center.model.ClinicAdministrator;

@Repository
public class InMemoryClinicAdminRepository implements ClinicAdminRepository{
	
	private static HashMap<String, ClinicAdministrator> clinicAdmins = new HashMap<String, ClinicAdministrator>(); 
	
	@Override
	public Collection<ClinicAdministrator> findAll(){
		return InMemoryClinicAdminRepository.clinicAdmins.values();
	}

	@Override
	public ClinicAdministrator create(ClinicAdministrator clinicAdmin) {
		if(InMemoryClinicAdminRepository.clinicAdmins.containsKey(clinicAdmin.getUsername())) {
			return null;
		}
		else {
			InMemoryClinicAdminRepository.clinicAdmins.put(clinicAdmin.getUsername(), clinicAdmin);
			return clinicAdmin;
		}
	}

	@Override
	public ClinicAdministrator findOne(String username) {
		if(InMemoryClinicAdminRepository.clinicAdmins.containsKey(username)) {
			return InMemoryClinicAdminRepository.clinicAdmins.get(username);
		}
		else {
			return null;
		}
		
	}
	
	@Override
	public ClinicAdministrator update(ClinicAdministrator clinicAdmin) {
		if(InMemoryClinicAdminRepository.clinicAdmins.containsKey(clinicAdmin.getUsername())) {
			InMemoryClinicAdminRepository.clinicAdmins.remove(clinicAdmin.getUsername());
			InMemoryClinicAdminRepository.clinicAdmins.put(clinicAdmin.getUsername(), clinicAdmin);
			return clinicAdmin;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean delete(String username) {
		if(InMemoryClinicAdminRepository.clinicAdmins.containsKey(username)) {
			InMemoryClinicAdminRepository.clinicAdmins.remove(username);
			return true;
		}
		else {
			return false;
		}
	}
}
