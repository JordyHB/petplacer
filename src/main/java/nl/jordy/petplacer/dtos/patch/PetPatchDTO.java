package nl.jordy.petplacer.dtos.patch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.enums.GenderEnum;

@Getter
@Setter
public class PetPatchDTO {
    @Size(max = 20, message = "Name should be less than 20 characters")
    private String name;

    @Size(max = 30, message = "Species should be less than 30 characters")
    private String species;

    @Size(max = 30, message = "Breed should be less than 30 characters")
    private String breed;

    @Size(max = 40, message = "Color should be less than 40 characters")
    private String color;

    @Min(value = 0, message = "Age must be 0 or greater")
    private int age;

    //    @Pattern(regexp = "MALE|FEMALE|UKNOWN|OTHER", message="Gender must be MALE, FEMALE, UNKNOWN or OTHER")
    private GenderEnum gender;

    @Size(max = 20, message = "Size should be less than 20 characters")
    private String size;

    private String description;
    private Boolean SpayedNeutered;
    private Boolean GoodWithKids;
    private Boolean GoodWithDogs;
    private Boolean GoodWithCats;
}
