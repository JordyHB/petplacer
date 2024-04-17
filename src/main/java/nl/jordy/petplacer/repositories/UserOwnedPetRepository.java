package nl.jordy.petplacer.repositories;

import nl.jordy.petplacer.models.UserOwnedPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOwnedPetRepository extends
        JpaRepository<UserOwnedPet, Long>,
        JpaSpecificationExecutor<UserOwnedPet> {
}
