package nl.jordy.petplacer.repositories;

import nl.jordy.petplacer.models.ShelterPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterPetRepository  extends JpaRepository<ShelterPet, Long>, JpaSpecificationExecutor<ShelterPet> {

}
