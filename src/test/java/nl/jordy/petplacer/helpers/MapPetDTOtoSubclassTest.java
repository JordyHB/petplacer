package nl.jordy.petplacer.helpers;

import nl.jordy.petplacer.dtos.input.PetInputDTO;
import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.dtos.input.UserOwnedPetInputDTO;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.models.Pet;
import nl.jordy.petplacer.models.ShelterPet;
import nl.jordy.petplacer.models.UserOwnedPet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapPetDTOtoSubclassTest {

    private ShelterPetInputDTO shelterPetInputDTO;
    private UserOwnedPetInputDTO userOwnedPetInputDTO;

    @BeforeEach
    public void setup() {
        PetInputDTO petInputDTO = new PetInputDTO();
        petInputDTO.setName("Test Pet");
        petInputDTO.setAge(5);
        petInputDTO.setSpecies("Dog");
        petInputDTO.setBreed("Bulldog");
        petInputDTO.setColor("Brown");
        petInputDTO.setGender(GenderEnum.MALE);
        petInputDTO.setSize("Medium");
        petInputDTO.setDescription("Test Description");
        petInputDTO.setSpayedNeutered(true);
        petInputDTO.setGoodWithKids(true);
        petInputDTO.setGoodWithDogs(true);
        petInputDTO.setGoodWithCats(true);

        shelterPetInputDTO = new ShelterPetInputDTO();
        shelterPetInputDTO.setPet(petInputDTO);

        userOwnedPetInputDTO = new UserOwnedPetInputDTO();
        userOwnedPetInputDTO.setPet(petInputDTO);
    }

    @DisplayName("Maps ShelterPetInputDTO to ShelterPet correctly")
    @Test
    public void testShelterPets() {
        Pet pet = MapPetDTOtoSubclass.mapPetDTOtoSubclass(shelterPetInputDTO, ShelterPet.class, null);

        assertEquals("Test Pet", pet.getName());
        assertEquals(5, pet.getAge());
        assertEquals("Dog", pet.getSpecies());
        assertEquals("Bulldog", pet.getBreed());
        assertEquals("Brown", pet.getColor());
        assertEquals(GenderEnum.MALE, pet.getGender());
        assertEquals("Medium", pet.getSize());
        assertEquals("Test Description", pet.getDescription());
        assertTrue(pet.isSpayedNeutered());
        assertTrue(pet.isGoodWithKids());
        assertTrue(pet.isGoodWithDogs());
        assertTrue(pet.isGoodWithCats());
    }

    @DisplayName("Maps UserOwnedPetInputDTO to UserOwnedPet correctly")
    @Test
    public void testUserOwnedPets() {
        Pet pet = MapPetDTOtoSubclass.mapPetDTOtoSubclass(userOwnedPetInputDTO, UserOwnedPet.class, null);

        assertEquals("Test Pet", pet.getName());
        assertEquals(5, pet.getAge());
        assertEquals("Dog", pet.getSpecies());
        assertEquals("Bulldog", pet.getBreed());
        assertEquals("Brown", pet.getColor());
        assertEquals(GenderEnum.MALE, pet.getGender());
        assertEquals("Medium", pet.getSize());
        assertEquals("Test Description", pet.getDescription());
        assertTrue(pet.isSpayedNeutered());
        assertTrue(pet.isGoodWithKids());
        assertTrue(pet.isGoodWithDogs());
        assertTrue(pet.isGoodWithCats());
    }

    @DisplayName("Maps ShelterPetInputDTO to existing ShelterPet correctly")
    @Test
    public void testExistingShelterPet() {
        ShelterPet existingPet = new ShelterPet();
        existingPet.setName("Old Name");

        Pet pet = MapPetDTOtoSubclass.mapPetDTOtoSubclass(shelterPetInputDTO, ShelterPet.class, existingPet);

        assertEquals("Test Pet", pet.getName());
    }

    @DisplayName("Maps UserOwnedPetInputDTO to existing UserOwnedPet correctly")
    @Test
    public void testExistingUserOwnedPet() {
        UserOwnedPet existingPet = new UserOwnedPet();
        existingPet.setName("Old Name");

        Pet pet = MapPetDTOtoSubclass.mapPetDTOtoSubclass(userOwnedPetInputDTO, UserOwnedPet.class, existingPet);

        assertEquals("Test Pet", pet.getName());
    }

    @DisplayName("Throws IllegalArgumentException when inputDTO does not contain a pet")
    @Test
    public void testThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> MapPetDTOtoSubclass.mapPetDTOtoSubclass(new Object(), ShelterPet.class, null));
    }
}
