package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.Patient;

public interface AppointmentService {
	
	Appointment findById(Long id);
	
	Appointment save(Appointment a);
	
	void delete(Appointment a);
	
	Appointment update(Long appId) throws Exception;
	
	Appointment update(Patient patient, Long appId) throws Exception;
	
	List<Appointment> findAll();
	
	List<Appointment> findAllByClinicIdAndFinished(Long id, Boolean finished);
	
	List<Appointment> findAllByClinicIdAndPatient(Long clinicId, Patient patient);
	
	List<Appointment> findAllByPatientIdAndDoctorId(Long patientId, Long doctorId);
	
	List<Appointment> findAllByPatientIdAndFinished(Long patientId, Boolean finished);
	
	List<Appointment> findAllByPatientIdAndConfirmed(Long patientId, Boolean confirmed);
	
	List<Appointment> findAllByClinicIdAndConfirmedAndFinished(Long id, Boolean confirmed, Boolean finished);
	
	List<Appointment> findAllByPatientIdAndConfirmedAndFinished(Long patientId, Boolean confirmed, Boolean finished);
	
	List<Appointment> findAllByDoctorId(Long id);
	
	List<Appointment> findAllByPatientId(Long id);
}
