package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.DonationInputDTO;
import nl.jordy.petplacer.dtos.output.DonationOutputDTO;
import nl.jordy.petplacer.dtos.patch.DonationPatchDTO;
import nl.jordy.petplacer.exceptions.CustomAccessDeniedException;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.Donation;
import nl.jordy.petplacer.models.Shelter;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.DonationRepository;
import nl.jordy.petplacer.specifications.DonationSpecification;
import nl.jordy.petplacer.util.AccessValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class DonationService {

    private final ShelterService shelterService;
    private final UserService userService;
    private final DonationRepository donationRepository;
    private final AccessValidator accessValidator;

    public DonationService(
            ShelterService shelterService,
            UserService userService,
            DonationRepository donationRepository,
            AccessValidator accessValidator
    ) {
        this.userService = userService;
        this.shelterService = shelterService;
        this.donationRepository = donationRepository;
        this.accessValidator = accessValidator;
    }

    public Donation fetchDonationByID(Long donationID) {
        return donationRepository.findById(donationID)
                .orElseThrow(() -> new RecordNotFoundException("Donation not found with id: " + donationID));
    }

    public DonationOutputDTO makeDonation(Long shelterID, DonationInputDTO donationInputDTO) {

        Shelter shelter = shelterService.fetchShelterByID(shelterID);
        Donation donation = ModelMapperHelper.getModelMapper().map(donationInputDTO, Donation.class);
        User user = userService.fetchUserByUsername(accessValidator.getAuth().getName());

        donation.setDonator(user);
        donation.setReceivingShelter(shelter);

        donationRepository.save(donation);

        return ModelMapperHelper.getModelMapper().map(donation, DonationOutputDTO.class);
    }

    public List<DonationOutputDTO> findAllDonations() {
        List<Donation> donations = donationRepository.findAll();

        return donations.stream()
                // filters out donations that the user is not the donator of, unless the user is an admin or the shelter manager
                .filter(donation -> accessValidator.isSheltersManagerOrAdminFilterOnly(
                                accessValidator.getAuth(),
                                donation.getReceivingShelter()
                        )
                                || donation.getDonator().getUsername().equals(accessValidator.getAuth().getName())
                )
                .map(donation -> ModelMapperHelper.getModelMapper().map(donation, DonationOutputDTO.class))
                .toList();
    }

    public DonationOutputDTO findDonationById(Long donationID) {

        Donation donation = fetchDonationByID(donationID);

        accessValidator.isSheltersManagerOrAdmin(accessValidator.getAuth(), donation.getReceivingShelter());

        return ModelMapperHelper.getModelMapper().map(fetchDonationByID(donationID), DonationOutputDTO.class);
    }

    public List<DonationOutputDTO> findDonationsByParams(
            Long shelterID,
            String donatorName,
            BigDecimal minDonationAmount,
            BigDecimal maxDonationAmount
    ) {
        return donationRepository.findAll(
                        new DonationSpecification(shelterID, donatorName, minDonationAmount, maxDonationAmount)
                )
                .stream()
                // filters out donations that the user is not the donator of, unless the user is an admin or the shelter manager
                .filter(
                        donation -> accessValidator.isSheltersManagerOrAdminFilterOnly(
                                accessValidator.getAuth(),
                                donation.getReceivingShelter()
                        )
                                || donation.getDonator().getUsername().equals(accessValidator.getAuth().getName())
                )
                .map(donation -> ModelMapperHelper.getModelMapper().map(donation, DonationOutputDTO.class))
                .toList();
    }

    public DonationOutputDTO updateDonationById(Long donationID, DonationPatchDTO donationPatchDTO) {

        Donation requestedDonation = fetchDonationByID(donationID);

        // returns a 401 if the is not the donator of the donation or an admin
        accessValidator.isUserOrAdmin(accessValidator.getAuth(), requestedDonation.getDonator().getUsername());

        ModelMapperHelper.getModelMapper().map(donationPatchDTO, requestedDonation);
        requestedDonation.setDateOfLastUpdate(new Date());

        donationRepository.save(requestedDonation);
        return ModelMapperHelper.getModelMapper().map(requestedDonation, DonationOutputDTO.class);
    }

    public String deleteDonationById(Long donationID) throws CustomAccessDeniedException {

        if (accessValidator.isAdmin(accessValidator.getAuth())) {
            Donation donation = fetchDonationByID(donationID);

            donationRepository.delete(donation);

            return "Donation with id: " + donationID + " has been successfully deleted.";
        } else {
            throw new CustomAccessDeniedException("Only admins can delete donations");
        }
    }

}
