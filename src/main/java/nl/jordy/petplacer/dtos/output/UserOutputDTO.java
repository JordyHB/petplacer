package nl.jordy.petplacer.dtos.output;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import nl.jordy.petplacer.interfaces.HasFetchableId;

import java.util.List;

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
    private List<ShelterOutputDTO> managedShelters;
    private List<Long> pets;
    private List<Long> donations;
    private List<Long> adoptionRequests;

}
