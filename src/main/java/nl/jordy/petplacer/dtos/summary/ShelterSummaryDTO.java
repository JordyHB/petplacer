package nl.jordy.petplacer.dtos.summary;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ShelterSummaryDTO {

    private Long id;
    private String shelterName;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String postalCode;
    private String description;
    private String website;
    private String facilities;
    private String openingHours;
    private Date dateOfRegistration;

    @JsonIdentityReference(alwaysAsId = true)
    private List<UserSummaryDTO> managers;
    @JsonIdentityReference(alwaysAsId = true)
    private List<DonationSummaryDTO> donations;
    @JsonIdentityReference(alwaysAsId = true)
    private List<ShelterPetSummaryDTO> shelterPets;

}
