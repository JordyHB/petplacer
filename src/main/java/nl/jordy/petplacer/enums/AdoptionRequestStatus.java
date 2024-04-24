package nl.jordy.petplacer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AdoptionRequestStatus {

    PENDING,
    APPROVED,
    REJECTED,
    INVALID;

    @JsonCreator
    public static AdoptionRequestStatus valueOfOrDefault(String value) {
        // catch illegal argument exception when being deserialized and sets it to invalid to handle invalid values later
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            return INVALID;
        }
    }
}
