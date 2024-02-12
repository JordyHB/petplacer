package nl.jordy.petplacer.dtos.output;

import lombok.Data;
import nl.jordy.petplacer.interfaces.HasFetchableId;

@Data
public class UserOutputDTO implements HasFetchableId {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

}
