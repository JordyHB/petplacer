package nl.jordy.petplacer.helpers.modalmapper;

import nl.jordy.petplacer.helpers.modalmapper.propertymaps.ShelterOutputToDonationOutputPropertyMap;
import nl.jordy.petplacer.helpers.modalmapper.propertymaps.ShelterPetInputDTOToShelterPetPropertyMap;
import nl.jordy.petplacer.helpers.modalmapper.propertymaps.ShelterPetPatchDTOToShelterPetPropertyMap;
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

        //adds mappings for the pet subclasses with nested DTOs
        modelMapper.addMappings(new ShelterPetInputDTOToShelterPetPropertyMap());
        modelMapper.addMappings(new ShelterPetPatchDTOToShelterPetPropertyMap());
        // adds a converter to cast strings to lowercase
        ModelMapperHelper.modelMapper.addConverter(new StringToLowerConverter());
        // adds a property map to map ShelterOutputDTO to DonationOutputDTO
        ModelMapperHelper.modelMapper.addMappings(new ShelterOutputToDonationOutputPropertyMap());
    }

    private ModelMapperHelper() {
        // Private constructor to prevent instantiation
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }

//    public static <T extends Pet> void mapCommonFields(PetInputDTO petInputDTO, T finalPet) {
//        if (petInputDTO == null) {
//            finalPet.setName(petInputDTO.getName());
//            finalPet.setAge(petInputDTO.getAge());
//            finalPet.setSpecies(petInputDTO.getSpecies());
//            finalPet.setBreed(petInputDTO.getBreed());
//            finalPet.setColor(petInputDTO.getColor());
//            finalPet.setGender(petInputDTO.getGender());
//            finalPet.setSize(petInputDTO.getSize());
//            finalPet.setDescription(petInputDTO.getDescription());
//            finalPet.setSpayedNeutered(petInputDTO.getSpayedNeutered());
//            finalPet.setGoodWithKids(petInputDTO.getGoodWithKids());
//            finalPet.setGoodWithDogs(petInputDTO.getGoodWithDogs());
//            finalPet.setGoodWithCats(petInputDTO.getGoodWithCats());
//        }
//    }
}

