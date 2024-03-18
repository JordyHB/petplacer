package nl.jordy.petplacer.dtos.output;

import lombok.Data;
import nl.jordy.petplacer.interfaces.HasFetchableId;

import java.util.Date;

@Data
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

}
