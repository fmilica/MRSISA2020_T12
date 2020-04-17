package mrs.isa.team12.clinical.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.isa.team12.clinical.center.model.RegisteredUser;

//Interface for User database access
public interface UserRepository extends JpaRepository<RegisteredUser, Long> {

}
