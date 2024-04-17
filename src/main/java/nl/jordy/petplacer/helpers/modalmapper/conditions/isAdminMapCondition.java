package nl.jordy.petplacer.helpers.modalmapper.conditions;

import nl.jordy.petplacer.dtos.patch.DonationPatchDTO;
import nl.jordy.petplacer.exceptions.CustomAccessDeniedException;
import nl.jordy.petplacer.models.Donation;
import nl.jordy.petplacer.util.AccessValidator;
import org.modelmapper.Condition;
import org.modelmapper.spi.MappingContext;

public class isAdminMapCondition implements Condition<DonationPatchDTO, Donation> {

    private final AccessValidator accessValidator;

    public isAdminMapCondition(AccessValidator accessValidator) {
        this.accessValidator = accessValidator;
    }
    @Override
    public boolean applies(MappingContext<DonationPatchDTO, Donation> context) {

        // exits if the donation amount is not being set
        if (context.getSource() == null) {
            return false;
        }

        if(accessValidator.isAdmin(accessValidator.getAuth())) {
            return true;
        } else {
            throw new CustomAccessDeniedException("Only admins can change the donation amount");
        }
    }
}
