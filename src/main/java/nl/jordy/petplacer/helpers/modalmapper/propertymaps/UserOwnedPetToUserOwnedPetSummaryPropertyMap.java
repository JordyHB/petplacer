package nl.jordy.petplacer.helpers.modalmapper.propertymaps;

import nl.jordy.petplacer.dtos.summary.UserOwnedPetSummaryDTO;
import nl.jordy.petplacer.helpers.modalmapper.converters.ImageToImageLinkConverter;
import nl.jordy.petplacer.models.UserOwnedPet;
import org.modelmapper.PropertyMap;

public class UserOwnedPetToUserOwnedPetSummaryPropertyMap extends PropertyMap<UserOwnedPet, UserOwnedPetSummaryDTO> {

    @Override
    protected void configure() {
        using(new ImageToImageLinkConverter())
                .map(source.getImage(), destination.getImageLink());
    }
}
