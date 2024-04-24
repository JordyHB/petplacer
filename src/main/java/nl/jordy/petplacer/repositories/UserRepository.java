package nl.jordy.petplacer.repositories;

import nl.jordy.petplacer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    boolean existsByUsernameIgnoreCase(String username);

    Optional <User> findByUsername(String username);

    boolean existsByEmailIgnoreCase(String email);
}
