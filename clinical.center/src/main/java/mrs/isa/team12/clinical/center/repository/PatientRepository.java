package mrs.isa.team12.clinical.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	Patient findOneByEmail(String email);

}