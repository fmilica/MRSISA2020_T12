package mrs.isa.team12.clinical.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.model.RegisteredUser;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long>{
	
	RegisteredUser findOneByEmail(String email);
	
	RegisteredUser findOneBySecurityNumber(String number);
	
	Patient findOneByToken(String token);

	Patient findOneById(Long id);
	
	RegisteredUser findOneByEmailAndActive(String email, Boolean activity);
}
