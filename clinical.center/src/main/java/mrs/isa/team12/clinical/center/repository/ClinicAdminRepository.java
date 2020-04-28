package mrs.isa.team12.clinical.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.ClinicAdmin;

//Interface for Clinic Admin database access
public interface ClinicAdminRepository extends JpaRepository<ClinicAdmin, Long> {
	
	ClinicAdmin findOneByEmail(String email);
}
