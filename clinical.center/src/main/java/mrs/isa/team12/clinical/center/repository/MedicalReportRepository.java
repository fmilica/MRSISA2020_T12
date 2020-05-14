package mrs.isa.team12.clinical.center.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.MedicalReport;

public interface MedicalReportRepository extends JpaRepository<MedicalReport, Long>{

	MedicalReport save(MedicalReport mr);
	
	List<MedicalReport> findAll();
}
