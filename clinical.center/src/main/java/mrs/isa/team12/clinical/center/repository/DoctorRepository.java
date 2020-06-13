package mrs.isa.team12.clinical.center.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.Doctor;

//Interface for Doctor database access
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
	
	Doctor findOneByEmail(String email);
	
	/*@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select d from Doctor d where d.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})*/
	Doctor findOneById(@Param("id") Long id);
	
	List<Doctor> findAll();
	
	List<Doctor> findAllByClinicId(Long id);

	List<Doctor> findAllByAppointmentTypes(AppointmentType a);
	
	List<Doctor> findAllByAppointmentTypesIn(List<AppointmentType> types);
	
	List<Doctor> findAllByClinicAndAppointmentTypesIn(Clinic clinic, List<AppointmentType> types);
	
	List<Doctor> findAllByClinicIdAndAppointmentTypes(Long clinicId, AppointmentType type);
}
