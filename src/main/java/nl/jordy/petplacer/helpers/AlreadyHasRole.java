package nl.jordy.petplacer.helpers;


import org.springframework.security.core.context.SecurityContextHolder;

public class AlreadyHasRole {

    public static boolean hasRole(String role) {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }
}
