package nl.jordy.petplacer.dtos.output;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import nl.jordy.petplacer.interfaces.HasFetchableId;
import nl.jordy.petplacer.models.Authority;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class UserOutputDTO implements HasFetchableId {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date createdAt;
    private Date updatedAt;
    private Set<Authority> authorities;
    private List<ShelterOutputDTO> managedShelters;
    private List<Long> pets;
    private List<Long> donations;
    private List<Long> adoptionRequests;

}
