package mrs.isa.team12.clinical.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;

public interface ClinicalCenterAdminRepository extends JpaRepository<ClinicalCentreAdmin, Long> {

	ClinicalCentreAdmin findOneByEmail(String email);
	
}
