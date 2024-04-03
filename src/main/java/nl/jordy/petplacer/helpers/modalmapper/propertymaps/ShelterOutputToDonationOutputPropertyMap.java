package nl.jordy.petplacer.helpers.modalmapper.propertymaps;


import nl.jordy.petplacer.dtos.output.DonationOutputDTO;
import nl.jordy.petplacer.helpers.modalmapper.ShelterToSummaryConverter;
import nl.jordy.petplacer.models.Donation;
import org.modelmapper.PropertyMap;

public class ShelterOutputToDonationOutputPropertyMap extends PropertyMap<Donation, DonationOutputDTO> {

    @Override
    protected void configure() {
        using(new ShelterToSummaryConverter())
                .map(source.getReceivingShelter(), destination.getReceivingShelter());
    }
}