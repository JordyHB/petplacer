package nl.jordy.petplacer.dtos.patch;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DonationPatchDTO {

    @DecimalMin(value = "0.01", message = "Donation amount must be greater than 0")
    @DecimalMax(value = "1000.00", message = "For donations greater than 1000,00 please contact us")
    private BigDecimal donationAmount;

    @NotBlank(message = "Donation message is required")
    @Size(max = 500, message = "Donation message is too long")
    private String donationMessage;
}
