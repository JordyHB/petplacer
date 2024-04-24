package nl.jordy.petplacer.util;

import nl.jordy.petplacer.exceptions.CustomAccessDeniedException;
import nl.jordy.petplacer.models.AdoptionRequest;
import nl.jordy.petplacer.models.Donation;
import nl.jordy.petplacer.models.Shelter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccessValidator {

    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isAdmin(Authentication userAuth) {
        // returns true if the user is an admin
        return userAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public void isUserOrAdmin(Authentication userAuth, String requestedUsername) {
        // if the user is not the requested user
        if (!userAuth.getName().equals(requestedUsername) &&
                // if the user is not an admin
                !isAdmin(userAuth)) {

            throw new CustomAccessDeniedException();
        }
    }

    public boolean isSheltersManagerOrAdminFilterOnly(Authentication userAuth, Shelter requestedShelter) {
        // returns true if the user is the shelter manager or an admin
        return requestedShelter.getManagers()
                .stream()
                .anyMatch(a -> a.getUsername().equals(userAuth.getName())) ||
                isAdmin(userAuth);
    }

    public void isSheltersManagerOrAdmin(Authentication userAuth, Shelter requestedShelter) {
        // will throw a 403 if the user is not a shelter manager or an admin
        if (userAuth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_SHELTER_MANAGER")) &&
                !isAdmin(userAuth)) {

            throw new CustomAccessDeniedException();
        }

//         will throw a 403 if the user is not in the managers list of the shelter or an admin
        if (!isAdmin(userAuth) &&
                requestedShelter.getManagers()
                        // loops through the managers list and checks if the user is in the list
                        .stream().noneMatch(a -> a.getUsername().equals(userAuth.getName()))) {

            throw new CustomAccessDeniedException();
        }
    }

    public boolean canAccessAdoptionInfo(Authentication userAuth, AdoptionRequest adoptionRequest) {
        // returns true if the user is an admin, the shelter manager, or the applicant
        return isAdmin(userAuth) ||
                // checks if the user is in the managers list of the shelter
                adoptionRequest.
                        getRequestedPet().getShelter().getManagers()
                        .stream()
                        .anyMatch(a -> a.getUsername().equals(userAuth.getName())) ||
                // checks if the user is the applicant
                adoptionRequest.getAdoptionApplicant().getUsername().equals(userAuth.getName());
    }

    public boolean canAccessDonationInfo(Authentication userAuth, Donation donation) {
        // returns true if the user is an admin, the shelter manager, or the donator
        return isAdmin(userAuth) ||
                // checks if the user is in the managers list of the shelter
                isSheltersManagerOrAdminFilterOnly(userAuth, donation.getReceivingShelter()) ||
                // checks if the user is the donator
                donation.getDonator().getUsername().equals(userAuth.getName());
    }
}
