package nl.jordy.petplacer.dtos.input;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DonationInputDTO {

    @NotNull(message = "User ID is required")
    @Min(value = 1, message = "User ID must be greater than 0")
    private Long userId;

    @NotNull(message = "Shelter ID is required")
    @Min(value = 1, message = "Shelter ID must be greater than 0")
    private Long shelterId;

    @NotBlank(message = "Donation amount is required")
    @DecimalMin(value = "0.01", message = "Donation amount must be greater than 0")
    @DecimalMax(value = "1000.00", message = "For donations greater than 1000,00 please contact us")
    private BigDecimal donationAmount;

    @NotBlank(message = "Donation message is required")
    @Size(max = 500, message = "Donation message is too long")
    private String donationMessage;
}
