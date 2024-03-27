package nl.jordy.petplacer.dtos.output;

import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.interfaces.HasFetchableId;

@Getter
@Setter
public class DonationOutputDTO implements HasFetchableId {

    private Long id;
}
