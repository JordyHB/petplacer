package nl.jordy.petplacer.models;


import jakarta.persistence.Column;
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

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String species;
    @Column(nullable = false)
    private String breed;
    private String color;
    @Column(nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @ValidEnumValue(enumClass = GenderEnum.class)
    @Column(nullable = false)
    private GenderEnum gender;

    private String size;
    private String description;
    @Column(nullable = false)
    private boolean spayedNeutered;
    @Column(nullable = false)
    private boolean goodWithKids;
    @Column(nullable = false)
    private boolean goodWithDogs;
    @Column(nullable = false)
    private boolean goodWithCats;
}

