package nl.jordy.petplacer.services;

import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.models.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws RecordNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RecordNotFoundException("Username: " + username + " not found"));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            grantedAuthorities.add((new SimpleGrantedAuthority(authority.getAuthority())));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
