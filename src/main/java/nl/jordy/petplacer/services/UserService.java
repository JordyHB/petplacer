package nl.jordy.petplacer.services;

import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }
}


