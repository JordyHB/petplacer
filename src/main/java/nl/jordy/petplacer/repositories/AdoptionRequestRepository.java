package nl.jordy.petplacer.repositories;

import nl.jordy.petplacer.models.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
}
