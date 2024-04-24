package nl.jordy.petplacer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ShelterPetStatus {
    AVAILABLE,
    RESERVED,
    ADOPTED,
    NO_LONGER_AVAILABLE,
    INVALID;

    @JsonCreator
    public static ShelterPetStatus valueOfOrDefault(String value) {
        // catch illegal argument exception when being deserialized and sets it to invalid to handle invalid values later
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            return INVALID;
        }
    }
}
