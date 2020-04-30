package mrs.isa.team12.clinical.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.AppointmentType;

public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, Long> {

	AppointmentType findOneByName(String name);
}
