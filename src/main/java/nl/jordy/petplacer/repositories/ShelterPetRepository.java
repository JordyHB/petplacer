package nl.jordy.petplacer.repositories;

import nl.jordy.petplacer.models.ShelterPet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterPetRepository  extends JpaRepository<ShelterPet, Long> {

}
