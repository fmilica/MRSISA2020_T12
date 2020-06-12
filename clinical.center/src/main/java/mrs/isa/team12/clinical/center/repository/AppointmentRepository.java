package mrs.isa.team12.clinical.center.repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.model.enums.OrdinationType;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
		
	Appointment findOneById(Long id);
/*
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Appointment findOneByIdPessimistic(Long id);
*/	
	List<Appointment> findAllByPatientIdAndDoctorId(Long patientId, Long doctorId);
	
	List<Appointment> findAllByClinicIdAndConfirmedAndFinished(Long id, Boolean confirmed, Boolean finished);
	
	List<Appointment> findAllByPatientIdAndFinished(Long patientId, Boolean finished);
	
	List<Appointment> findAllByPatientIdAndConfirmed(Long patientId, Boolean confirmed);
	
	List<Appointment> findAllByPatientIdAndConfirmedAndFinished(Long patientId, Boolean confirmed, Boolean finished);
	
	List<Appointment> findAllByClinicIdAndPatient(Long clinicId, Patient patient);
	
	List<Appointment> findAllByDoctorId(Long id);
	
	List<Appointment> findAllByPatientId(Long id);
	
	List<Appointment> findAllByClinicIdAndFinished(Long id, Boolean finished);
	
	List<Appointment> findAllByDoctorsIn(Set<Doctor> doctors);
	
	List<Appointment> findAllByTypeAndDoctorsIn(OrdinationType ot, Set<Doctor> doctors);
	
	List<Appointment> findAllByDoctorIdAndDateBetween(Long id, Date d1, Date d2);
	
	@Query("SELECT a "
			+ "FROM Appointment a "
			+ "WHERE a.doctor.id = ?1 "
			+ "AND a.date = ?2 "
			+ "AND ( "
			+ "((a.startTime BETWEEN ?3 AND ?4) AND (a.endTime BETWEEN ?3 AND ?4)) "
			+ "OR (a.startTime <= ?3 AND a.endTime >= ?4)"
			+ ")")
	/*@Lock(LockModeType.PESSIMISTIC_WRITE)
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})*/
	// nije dovoljno iz razloga sto moze neko u medjuvremenu da izvrsi dodavanje u tom periodu
	// moramo zakljucati podatke te dok god traje transakcija
	// Repeatable read bi nam verovatno pomogao!
	List<Appointment> findAllExisting(Long doctorId, Date appDate, Integer startTime, Integer endTime);
}
