package nl.jordy.petplacer.util;

import nl.jordy.petplacer.exceptions.CustomAccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AccessValidator {

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
}
