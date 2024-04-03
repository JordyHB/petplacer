package nl.jordy.petplacer.dtos.output;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.dtos.summary.ShelterPetSummaryDTO;
import nl.jordy.petplacer.dtos.summary.UserSummaryDTO;
import nl.jordy.petplacer.interfaces.HasFetchableId;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ShelterOutputDTO implements HasFetchableId {

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
    private Date dateOfLastUpdate;
    private List<UserSummaryDTO>managers;
    private List<DonationOutputDTO> donations;
    private List<ShelterPetSummaryDTO> shelterPets;

}
