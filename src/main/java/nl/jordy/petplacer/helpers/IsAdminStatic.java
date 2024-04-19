package nl.jordy.petplacer.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class IsAdminStatic {
    public static boolean isAdminStatic() {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        return userAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
