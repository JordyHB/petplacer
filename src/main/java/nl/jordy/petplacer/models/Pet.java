package nl.jordy.petplacer.models;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.enums.GenderEnum;

@Getter
@Setter
@MappedSuperclass
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

