package nl.jordy.petplacer.dtos.patch;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.enums.ShelterPetStatus;
import nl.jordy.petplacer.interfaces.ValidEnumValue;

@Getter
@Setter
public class ShelterPetStatusPatchDTO {

    @ValidEnumValue(enumClass = ShelterPetStatus.class)
    @Enumerated(EnumType.STRING)
    private ShelterPetStatus status;
}
