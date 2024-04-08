package nl.jordy.petplacer.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    private boolean isAdopted;
    private int yearsOwned;
    private Date dateOfRegistration = new Date();
    private Date dateOfLastUpdate = new Date();

    @OneToOne(mappedBy = "userOwnedPet", cascade = CascadeType.ALL)
    private Image image;

    @ManyToOne
    private User currentOwner;
}
