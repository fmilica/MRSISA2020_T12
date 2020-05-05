package mrs.isa.team12.clinical.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.AppointmentRequest;

public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, Long> {
	
	//AppointmentRequest findOneByName(String name);
	
	AppointmentRequest findOneById(Long id);
}
