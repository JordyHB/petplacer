package nl.jordy.petplacer.helpers;


import nl.jordy.petplacer.models.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AlreadyHasRole {

    public static boolean hasRole(String role) {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }

    public static boolean fetchedUserHasRole(User user, String role) {
        return user.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }
}
