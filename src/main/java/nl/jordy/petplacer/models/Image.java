package nl.jordy.petplacer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    
    private byte[] imageData;

    @OneToOne(fetch = FetchType.LAZY)
    ShelterPet shelterPet;

    @OneToOne(fetch = FetchType.LAZY)
    UserOwnedPet userOwnedPet;


}
