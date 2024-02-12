package nl.jordy.petplacer.dtos.input;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class UserInputDTO {

    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Email(message = "Email is not valid")
    private String email;

}
