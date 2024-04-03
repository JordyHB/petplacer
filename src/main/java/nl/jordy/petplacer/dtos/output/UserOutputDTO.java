package nl.jordy.petplacer.dtos.output;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.dtos.summary.ShelterSummaryDTO;
import nl.jordy.petplacer.models.Authority;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "username")
public class UserOutputDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date createdAt;
    private Date updatedAt;
    private Set<Authority> authorities;
    private List<ShelterSummaryDTO> managedShelters;
    private List<Long> pets;
    @JsonIdentityReference(alwaysAsId = true)
    private List<DonationOutputDTO> donations;
    private List<Long> adoptionRequests;
}
