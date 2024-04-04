package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.DonationInputDTO;
import nl.jordy.petplacer.dtos.output.DonationOutputDTO;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.Donation;
import nl.jordy.petplacer.models.Shelter;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.DonationRepository;
import nl.jordy.petplacer.util.AccessValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    private final ShelterService shelterService;
    private final UserService userService;
    private final DonationRepository donationRepository;

    public DonationService(
            ShelterService shelterService,
            UserService userService,
            DonationRepository donationRepository
    ) {
        this.userService = userService;
        this.shelterService = shelterService;
        this.donationRepository = donationRepository;
    }

    public Donation fetchDonationByID(Long donationID) {
        return donationRepository.findById(donationID)
                .orElseThrow(() -> new RecordNotFoundException("Donation not found with id: " + donationID));
    }

    public DonationOutputDTO makeDonation(Long shelterID, DonationInputDTO donationInputDTO) {

        Shelter shelter = shelterService.fetchShelterByID(shelterID);
        Donation donation = ModelMapperHelper.getModelMapper().map(donationInputDTO, Donation.class);
        User user = userService.fetchUserByUsername(AccessValidator.getAuth().getName());

        donation.setDonator(user);
        donation.setReceivingShelter(shelter);

        donationRepository.save(donation);

        return ModelMapperHelper.getModelMapper().map(donation, DonationOutputDTO.class);
    }

    public List<DonationOutputDTO> findAllDonations() {
        List<Donation> donations = donationRepository.findAll();

        return donations.stream()
                .map(donation -> ModelMapperHelper.getModelMapper().map(donation, DonationOutputDTO.class))
                .toList();
    }

    public DonationOutputDTO findDonationById(Long donationID) {
        return ModelMapperHelper.getModelMapper().map(fetchDonationByID(donationID), DonationOutputDTO.class);
    }

}
