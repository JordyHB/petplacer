package nl.jordy.petplacer.helpers;

import org.modelmapper.ModelMapper;

public class ModelMapperHelper {

    // ModelMapper instance to map DTOs to models and vice versa
    private static final ModelMapper modelMapper = new ModelMapper();

    private ModelMapperHelper() {
        // Private constructor to prevent instantiation
    }

    // Returns the ModelMapper instance
    public static ModelMapper getModelMapper() {
        return modelMapper;
    }



}
