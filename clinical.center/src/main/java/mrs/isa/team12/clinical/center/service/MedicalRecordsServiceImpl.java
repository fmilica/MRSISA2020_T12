package mrs.isa.team12.clinical.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.MedicalRecords;
import mrs.isa.team12.clinical.center.repository.MedicalRecordRepository;
import mrs.isa.team12.clinical.center.service.interfaces.MedicalRecordsService;

@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsService{

	private MedicalRecordRepository medicalRecordsRepository;
	
	@Autowired
	public MedicalRecordsServiceImpl(MedicalRecordRepository mrr) {
		this.medicalRecordsRepository = mrr;
	}

	@Override
	public MedicalRecords save(MedicalRecords mr) {
		return medicalRecordsRepository.save(mr);
	}

	@Override
	public MedicalRecords findOneById(Long id) {
		return medicalRecordsRepository.findOneById(id);
	}
	
}
