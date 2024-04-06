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
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            return INVALID;
        }
    }
}
