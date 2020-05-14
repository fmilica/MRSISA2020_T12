package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.MedicalReport;
import mrs.isa.team12.clinical.center.repository.MedicalReportRepository;
import mrs.isa.team12.clinical.center.service.interfaces.MedicalReportService;

@Service
public class MedicalReportServiceImpl implements MedicalReportService{

	private MedicalReportRepository medicalreportRepository;
	
	@Autowired
	public MedicalReportServiceImpl(MedicalReportRepository mrr) {
		this.medicalreportRepository = mrr;
	}

	@Override
	public MedicalReport save(MedicalReport mr) {
		return medicalreportRepository.save(mr);
	}

	@Override
	public List<MedicalReport> findAll() {
		return medicalreportRepository.findAll();
	}
}
