package nl.jordy.petplacer.dtos.output;

import lombok.Data;
import nl.jordy.petplacer.models.Authority;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class UserOutputDTO {

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
