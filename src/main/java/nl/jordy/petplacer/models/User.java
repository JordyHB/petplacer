package nl.jordy.petplacer.models;

//imports
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private boolean enabled = true;
    @Column(nullable = false)
    private Date createdAt = new Date();
    @Column(nullable = false)
    private Date updatedAt = new Date();

    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username", // Use the association to the User entity
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    @ManyToMany(mappedBy = "managers")
    private List<Shelter> managedShelters;

    @OneToMany(mappedBy = "currentOwner")
    private List<UserOwnedPet> pets;

    @OneToMany(mappedBy = "donator")
    private List<Donation> donations;

    @OneToMany(mappedBy = "adoptionApplicant")
    private List<AdoptionRequest> adoptionRequests;

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        authorities.remove(authority);
    }
}
