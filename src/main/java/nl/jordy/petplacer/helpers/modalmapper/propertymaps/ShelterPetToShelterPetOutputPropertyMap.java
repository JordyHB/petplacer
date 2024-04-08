package nl.jordy.petplacer.helpers.modalmapper.propertymaps;

import nl.jordy.petplacer.dtos.output.ShelterPetOutputDTO;
import nl.jordy.petplacer.helpers.modalmapper.converters.ImageToImageLinkConverter;
import nl.jordy.petplacer.helpers.modalmapper.converters.TwoNumberBigDecimalCoverter;
import nl.jordy.petplacer.models.ShelterPet;
import org.modelmapper.PropertyMap;

public class ShelterPetToShelterPetOutputPropertyMap extends PropertyMap<ShelterPet, ShelterPetOutputDTO> {

    @Override
    protected void configure() {
        using(new ImageToImageLinkConverter())
                .map(source.getImage(), destination.getImageLink());

        using(new TwoNumberBigDecimalCoverter())
                .map(source.getAdoptionFee(), destination.getAdoptionFee());
    }
}
