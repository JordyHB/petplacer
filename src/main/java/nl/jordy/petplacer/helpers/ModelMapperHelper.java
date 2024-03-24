package nl.jordy.petplacer.helpers;

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

        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());

        registerConverters();
        return modelMapper;
    }

    private static void registerConverters() {
        ModelMapperHelper.modelMapper.addConverter(new StringToLowerConverter());
    }
}
