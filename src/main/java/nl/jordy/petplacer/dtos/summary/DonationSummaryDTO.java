package nl.jordy.petplacer.dtos.summary;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DonationSummaryDTO {

    private Long id;
    private String usernameDonator;
    private Long receivingShelterId;
    private BigDecimal donationAmount;
    private String donationMessage;
    private String dateOfDonation;
}
