package mrs.isa.team12.clinical.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

	Clinic findOneByName(String name);
	
}
