package nl.jordy.petplacer.helpers.modalmapper;

import nl.jordy.petplacer.helpers.modalmapper.converters.StringToLowerConverter;
import nl.jordy.petplacer.helpers.modalmapper.propertymaps.DonationToDonationOutputPropertyMap;
import nl.jordy.petplacer.helpers.modalmapper.propertymaps.DonationToDonationSummaryPropertyMap;
import nl.jordy.petplacer.helpers.modalmapper.propertymaps.UserInputDTOPropertyMap;
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
        // adds a property map to map ShelterOutputDTO to DonationOutputDTO
        ModelMapperHelper.modelMapper.addMappings(new DonationToDonationOutputPropertyMap());
        // adds a property map to map donation to donation summary
        ModelMapperHelper.modelMapper.addMappings(new DonationToDonationSummaryPropertyMap());
    }

    private ModelMapperHelper() {
        // Private constructor to prevent instantiation
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }
}

