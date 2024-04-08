package nl.jordy.petplacer.util;

import nl.jordy.petplacer.exceptions.CustomAccessDeniedException;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.models.AdoptionRequest;
import nl.jordy.petplacer.models.Shelter;
import nl.jordy.petplacer.repositories.ShelterRepository;
import nl.jordy.petplacer.services.ShelterService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccessValidator {

    private final ShelterRepository shelterRepository;

    public AccessValidator(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public static Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAdmin(Authentication userAuth) {
        return userAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public static void isUserOrAdmin(Authentication userAuth, String requestedUsername) {
        // if the user is not the requested user
        if (!userAuth.getName().equals(requestedUsername) &&
                // if the user is not an admin
                !isAdmin(userAuth)) {

            throw new CustomAccessDeniedException();
        }
    }

    public static void isSheltersManagerOrAdmin(Authentication userAuth, Shelter requestedShelter) {
        // will throw a 403 if the user is not the shelter manager or an admin
        if (!userAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SHELTER_MANAGER")) &&
                !isAdmin(userAuth)) {

            throw new CustomAccessDeniedException();
        }

//         will throw a 403 if the user is not in the managers list of the shelter or an admin
        if (!isAdmin(userAuth) &&
                // fetches the shelter and checks if the user is in the managers list
                !requestedShelter.getManagers()
                        // loops through the managers list and checks if the user is in the list
                        .stream().anyMatch(a -> a.getUsername().equals(userAuth.getName()))) {

            throw new CustomAccessDeniedException();
        }
    }

    public static boolean canAccessAdoptionInfo(Authentication userAuth, AdoptionRequest adoptionRequest) {
        // checks if the user is an admin the shelter manager or the applicant
        return isAdmin(userAuth) ||
                adoptionRequest.
                        getRequestedPet().getShelter().getManagers()
                        .stream()
                        .anyMatch(a -> a.getUsername().equals(userAuth.getName())) ||
                adoptionRequest.getAdoptionApplicant().getUsername().equals(userAuth.getName());
    }
}
