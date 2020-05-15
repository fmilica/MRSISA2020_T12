package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.Appointment;

public interface AppointmentService {
	
	Appointment save(Appointment a);
	
	List<Appointment> findAllByPatientIdAndDoctorId(Long patientId, Long doctorId);
	
	List<Appointment> findAll();
	
	Appointment findById(Long id);
	
	List<Appointment> findAllByClinicIdAndConfirmedAndFinished(Long id, Boolean confirmed, Boolean finished);
}
