package nl.jordy.petplacer.repositories;

import nl.jordy.petplacer.models.UserOwnedPet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOwnedPetRepository extends JpaRepository<UserOwnedPet, Long> {
}
