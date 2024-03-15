package nl.jordy.petplacer.dtos.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShelterInputDTO {

    @NotBlank(message = "Shelter name is required")
    @Size(min = 2, max = 50, message = "Shelter name must be between 2 and 50 characters")
    private String shelterName;

    @NotBlank(message = "Phone number is required")
    // TODO: add phone number validation
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
}
