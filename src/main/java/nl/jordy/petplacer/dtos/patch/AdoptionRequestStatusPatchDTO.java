package nl.jordy.petplacer.dtos.patch;

import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.enums.AdoptionRequestStatus;
import nl.jordy.petplacer.interfaces.ValidEnumValue;

@Getter
@Setter
public class AdoptionRequestStatusPatchDTO {

    @ValidEnumValue(enumClass = AdoptionRequestStatus.class)
    private AdoptionRequestStatus status;
}
