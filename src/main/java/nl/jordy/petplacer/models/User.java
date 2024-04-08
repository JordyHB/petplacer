package nl.jordy.petplacer.models;

import jakarta.persistence.*;
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
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();


    @ManyToMany(mappedBy = "managers", fetch = FetchType.LAZY)
    private List<Shelter> managedShelters;

    @OneToMany(mappedBy = "currentOwner",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<UserOwnedPet> pets;

    @OneToMany(mappedBy = "donator",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<Donation> donations;

    @OneToMany(mappedBy = "adoptionApplicant",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<AdoptionRequest> adoptionRequests;

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
        this.setUpdatedAt(new Date());
    }

    public void removeAuthority(Authority authority) {

        authorities.remove(authority);
        authority.setUsername(null);
        this.setUpdatedAt(new Date());
    }
}
