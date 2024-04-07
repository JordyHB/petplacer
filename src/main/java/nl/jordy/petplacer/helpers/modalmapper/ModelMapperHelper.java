package nl.jordy.petplacer.helpers.modalmapper;

import nl.jordy.petplacer.helpers.modalmapper.converters.StringToLowerConverter;
import nl.jordy.petplacer.helpers.modalmapper.propertymaps.*;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;


public class ModelMapperHelper {

    // ModelMapper instance to map DTOs to models and vice versa
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        // skips null fields when mapping patch DTOs
        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());

        // skips mapping password, preventing it from casting it to lower.
        modelMapper.addMappings(new UserInputDTOPropertyMap());
        // adds a converter to cast strings to lowercase
        ModelMapperHelper.modelMapper.addConverter(new StringToLowerConverter());
        // adds a property maps
        ModelMapperHelper.modelMapper.addMappings(new DonationToDonationOutputPropertyMap());
        ModelMapperHelper.modelMapper.addMappings(new DonationToDonationSummaryPropertyMap());
        ModelMapperHelper.modelMapper.addMappings(new DonationPatchToDonationPropertyMap());
        ModelMapperHelper.modelMapper.addMappings(new ShelterPetToShelterPetOutputPropertyMap());
        ModelMapperHelper.modelMapper.addMappings(new UserOwnedPetToUserOwnedPetOutputPropertyMap());
    }

    private ModelMapperHelper() {
        // Private constructor to prevent instantiation
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }
}

