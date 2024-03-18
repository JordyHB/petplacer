package nl.jordy.petplacer.models;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import nl.jordy.petplacer.enums.GenderEnum;

@Data
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pet {

    private String name;
    private String species;
    private String breed;
    private String color;
    private int age;
    private GenderEnum gender;
    private String size;
    // TDDO: add weight
    private String description;
    private boolean spayedNeutered;
    private boolean goodWithKids;
    private boolean goodWithDogs;
    private boolean goodWithCats;
}

