package nl.jordy.petplacer.util;

import nl.jordy.petplacer.exceptions.CustomAccessDeniedException;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
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

    public static void isUserOrAdmin(Authentication userAuth, String requestedUsername) {
        // if the user is not the requested user
        if (!userAuth.getName().equals(requestedUsername) &&
                // if the user is not an admin
                !userAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            throw new CustomAccessDeniedException();
        }
    }

    public void isSheltersManagerOrAdmin(Authentication userAuth, Long requestedShelterID) {
        // will throw a 403 if the user is not the shelter manager or an admin
        if (!userAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SHELTER_MANAGER")) &&
                !userAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            throw new CustomAccessDeniedException();
        }

//         will throw a 403 if the user is not in the managers list of the shelter or an admin
        if (!userAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) &&
                // fetches the shelter and checks if the user is in the managers list
                !shelterRepository.findById(requestedShelterID)
                        .orElseThrow(RecordNotFoundException::new)
                        .getManagers()
                        // loops through the managers list and checks if the user is in the list
                        .stream().anyMatch(a -> a.getUsername().equals(userAuth.getName()))) {

            throw new CustomAccessDeniedException();
        }
    }
}
