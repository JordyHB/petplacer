package nl.jordy.petplacer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    private String name;
    private String type;

    @Column(nullable = false)
    private byte[] imageData;

    @OneToOne(fetch = FetchType.LAZY)
    ShelterPet shelterPet;

    @OneToOne(fetch = FetchType.LAZY)
    UserOwnedPet userOwnedPet;


}
