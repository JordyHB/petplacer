package nl.jordy.petplacer.dtos.output;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.dtos.summary.ShelterSummaryDTO;
import nl.jordy.petplacer.dtos.summary.UserSummaryDTO;
import nl.jordy.petplacer.interfaces.HasFetchableId;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class DonationOutputDTO implements HasFetchableId {

    private Long id;
    private UserSummaryDTO donator;
    private ShelterSummaryDTO receivingShelter = new ShelterSummaryDTO();
    private BigDecimal donationAmount;
    private String donationMessage;
    private Date dateOfDonation;
    private Date dateOfLastUpdate;
}
