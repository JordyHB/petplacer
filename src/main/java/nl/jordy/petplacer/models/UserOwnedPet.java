package nl.jordy.petplacer.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_owned_pets")
public class UserOwnedPet extends Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // blocks the setter for ID
    @Setter(AccessLevel.NONE)
    private Long id;

    private boolean firsOwner;
    private boolean adopted;
    private int yearsOwned;

}
