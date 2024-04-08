package nl.jordy.petplacer.helpers.modalmapper.propertymaps;

import nl.jordy.petplacer.dtos.output.UserOwnedPetOutputDTO;
import nl.jordy.petplacer.helpers.modalmapper.converters.ImageToImageLinkConverter;
import nl.jordy.petplacer.models.UserOwnedPet;
import org.modelmapper.PropertyMap;

public class UserOwnedPetToUserOwnedPetOutputPropertyMap extends PropertyMap<UserOwnedPet, UserOwnedPetOutputDTO> {

    @Override
    protected void configure() {
        using(new ImageToImageLinkConverter())
                .map(source.getImage(), destination.getImageLink());
    }
}
