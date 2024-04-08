package nl.jordy.petplacer.models;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.interfaces.ValidEnumValue;

@Getter
@Setter
@MappedSuperclass
public abstract class Pet {

    private String name;
    private String species;
    private String breed;
    private String color;
    private int age;

    @Enumerated(EnumType.STRING)
    @ValidEnumValue(enumClass = GenderEnum.class)
    private GenderEnum gender;

    private String size;
    private String description;
    private boolean spayedNeutered;
    private boolean goodWithKids;
    private boolean goodWithDogs;
    private boolean goodWithCats;
}

