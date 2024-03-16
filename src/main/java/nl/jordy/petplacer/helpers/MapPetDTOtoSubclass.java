package nl.jordy.petplacer.helpers;

import nl.jordy.petplacer.dtos.input.PetInputDTO;
import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.models.ShelterPet;

public class MapPetDTOtoSubclass {

    public static ShelterPet mapPetDTOtoSubclass(ShelterPetInputDTO ShelterPetInputDTO) {

        ShelterPet shelterPet = ModelMapperHelper.getModelMapper().map(ShelterPetInputDTO, ShelterPet.class);

        PetInputDTO petInputDTO = ShelterPetInputDTO.getPet();

        shelterPet.setName(petInputDTO.getName());
        shelterPet.setAge(petInputDTO.getAge());
        shelterPet.setSpecies(petInputDTO.getSpecies());
        shelterPet.setBreed(petInputDTO.getBreed());
        shelterPet.setColor(petInputDTO.getColor());
        shelterPet.setGender(petInputDTO.getGender());
        shelterPet.setSize(petInputDTO.getSize());
        shelterPet.setDescription(petInputDTO.getDescription());
        shelterPet.setSpayedNeutered(petInputDTO.isSpayedNeutered());
        shelterPet.setGoodWithKids(petInputDTO.isGoodWithKids());
        shelterPet.setGoodWithDogs(petInputDTO.isGoodWithDogs());
        shelterPet.setGoodWithCats(petInputDTO.isGoodWithCats());

        return shelterPet;
    }
}
