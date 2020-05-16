package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.DiagnosePerscription;
import mrs.isa.team12.clinical.center.repository.DiagnosisPrescriptionRepository;
import mrs.isa.team12.clinical.center.service.interfaces.DiagnosisPrescriptionService;

@Service
public class DiagnosePrescriptionServiceImpl implements DiagnosisPrescriptionService{

	private DiagnosisPrescriptionRepository diagnosePrescriptionRep;
	
	@Autowired
	public DiagnosePrescriptionServiceImpl(DiagnosisPrescriptionRepository dpr) {
		this.diagnosePrescriptionRep = dpr;
	}

	@Override
	public List<DiagnosePerscription> findAll() {
		return diagnosePrescriptionRep.findAll();
	}
	
	
}
