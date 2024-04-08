package nl.jordy.petplacer.helpers.modalmapper.propertymaps;


import nl.jordy.petplacer.dtos.output.DonationOutputDTO;
import nl.jordy.petplacer.helpers.modalmapper.converters.ShelterToSummaryConverter;
import nl.jordy.petplacer.helpers.modalmapper.converters.TwoNumberBigDecimalCoverter;
import nl.jordy.petplacer.models.Donation;
import org.modelmapper.PropertyMap;

public class DonationToDonationOutputPropertyMap extends PropertyMap<Donation, DonationOutputDTO> {

    @Override
    protected void configure() {
        using(new ShelterToSummaryConverter())
                .map(source.getReceivingShelter(), destination.getReceivingShelter());

        using(new TwoNumberBigDecimalCoverter())
                .map(source.getDonationAmount(), destination.getDonationAmount());
    }
}