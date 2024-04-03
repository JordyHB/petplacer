package nl.jordy.petplacer.helpers.modalmapper;

import nl.jordy.petplacer.dtos.summary.ShelterSummaryDTO;
import nl.jordy.petplacer.models.Shelter;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

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



        return target;
    }
}