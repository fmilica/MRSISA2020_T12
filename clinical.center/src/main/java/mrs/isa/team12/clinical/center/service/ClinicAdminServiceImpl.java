package mrs.isa.team12.clinical.center.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.ClinicAdministrator;
import mrs.isa.team12.clinical.center.repository.InMemoryClinicAdminRepository;

@Service
public class ClinicAdminServiceImpl implements ClinicAdminService {

	private InMemoryClinicAdminRepository clinicAdminRepository;
	
	@Autowired
	public ClinicAdminServiceImpl(InMemoryClinicAdminRepository clinicAdminRepository) {
		this.clinicAdminRepository = clinicAdminRepository;
	}
	
	@Override
	public Collection<ClinicAdministrator> findAll() {
		Collection<ClinicAdministrator> admins = clinicAdminRepository.findAll();
		return admins;
	}

	@Override
	public ClinicAdministrator create(ClinicAdministrator clinicAdmin) {
		ClinicAdministrator admin = clinicAdminRepository.create(clinicAdmin);
		return admin;
	}

	@Override
	public ClinicAdministrator findOne(String username) {
		ClinicAdministrator admin = clinicAdminRepository.findOne(username);
		return admin;
	}

	@Override
	public ClinicAdministrator update(ClinicAdministrator clinicAdmin) {
		ClinicAdministrator admin = clinicAdminRepository.update(clinicAdmin);
		return admin;
	}

	@Override
	public boolean delete(String username) {
		boolean ind = clinicAdminRepository.delete(username);
		return ind;
	}
}
