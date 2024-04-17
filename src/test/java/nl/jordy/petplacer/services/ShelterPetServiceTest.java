package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.dtos.output.ShelterPetOutputDTO;
import nl.jordy.petplacer.dtos.patch.ShelterPetPatchDTO;
import nl.jordy.petplacer.dtos.patch.ShelterPetStatusPatchDTO;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.enums.ShelterPetStatus;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.ShelterPet;
import nl.jordy.petplacer.repositories.ShelterPetRepository;
import nl.jordy.petplacer.specifications.ShelterPetSpecification;
import nl.jordy.petplacer.util.AccessValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShelterPetServiceTest {

    @Mock
    ShelterPetRepository shelterPetRepository;

    @Mock
    ShelterService shelterService;

    @Mock
    AccessValidator accessValidator;

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

        shelterPetInputDTO.setName(name);
        shelterPetInputDTO.setSpecies("dog");
        shelterPetInputDTO.setBreed("labrador");
        shelterPetInputDTO.setColor("brown");
        shelterPetInputDTO.setAge(age);
        shelterPetInputDTO.setGender(GenderEnum.FEMALE);
        shelterPetInputDTO.setSize("large");
        shelterPetInputDTO.setDescription("friendly dog");
        shelterPetInputDTO.setSpayedNeutered(true);
        shelterPetInputDTO.setGoodWithKids(true);
        shelterPetInputDTO.setGoodWithDogs(true);
        shelterPetInputDTO.setGoodWithCats(true);
        shelterPetInputDTO.setMonthsInShelter(monthsInShelter);
        shelterPetInputDTO.setMedicalHistory("healthy");
        shelterPetInputDTO.setSpecialNeeds(specialNeeds);
        shelterPetInputDTO.setPreviousSituation("owner couldn't take care of him anymore");
        return shelterPetInputDTO;
    }

    private ShelterPetPatchDTO getShelterPetPatchDTO(
            int monthsInShelter,
            String specialNeeds,
            String name,
            int age
    ) {
        ShelterPetPatchDTO shelterPetPatchDTO = new ShelterPetPatchDTO();

        shelterPetPatchDTO.setName(name);
        shelterPetPatchDTO.setAge(age);
        shelterPetPatchDTO.setSpecies("dog");
        shelterPetPatchDTO.setBreed("labrador");
        shelterPetPatchDTO.setColor("brown");
        shelterPetPatchDTO.setAge(age);
        shelterPetPatchDTO.setGender(GenderEnum.MALE);
        shelterPetPatchDTO.setSize("large");
        shelterPetPatchDTO.setDescription("friendly dog");
        shelterPetPatchDTO.setSpayedNeutered(true);
        shelterPetPatchDTO.setGoodWithKids(true);
        shelterPetPatchDTO.setGoodWithDogs(true);
        shelterPetPatchDTO.setGoodWithCats(true);
        shelterPetPatchDTO.setMonthsInShelter(monthsInShelter);
        shelterPetPatchDTO.setSpecialNeeds(specialNeeds);
        return shelterPetPatchDTO;
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
        ShelterPet shelterPet = ModelMapperHelper.getModelMapper().map(
                shelterPetInputDTO,
                ShelterPet.class
        );

        ReflectionTestUtils.setField(shelterPet, "id", shelterPetID);

        when(shelterPetRepository.save(any(ShelterPet.class)))
                .thenReturn(shelterPet);
        // Act
        ShelterPetOutputDTO storedPet = shelterPetService.registerNewShelterPet(232L, shelterPetInputDTO);

        // Assert
        assertEquals("bernie", storedPet.getName());
        assertEquals("dog", storedPet.getSpecies());
        assertEquals("healthy", storedPet.getMedicalHistory());
        assertEquals(5, storedPet.getMonthsInShelter());
        assertEquals("tasty treats", storedPet.getSpecialNeeds());
        assertEquals("owner couldn't take care of him anymore", storedPet.getPreviousSituation());
        assertThat(storedPet.getDateOfLastUpdate()).isNotNull();
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
        ShelterPet shelterPet = ModelMapperHelper.getModelMapper().map(
                getShelterPetInputDTO(
                        3,
                        "Lots of love",
                        "El Ratto",
                        5),
                ShelterPet.class
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

        ShelterPetPatchDTO newShelterPetPatchDTO = getShelterPetPatchDTO(
                5,
                "tasty chicken",
                "zebadiah",
                7);

        // Maps it to an actual Entity for mocking
        ShelterPet shelterPet = ModelMapperHelper.getModelMapper().map(
                oldShelterPetInputDTO,
                ShelterPet.class
        );

        ReflectionTestUtils.setField(shelterPet, "id", shelterPetID);

        when(shelterPetRepository.findById(shelterPetID)).thenReturn(Optional.of(shelterPet));
        when(shelterPetRepository.save(shelterPet)).thenReturn(shelterPet);

        // Act
        ShelterPetOutputDTO updatedPet = shelterPetService.updateShelterPetByID(shelterPetID, newShelterPetPatchDTO);

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

        ShelterPet shelterPet = ModelMapperHelper.getModelMapper().map(
                getShelterPetInputDTO(
                        5,
                        "Tasty treats",
                        "Bernie",
                        7),
                ShelterPet.class
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

    @DisplayName("Update ShelterPetStatus")
    @Test
    void updateShelterPetStatus() {
        // Arrange
        Long shelterPetId = 5L;
        ShelterPetStatusPatchDTO statusPatchDTO = new ShelterPetStatusPatchDTO();
        statusPatchDTO.setStatus(ShelterPetStatus.RESERVED);

        ShelterPet shelterPet = ModelMapperHelper.getModelMapper().map(
                getShelterPetInputDTO(5, "has trouble looking", "bert", 2),
                ShelterPet.class
        );
        ReflectionTestUtils.setField(shelterPet, "id", shelterPetId);
        ShelterPet updatedShelterPet = ModelMapperHelper.getModelMapper().map(shelterPet, ShelterPet.class);
        ReflectionTestUtils.setField(updatedShelterPet, "id", shelterPetId);
        updatedShelterPet.setStatus(ShelterPetStatus.RESERVED);

        when(shelterPetRepository.findById(shelterPetId)).thenReturn(Optional.of(shelterPet));
        when(shelterPetRepository.save(shelterPet)).thenReturn(updatedShelterPet);

        // Act
        ShelterPetOutputDTO updatedShelterPetDTO = shelterPetService
                .updateShelterPetStatus(shelterPetId, statusPatchDTO);

        // Assert
        assertEquals(ShelterPetStatus.RESERVED, updatedShelterPetDTO.getStatus());
    }

    @DisplayName("Test find all shelter pets by parameters")
    @Test
    void findAllShelterPetsByParameters() {
        // Arrange
        ShelterPet shelterPet1 = new ShelterPet();
        ShelterPet shelterPet2 = new ShelterPet();
        ShelterPet shelterPet3 = new ShelterPet();
        shelterPet1.setName("freddy");
        shelterPet2.setName("freek");
        shelterPet3.setName("frederick");
        shelterPet1.setSpecies("dog");
        shelterPet2.setSpecies("dog");
        shelterPet3.setSpecies("dog");
        shelterPet1.setBreed("labrador");
        shelterPet2.setBreed("siamese");
        shelterPet3.setBreed("golden retriever");
        List<ShelterPet> shelterPets = List.of(shelterPet1, shelterPet2, shelterPet3);

        when(shelterPetRepository.findAll(any(ShelterPetSpecification.class))).thenReturn(shelterPets);

        // Act

        List<ShelterPetOutputDTO> foundShelterPets = shelterPetService.findShelterPetsByParams(
                "fre",
                "dog",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // Assert
        assertEquals(3, foundShelterPets.size());
        assertEquals("freddy", foundShelterPets.get(0).getName());
        assertEquals("freek", foundShelterPets.get(1).getName());
    }
}