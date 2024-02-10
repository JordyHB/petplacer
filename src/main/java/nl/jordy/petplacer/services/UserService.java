package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.UserDto;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    //Injects dependencies
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(UserDto userDto) {

        //Creates user class and fills it with data from the DTO
        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        //Saves it and returns the username
        User savedUser = userRepository.save(user);

        return savedUser.getUsername();

    }

}


