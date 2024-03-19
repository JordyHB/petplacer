package nl.jordy.petplacer.models;

//imports
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // blocks the setter for ID
    @Setter(AccessLevel.NONE)
    private Long id;

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
            mappedBy = "id",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
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
        authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        authorities.remove(authority);
    }
}
