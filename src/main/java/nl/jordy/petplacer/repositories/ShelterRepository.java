package nl.jordy.petplacer.repositories;

import nl.jordy.petplacer.models.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    boolean existsByShelterNameIgnoreCase(String shelterName);
}
