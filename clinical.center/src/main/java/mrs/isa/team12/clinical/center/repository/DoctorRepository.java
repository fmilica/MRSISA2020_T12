package mrs.isa.team12.clinical.center.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.Doctor;

//Interface for Doctor database access
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
	
	void addDoctor(Doctor doctor);
	
	ArrayList<Doctor> listDoctors();
	
	Doctor findOneByEmail(String email);
	
}
