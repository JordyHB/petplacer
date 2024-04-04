package nl.jordy.petplacer.helpers.modalmapper.propertymaps;

import nl.jordy.petplacer.dtos.patch.DonationPatchDTO;
import nl.jordy.petplacer.helpers.modalmapper.conditions.isAdminMapCondition;
import nl.jordy.petplacer.models.Donation;
import org.modelmapper.PropertyMap;


public class DonationPatchToDonationPropertyMap extends PropertyMap<DonationPatchDTO, Donation> {

    @Override
    protected void configure() {

        when(new isAdminMapCondition())
                .map().setDonationAmount(source.getDonationAmount());
    }
}
