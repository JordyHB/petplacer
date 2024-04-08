package nl.jordy.petplacer.helpers.modalmapper.propertymaps;

import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.models.User;
import org.modelmapper.PropertyMap;

public class UserInputDTOToUserPropertyMap extends PropertyMap<UserInputDTO, User> {


    @Override
    protected void configure() {
        skip().setPassword(null);
    }
}
