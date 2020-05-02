package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.repository.ClinicRepository;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;

@Service
public class ClinicImpl implements ClinicService {

	private ClinicRepository repository;
	
	@Autowired
	public ClinicImpl(ClinicRepository rep) {
		this.repository = rep;
	}
	
	@Override
	public List<Clinic> findAll() {
		return this.repository.findAll();
	}

	@Override
	public Clinic findOneById(Long id) {
		return this.repository.findOneById(id);
	}
	
	@Override
	public Clinic findOneByName(String name) {
		return this.repository.findOneByName(name);
	}
	
	@Override
	public Clinic save(Clinic c) {
		return this.repository.save(c);
	}

}
