package nl.jordy.petplacer.helpers;


import nl.jordy.petplacer.interfaces.AuthorityChecker;
import nl.jordy.petplacer.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserAuthChecker implements AuthorityChecker {

    @Override
    public boolean fetchedUserHasAuthority(User user, String role) {
        return user.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }
}
