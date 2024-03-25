package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.PetInputDTO;
import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.dtos.output.ShelterPetOutputDTO;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.MapPetDTOtoSubclass;
import nl.jordy.petplacer.models.ShelterPet;
import nl.jordy.petplacer.repositories.ShelterPetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShelterPetServiceTest {

    @Mock
    ShelterPetRepository shelterPetRepository;

    @InjectMocks
    ShelterPetService shelterPetService;

    // has 2 parameters to change it up a bit in the tests
    private ShelterPetInputDTO getShelterPetInputDTO(
            int monthsInShelter,
            String specialNeeds,
            String name,
            int age
    ) {
        ShelterPetInputDTO shelterPetInputDTO = new ShelterPetInputDTO();

        PetInputDTO petInputDTO = getPetInputDTO(name, age);

        // fills the shelterPetInputDTO with the petInputDTO and some extra fields
        shelterPetInputDTO.setPet(petInputDTO);
        shelterPetInputDTO.setMonthsInShelter(monthsInShelter);
        shelterPetInputDTO.setMedicalHistory("healthy");
        shelterPetInputDTO.setSpecialNeeds(specialNeeds);
        shelterPetInputDTO.setPreviousSituation("owner couldn't take care of him anymore"
        );
        return shelterPetInputDTO;
    }

    private PetInputDTO getPetInputDTO(String name, int age) {
        PetInputDTO petInputDTO = new PetInputDTO();
        petInputDTO.setName(name);
        petInputDTO.setSpecies("dog");
        petInputDTO.setBreed("labrador");
        petInputDTO.setColor("brown");
        petInputDTO.setAge(age);
        petInputDTO.setGender(GenderEnum.FEMALE);
        petInputDTO.setSize("large");
        petInputDTO.setDescription("friendly dog");
        petInputDTO.setSpayedNeutered(true);
        petInputDTO.setGoodWithKids(true);
        petInputDTO.setGoodWithDogs(true);
        petInputDTO.setGoodWithCats(true);
        return petInputDTO;
    }

    @DisplayName("Fetch ShelterPet by ID")
    @Test
    public void fetchShelterPetByID() {
        // Arrange
        Long shelterPetID = 1L;
        ShelterPet shelterPet = new ShelterPet();
        ReflectionTestUtils.setField(shelterPet, "id", shelterPetID);
        when(shelterPetRepository.findById(anyLong())).thenReturn(Optional.of(shelterPet));

        // Act
        ShelterPet fetchedShelterPet = shelterPetService.fetchShelterPetByID(shelterPetID);

        // Assert
        assertEquals(shelterPetID, fetchedShelterPet.getId());
    }

    @DisplayName("Should throw an exception when fetching a ShelterPet by ID that does not exist")
    @Test
    public void fetchShelterPetByIDThrowsException() {
        // Arrange
        Long shelterPetID = 12L;
        when(shelterPetRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RecordNotFoundException.class, () -> {
            shelterPetService.fetchShelterPetByID(shelterPetID);
        });
    }

    @DisplayName("Register new pet")
    @Test
    void registerShelterPet() {
        // Arrange
        Long shelterPetID = 12L;
        // Creates the shelter input DTO
        ShelterPetInputDTO shelterPetInputDTO = getShelterPetInputDTO(
                5,
                "tasty treats",
                "bernie",
                7);
        // Maps it to an actual Entity for mocking
        ShelterPet shelterPet = MapPetDTOtoSubclass.mapPetDTOtoSubclass(
                shelterPetInputDTO,
                ShelterPet.class,
                null
        );

        ReflectionTestUtils.setField(shelterPet, "id", shelterPetID);

        when(shelterPetRepository.save(shelterPet)).thenReturn(shelterPet);
        // Act
        ShelterPetOutputDTO storedPet = shelterPetService.registerNewShelterPet(shelterPetInputDTO);

        // Assert
        assertEquals("bernie", storedPet.getName());
        assertEquals("dog", storedPet.getSpecies());
        assertEquals("healthy", storedPet.getMedicalHistory());
        assertEquals(5, storedPet.getMonthsInShelter());
        assertEquals("tasty treats", storedPet.getSpecialNeeds());
        assertEquals("owner couldn't take care of him anymore", storedPet.getPreviousSituation());
        assertThat(storedPet.getDateOfArrival()).isNotNull();
    }

    @Test
    void getALlShelterPets() {
        // Arrange

        ShelterPet shelterPet1 = new ShelterPet();
        ShelterPet shelterPet2 = new ShelterPet();
        shelterPet1.setName("Freddy");
        shelterPet2.setName("Buddy");

        when(shelterPetRepository.findAll()).thenReturn(List.of(shelterPet1, shelterPet2));

        // Act
        List<ShelterPetOutputDTO> shelterPets = shelterPetService.findAllShelterPets();

        // Assert
        assertEquals(2, shelterPets.size());
        assertEquals("freddy", shelterPets.get(0).getName());
        assertEquals("buddy", shelterPets.get(1).getName());

    }

    @Test
    void getShelterPetByID() {
        // Arrange
        Long shelterPetID = 1L;
        ShelterPet shelterPet = MapPetDTOtoSubclass
                .mapPetDTOtoSubclass(
                        getShelterPetInputDTO(
                                3,
                                "Lots of love",
                                "El Ratto",
                                5),
                        ShelterPet.class,
                        null
                );

        ReflectionTestUtils.setField(shelterPet, "id", shelterPetID);
        when(shelterPetRepository.findById(anyLong())).thenReturn(Optional.of(shelterPet));

        // Act
        ShelterPetOutputDTO foundShelterPet = shelterPetService.findShelterPetById(shelterPetID);

        // Assert
        assertEquals(shelterPetID, foundShelterPet.getId());
        assertEquals("el ratto", foundShelterPet.getName());
        assertEquals("dog", foundShelterPet.getSpecies());
        assertEquals("healthy", foundShelterPet.getMedicalHistory());
    }

    @DisplayName("Update ShelterPet by ID")
    @Test
    void updateShelterPetByID() {
        // Arrange
        Long shelterPetID = 14L;
        // Creates the shelter input DTO
        ShelterPetInputDTO oldShelterPetInputDTO = getShelterPetInputDTO(
                10,
                "Couch",
                "Jeff",
                8);

        ShelterPetInputDTO newShelterPetInputDTO = getShelterPetInputDTO(
                5,
                "Tasty Chicken",
                "Zebadiah",
                7);

        // Maps it to an actual Entity for mocking
        ShelterPet shelterPet = MapPetDTOtoSubclass.mapPetDTOtoSubclass(
                oldShelterPetInputDTO,
                ShelterPet.class,
                null
        );

        ReflectionTestUtils.setField(shelterPet, "id", shelterPetID);

        when(shelterPetRepository.findById(shelterPetID)).thenReturn(Optional.of(shelterPet));
        when(shelterPetRepository.save(shelterPet)).thenReturn(shelterPet);

        // Act
        ShelterPetOutputDTO updatedPet = shelterPetService.updateShelterPetByID(shelterPetID, newShelterPetInputDTO);

        // Assert
        assertEquals("zebadiah", updatedPet.getName());
        assertEquals(5, updatedPet.getMonthsInShelter());
        assertEquals("healthy", updatedPet.getMedicalHistory());
        assertEquals(7, updatedPet.getAge());
        assertEquals("tasty chicken", updatedPet.getSpecialNeeds());
        assertEquals("owner couldn't take care of him anymore", updatedPet.getPreviousSituation());
    }

    @DisplayName("Delete ShelterPet by ID")
    @Test
    void deleteShelterPetByID() {
        // Arrange
        Long shelterPetID = 1L;

        ShelterPet shelterPet = MapPetDTOtoSubclass.mapPetDTOtoSubclass(
                getShelterPetInputDTO(
                        5,
                        "Tasty treats",
                        "Bernie",
                        7),
                ShelterPet.class,
                null
        );

        ReflectionTestUtils.setField(shelterPet, "id", shelterPetID);
        when(shelterPetRepository.findById(shelterPetID)).thenReturn(Optional.of(shelterPet));

        // Act
        shelterPetService.deleteShelterPetByID(shelterPetID);

        // Assert
        assertEquals(0, shelterPetRepository.count());
        assertEquals(
                "Shelter Pet: 1 has been successfully deleted.",
                shelterPetService.deleteShelterPetByID(shelterPetID)
        );
    }
}