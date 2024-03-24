package nl.jordy.petplacer.repositories;

import nl.jordy.petplacer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameIgnoreCase(String username);

    Optional <User> findByUsername(String username);

    boolean existsByEmailIgnoreCase(String email);
    @Query("SELECT u.id, u.createdAt, u.email, u.enabled, u.firstName, u.lastName, u.password, u.phoneNumber, u.updatedAt, u.username, a.username, a.authority " +
            "FROM User u " +
            "LEFT JOIN Authority a ON u.username = a.username " +
            "WHERE u.id = ?1")
    Optional<User> findByIdWithAuthorities(Long id);
}
