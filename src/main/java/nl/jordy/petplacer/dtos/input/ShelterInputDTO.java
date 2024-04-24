package nl.jordy.petplacer.dtos.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShelterInputDTO {

    @NotBlank(message = "Shelter name is required")
    @Size(min = 2, max = 50, message = "Shelter name must be between 2 and 50 characters")
    private String shelterName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+31|0|0031)[6-9][0-9]{8}$",
            message = "Please enter a valid Dutch phone number. (e.g. 0612345678)")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Address is required")
    @Size(min = 2, max = 50, message = "Address must be between 2 and 50 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 30, message = "City must be between 2 and 30 characters")
    private String city;

    @NotBlank(message = "Postal code is required")
    @Size(min = 6, max = 6, message = "Postal code must be 6 characters")
    private String postalCode;

    @NotBlank(message = "Description is required")
    @Size(min = 2, max = 500, message = "Description must be between 2 and 500 characters")
    private String description;

    @Pattern(regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(/[\\w-]+)*(\\?\\S+)?",
            message = "Please enter a valid URL. (e.g. https://www.example.com)")
    private String website;

    @Size(min = 2, max = 500, message = "Facilities must be between 2 and 500 characters")
    private String facilities;

    @NotBlank(message = "Opening hours are required")
    @Size(min = 2, max = 100, message = "Opening hours must be between 2 and 100 characters")
    private String openingHours;
}
