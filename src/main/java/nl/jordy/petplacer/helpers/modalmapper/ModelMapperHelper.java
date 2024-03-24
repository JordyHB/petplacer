package nl.jordy.petplacer.helpers.modalmapper;

import nl.jordy.petplacer.helpers.modalmapper.propertymaps.UserInputDTOPropertyMap;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

public class ModelMapperHelper {

    // ModelMapper instance to map DTOs to models and vice versa
    private static final ModelMapper modelMapper = new ModelMapper();

    private ModelMapperHelper() {
        // Private constructor to prevent instantiation
    }

    // Returns the ModelMapper instance
    public static ModelMapper getModelMapper() {

        // skips null fields when mapping patch DTOs
        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());

        // skips mapping password, preventing it from casting it to lower.
        modelMapper.addMappings(new UserInputDTOPropertyMap());

        ModelMapperHelper.modelMapper.addConverter(new StringToLowerConverter());

        return modelMapper;
    }
}
