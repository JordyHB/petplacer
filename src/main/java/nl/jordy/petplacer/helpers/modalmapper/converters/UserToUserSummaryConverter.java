package nl.jordy.petplacer.helpers.modalmapper.converters;

import nl.jordy.petplacer.dtos.summary.UserSummaryDTO;
import nl.jordy.petplacer.models.User;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class UserToUserSummaryConverter implements Converter<User, UserSummaryDTO> {

    @Override
    public UserSummaryDTO convert(MappingContext<User, UserSummaryDTO> context) {
        User source = context.getSource();
        UserSummaryDTO target = context.getDestination();

        if (target == null) {
            target = new UserSummaryDTO();
        }

        target.setUsername(source.getUsername());

        return target;
    }
}
