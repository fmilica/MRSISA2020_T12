package mrs.isa.team12.clinical.center.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.Patient;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
	
	Appointment findOneById(Long id);
	
	List<Appointment> findAllByPatientIdAndDoctorId(Long patientId, Long doctorId);
	
	List<Appointment> findAllByClinicIdAndConfirmedAndFinished(Long id, Boolean confirmed, Boolean finished);
	
	List<Appointment> findAllByClinicIdAndPatient(Long clinicId, Patient patient);
}
