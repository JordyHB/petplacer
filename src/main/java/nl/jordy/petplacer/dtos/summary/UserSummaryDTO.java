package nl.jordy.petplacer.dtos.summary;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.dtos.output.DonationOutputDTO;
import nl.jordy.petplacer.models.UserOwnedPet;

import java.util.List;

@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "username")
public class UserSummaryDTO {

        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        @JsonIdentityReference(alwaysAsId = true)
        private List<ShelterSummaryDTO> managedShelters;
        @JsonIdentityReference(alwaysAsId = true)
        private List<DonationSummaryDTO> donations;
        @JsonIdentityReference(alwaysAsId = true)
        private List<UserOwnedPetSummaryDTO> pets;
        private List<Long> adoptionRequests;
}
