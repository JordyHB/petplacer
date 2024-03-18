package nl.jordy.petplacer.helpers;

import nl.jordy.petplacer.dtos.input.PetInputDTO;
import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.dtos.input.UserOwnedPetInputDTO;
import nl.jordy.petplacer.models.Pet;
import nl.jordy.petplacer.models.ShelterPet;
import nl.jordy.petplacer.models.UserOwnedPet;

public class MapPetDTOtoSubclass {

    // checks and sets FinalPet to the correct subclass
    private static  <T extends Pet> T checkExistingPet(T existingPet, Object inputDTO, Class<T> subclass) {
        if (existingPet == null) {
            return ModelMapperHelper.getModelMapper().map(inputDTO, subclass);
        } else {
            if (existingPet instanceof ShelterPet) {
                return existingPet;
            } else if (existingPet instanceof UserOwnedPet) {
                return existingPet;
            } else {
                throw new IllegalArgumentException("The existingPet is not a subclass of Pet");
            }
        }
    }

    public static <T extends Pet> T mapPetDTOtoSubclass(Object inputDTO, Class<T> subclass, T existingPet) {

        T finalPet = checkExistingPet(existingPet, inputDTO, subclass);

        PetInputDTO petInputDTO = null;

        // typecast the inputDTO to a ShelterPetInputDTO
        if (inputDTO instanceof ShelterPetInputDTO)  {
            ((ShelterPet) finalPet).setMonthsInShelter(((ShelterPetInputDTO) inputDTO).getMonthsInShelter());
            ((ShelterPet) finalPet).setMedicalHistory(((ShelterPetInputDTO) inputDTO).getMedicalHistory());
            ((ShelterPet) finalPet).setSpecialNeeds(((ShelterPetInputDTO) inputDTO).getSpecialNeeds());
            ((ShelterPet) finalPet).setPreviousSituation(((ShelterPetInputDTO) inputDTO).getPreviousSituation());
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

        finalPet.setName(petInputDTO.getName());
        finalPet.setAge(petInputDTO.getAge());
        finalPet.setSpecies(petInputDTO.getSpecies());
        finalPet.setBreed(petInputDTO.getBreed());
        finalPet.setColor(petInputDTO.getColor());
        finalPet.setGender(petInputDTO.getGender());
        finalPet.setSize(petInputDTO.getSize());
        finalPet.setDescription(petInputDTO.getDescription());
        finalPet.setSpayedNeutered(petInputDTO.getSpayedNeutered());
        finalPet.setGoodWithKids(petInputDTO.getGoodWithKids());
        finalPet.setGoodWithDogs(petInputDTO.getGoodWithDogs());
        finalPet.setGoodWithCats(petInputDTO.getGoodWithCats());

        return finalPet;
    }
}
