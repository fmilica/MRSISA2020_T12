package mrs.isa.team12.clinical.center.repository;

import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.Doctor;

//Interface for Doctor database access
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
	
	Doctor findOneByEmail(String email);
	
	Doctor findOneById(Long id);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select d from Doctor d where d.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	Doctor findOneByIdPessimistic(Long id);
	
	/*@Query("SELECT d "
			+ "FROM Doctor d "
			+ "WHERE d.id = ?1 "
			+ "AND ("+
					"SELECT COUNT(*) "+
					"FROM ("+
			"			SELECT a " + 
			"			FROM d.appointments a" + 
			"			WHERE a.date = ?2 "+
			"			AND ("+
			"					("+
			"						(a.startTime BETWEEN ?3 AND ?4) "+
			"						AND (a.endTime BETWEEN ?3 AND ?4)"+
			"					)" + 
			"					OR (a.startTime <= ?3 AND a.endTime >= ?4)" + 
			"			)"+
					")"+
			"	) "
			+ "= 0")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	Doctor findAvailability(Long id, Date date, Integer startTime, Integer endTime);*/
	
	List<Doctor> findAll();
	
	List<Doctor> findAllByClinicId(Long id);

	List<Doctor> findAllByAppointmentTypes(AppointmentType a);
	
	List<Doctor> findAllByAppointmentTypesIn(List<AppointmentType> types);
	
	List<Doctor> findAllByClinicAndAppointmentTypesIn(Clinic clinic, List<AppointmentType> types);
	
	List<Doctor> findAllByClinicIdAndAppointmentTypes(Long clinicId, AppointmentType type);
}
