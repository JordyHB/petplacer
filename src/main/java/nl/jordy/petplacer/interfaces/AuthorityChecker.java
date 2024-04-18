package nl.jordy.petplacer.interfaces;

import nl.jordy.petplacer.models.User;

public interface AuthorityChecker {
    boolean fetchedUserHasAuthority (User user, String authName);
}
