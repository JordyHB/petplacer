package nl.jordy.petplacer.dtos.input;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.interfaces.ValidEnumValue;

@Getter
@Setter
public class PetInputDTO {
    @NotBlank(message = "Name is required")
    @Size(max = 20, message = "Name should be less than 20 characters")
    private String name;

    @NotBlank(message = "Species is required")
    @Size(max = 30, message = "Species should be less than 30 characters")
    private String species;

    @NotBlank(message = "Breed is required")
    @Size(max = 30, message = "Breed should be less than 30 characters")
    private String breed;

    @NotBlank(message = "Color is required")
    @Size(max = 40, message = "Color should be less than 40 characters")
    private String color;

    @Min(value = 0, message = "Age must be 0 or greater")
    @Max(value = 200, message = "Age must be 200 or less")
    private int age;

    @NotNull(message = "gender is required")
    @ValidEnumValue(enumClass = GenderEnum.class)
    private GenderEnum gender;

    @NotBlank(message = "Size is required")
    @Size(max = 20, message = "Size should be less than 20 characters")
    private String size;

    @Size(max = 500, message = "Description should be less than 500 characters")
    private String description;

    @NotNull(message = "Spayed/Neutered is required")
    private Boolean SpayedNeutered;

    @NotNull(message = "Good with fields are mandatory")
    private Boolean GoodWithKids;

    @NotNull(message = "Good with fields are mandatory")
    private Boolean GoodWithDogs;

    @NotNull(message = "Good with fields are mandatory")
    private Boolean GoodWithCats;
}
