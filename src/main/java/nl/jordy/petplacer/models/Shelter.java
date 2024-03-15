package nl.jordy.petplacer.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;


@Entity
@Data
@Table(name = "shelters")
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // blocks the setter for ID
    @Setter(AccessLevel.NONE)
    private Long id;

    private String shelterName;

    @OneToMany(mappedBy = "shelter")
    private List<ShelterPet> shelterPets;

    @ManyToMany
    private List<User> managers;

    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String postalCode;
}
