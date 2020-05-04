package mrs.isa.team12.clinical.center.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.Ordination;

public interface OrdinationRepository extends JpaRepository<Ordination, Long>{

	Ordination findOneByName(String name);
	
	Ordination findOneByNameAndOrdinationNumber(String name, Integer ordinationNumber);
	
	List<Ordination> findAllByClinicId(Long clinicId);
}
