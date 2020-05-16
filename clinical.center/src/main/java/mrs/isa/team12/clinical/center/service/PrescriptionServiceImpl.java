package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.Prescription;
import mrs.isa.team12.clinical.center.repository.PrescriptionRepository;
import mrs.isa.team12.clinical.center.service.interfaces.PrescriptionService;

@Service
public class PrescriptionServiceImpl implements PrescriptionService{

	private PrescriptionRepository prescriptionRepository;
	
	@Autowired
	public PrescriptionServiceImpl(PrescriptionRepository pr) {
		this.prescriptionRepository= pr;
	}

	@Override
	public List<Prescription> findAll() {
		return prescriptionRepository.findAll();
	}

	@Override
	public Prescription save(Prescription p) {
		return prescriptionRepository.save(p);
	}
}
