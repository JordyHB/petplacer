package nl.jordy.petplacer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum AdoptionRequestStatus {

    PENDING,
    APPROVED,
    REJECTED,
    INVALID;

    @JsonProperty


    @JsonCreator
    public static AdoptionRequestStatus valueOfOrDefault(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            return INVALID;
        }
    }

}
