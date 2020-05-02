package mrs.isa.team12.clinical.center.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.Doctor;

//Interface for Doctor database access
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
	
	Doctor findOneByEmail(String email);
	
	Doctor findOneById(Long id);
	
	List<Doctor> findAllByClinicId(Long id);
	
}
