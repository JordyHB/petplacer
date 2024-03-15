package nl.jordy.petplacer.models;

//imports
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // blocks the setter for ID
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToMany(mappedBy = "managers")
    private List<Shelter> managedShelters;

    @OneToMany(mappedBy = "currentOwner")
    private List<UserOwnedPet> pets;

    private String username;
    private String firstName;
    private String lastName;
    private String email;


}
