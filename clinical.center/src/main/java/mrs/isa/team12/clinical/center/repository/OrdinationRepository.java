package mrs.isa.team12.clinical.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.Ordination;

public interface OrdinationRepository extends JpaRepository<Ordination, Long>{

	Ordination findOneByName(String name);
	
}
