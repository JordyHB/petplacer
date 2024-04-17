package nl.jordy.petplacer.repositories;

import nl.jordy.petplacer.models.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long>, JpaSpecificationExecutor<Shelter> {
    boolean existsByShelterNameIgnoreCase(String shelterName);
}
