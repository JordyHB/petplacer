package nl.jordy.petplacer.helpers.modalmapper.converters;

import nl.jordy.petplacer.dtos.summary.DonationSummaryDTO;
import nl.jordy.petplacer.dtos.summary.ShelterPetSummaryDTO;
import nl.jordy.petplacer.dtos.summary.ShelterSummaryDTO;
import nl.jordy.petplacer.dtos.summary.UserSummaryDTO;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.Shelter;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.List;

public class ShelterToSummaryConverter implements Converter<Shelter, ShelterSummaryDTO> {

    @Override
    public ShelterSummaryDTO convert(MappingContext<Shelter, ShelterSummaryDTO> context) {
        Shelter source = context.getSource();
        ShelterSummaryDTO target = context.getDestination();

        if (target == null) {
            target = new ShelterSummaryDTO();
        }

        target.setId(source.getId());
        target.setShelterName(source.getShelterName());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setEmail(source.getEmail());
        target.setAddress(source.getAddress());
        target.setCity(source.getCity());
        target.setPostalCode(source.getPostalCode());
        target.setDescription(source.getDescription());
        target.setWebsite(source.getWebsite());
        target.setFacilities(source.getFacilities());
        target.setOpeningHours(source.getOpeningHours());
        target.setDateOfRegistration(source.getDateOfRegistration());

        if (source.getManagers() != null) {
            List<UserSummaryDTO> managers = source.getManagers()
                    .stream()
                    .map(manager -> ModelMapperHelper.getModelMapper().map(manager, UserSummaryDTO.class))
                    .toList();

            target.setManagers(managers);
        }

        if (source.getDonations() != null) {
            List<DonationSummaryDTO> donations = source.getDonations()
                    .stream()
                    .map(donation -> ModelMapperHelper.getModelMapper().map(donation, DonationSummaryDTO.class))
                    .toList();

            target.setDonations(donations);
        }

        if (source.getShelterPets() != null) {
            List<ShelterPetSummaryDTO> shelterPets = source.getShelterPets()
                    .stream()
                    .map(shelterPet -> ModelMapperHelper.getModelMapper().map(shelterPet, ShelterPetSummaryDTO.class))
                    .toList();

            target.setShelterPets(shelterPets);
        }

        return target;
    }
}