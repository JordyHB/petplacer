package nl.jordy.petplacer.repositories;

import nl.jordy.petplacer.models.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
