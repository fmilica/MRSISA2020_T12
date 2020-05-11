package mrs.isa.team12.clinical.center.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
	
	List<Appointment> findAllByPatientIdAndDoctorId(Long patientId, Long doctorId);

}
