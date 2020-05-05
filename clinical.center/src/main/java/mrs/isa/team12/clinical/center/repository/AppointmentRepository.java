package mrs.isa.team12.clinical.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

}
