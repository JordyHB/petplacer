package nl.jordy.petplacer.util;

import nl.jordy.petplacer.exceptions.CustomAccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AccessValidator {

    public static Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static void isUserOrAdmin(Authentication userAuth, String requestedUsername) {
        // return true if the user is the requested user or an admin
        if (!userAuth.getName().equals(requestedUsername) &&
                // Check if the user is an admin
                userAuth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            throw new CustomAccessDeniedException();
        }
    }
}
