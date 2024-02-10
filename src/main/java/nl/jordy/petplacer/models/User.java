package nl.jordy.petplacer.models;

//imports
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import nl.jordy.petplacer.interfaces.HasFetchableId;

@Entity
@Data
@Table(name = "users")
public class User implements HasFetchableId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // blocks the setter for ID
    @Setter(AccessLevel.NONE)
    private Long id;

    private String username;
    private String firstName;
    private String lastName;
    private String email;


}
