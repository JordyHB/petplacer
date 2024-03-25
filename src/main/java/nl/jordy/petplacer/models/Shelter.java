package nl.jordy.petplacer.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "shelters")
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // blocks the setter for ID
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, unique = true)
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

    @Column(name = "date_of_registration")
    private Date dateOfRegistration = new Date();

    @Column(name = "date_of_last_update")
    private Date dateOfLastUpdate = new Date();

//    @OneToMany(mappedBy = "shelter")
//    private List<ShelterPet> shelterPets;

    @OneToMany(mappedBy = "receivingShelter")
    private List<Donation> donations;

    @OneToMany(mappedBy = "requestedPetShelter")
    private List<AdoptionRequest> adoptionRequests;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "shelter_managers",
            joinColumns = @JoinColumn(name = "shelter_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> managers = new ArrayList<>() {
    };
}
