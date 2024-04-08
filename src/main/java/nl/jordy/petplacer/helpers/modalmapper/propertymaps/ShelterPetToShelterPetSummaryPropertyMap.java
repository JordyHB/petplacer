package nl.jordy.petplacer.helpers.modalmapper.propertymaps;

import nl.jordy.petplacer.dtos.summary.ShelterPetSummaryDTO;
import nl.jordy.petplacer.helpers.modalmapper.converters.ImageToImageLinkConverter;
import nl.jordy.petplacer.models.ShelterPet;
import org.modelmapper.PropertyMap;

public class ShelterPetToShelterPetSummaryPropertyMap extends PropertyMap<ShelterPet, ShelterPetSummaryDTO> {

    @Override
    protected void configure() {
        using(new ImageToImageLinkConverter())
                .map(source.getImage(), destination.getImageLink());
    }
}
