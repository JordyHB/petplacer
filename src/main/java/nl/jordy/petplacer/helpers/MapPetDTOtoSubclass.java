package nl.jordy.petplacer.helpers;

import nl.jordy.petplacer.dtos.input.PetInputDTO;
import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.dtos.input.UserOwnedPetInputDTO;
import nl.jordy.petplacer.models.Pet;

public class MapPetDTOtoSubclass {

    public static <T extends Pet> T mapPetDTOtoSubclass(Object inputDTO, Class<T> subclass) {

        T mappedSubclass =ModelMapperHelper.getModelMapper().map(inputDTO, subclass);
        PetInputDTO petInputDTO = null;

        // typecast the inputDTO to a ShelterPetInputDTO
        if (inputDTO instanceof ShelterPetInputDTO)  {
            petInputDTO = ((ShelterPetInputDTO) inputDTO).getPet();
        }

        // typecast the inputDTO to a UserOwnedPetInputDTO
        if (inputDTO instanceof UserOwnedPetInputDTO) {
            petInputDTO = ((UserOwnedPetInputDTO) inputDTO).getPet();
        }

        // if the inputDTO does not contain a pet, throw an exception
        if (petInputDTO == null) {
            throw new IllegalArgumentException("The inputDTO does not contain a pet");
        }

        mappedSubclass.setName(petInputDTO.getName());
        mappedSubclass.setAge(petInputDTO.getAge());
        mappedSubclass.setSpecies(petInputDTO.getSpecies());
        mappedSubclass.setBreed(petInputDTO.getBreed());
        mappedSubclass.setColor(petInputDTO.getColor());
        mappedSubclass.setGender(petInputDTO.getGender());
        mappedSubclass.setSize(petInputDTO.getSize());
        mappedSubclass.setDescription(petInputDTO.getDescription());
        mappedSubclass.setSpayedNeutered(petInputDTO.isSpayedNeutered());
        mappedSubclass.setGoodWithKids(petInputDTO.isGoodWithKids());
        mappedSubclass.setGoodWithDogs(petInputDTO.isGoodWithDogs());
        mappedSubclass.setGoodWithCats(petInputDTO.isGoodWithCats());

        return mappedSubclass;
    }
}
