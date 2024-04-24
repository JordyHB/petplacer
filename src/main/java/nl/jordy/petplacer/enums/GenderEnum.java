package nl.jordy.petplacer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GenderEnum {
    MALE,
    FEMALE,
    UNKNOWN,
    OTHER,
    INVALID;

    @JsonCreator
    public static GenderEnum valueOfOrDefault(String value) {
        // catch illegal argument exception when being deserialized and sets it to invalid to handle invalid values later
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            return INVALID;
        }
    }
}
