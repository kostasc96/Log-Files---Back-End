package gr.dit.project1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gr.dit.project1.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUsernameOrEmail(String username, String email);
	
	Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
