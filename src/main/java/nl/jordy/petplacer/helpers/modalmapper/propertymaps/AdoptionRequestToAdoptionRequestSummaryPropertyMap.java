package nl.jordy.petplacer.helpers.modalmapper.propertymaps;

import nl.jordy.petplacer.dtos.summary.AdoptionRequestSummaryDTO;
import nl.jordy.petplacer.helpers.modalmapper.converters.ShelterPetToSummaryConverter;
import nl.jordy.petplacer.models.AdoptionRequest;
import org.modelmapper.PropertyMap;

public class AdoptionRequestToAdoptionRequestSummaryPropertyMap extends PropertyMap<AdoptionRequest, AdoptionRequestSummaryDTO> {

    @Override
    protected void configure() {
        using(new ShelterPetToSummaryConverter())
                .map(source.getRequestedPet(), destination.getRequestedPet());
    }
}
