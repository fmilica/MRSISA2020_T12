package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.Diagnosis;
import mrs.isa.team12.clinical.center.repository.DiagnosisRepository;
import mrs.isa.team12.clinical.center.service.interfaces.DiagnosisService;

@Service
public class DiagnosisServiceImpl implements DiagnosisService{
	
	private DiagnosisRepository diagnosisRepository;
	
	@Autowired
	public DiagnosisServiceImpl(DiagnosisRepository dr) {
		this.diagnosisRepository = dr;
	}

	@Override
	public List<Diagnosis> findAll() {
		return diagnosisRepository.findAll();
	}

	@Override
	public Diagnosis save(Diagnosis d) {
		return diagnosisRepository.save(d);
	}

	@Override
	public Diagnosis findOneByName(String name) {
		return diagnosisRepository.findOneByName(name);
	}

	@Override
	public Diagnosis findOneById(Long id) {
		return diagnosisRepository.findOneById(id);
	}
}
