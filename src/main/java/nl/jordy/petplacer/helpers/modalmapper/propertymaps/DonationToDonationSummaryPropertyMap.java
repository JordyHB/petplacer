package nl.jordy.petplacer.helpers.modalmapper.propertymaps;

import nl.jordy.petplacer.dtos.summary.DonationSummaryDTO;
import nl.jordy.petplacer.helpers.modalmapper.converters.TwoNumberBigDecimalCoverter;
import nl.jordy.petplacer.models.Donation;
import org.modelmapper.PropertyMap;

public class DonationToDonationSummaryPropertyMap extends PropertyMap<Donation, DonationSummaryDTO> {

    @Override
    protected void configure() {
        map().setUsernameDonator(source.getDonator().getUsername());
        map().setReceivingShelterId(source.getReceivingShelter().getId());

        using(new TwoNumberBigDecimalCoverter())
                .map().setDonationAmount(source.getDonationAmount());
    }
}
