package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.dtos.output.UserOutputDTO;
import nl.jordy.petplacer.exceptions.BadRequestException;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    //Injects dependencies
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // transforms the DTO to a User
    public User setDTOtoUser(UserInputDTO userInputDTO) {
        User user = new User();
        user.setUsername(userInputDTO.getUsername());
        user.setFirstName(userInputDTO.getFirstName());
        user.setLastName(userInputDTO.getLastName());
        user.setEmail(userInputDTO.getEmail());
        return user;
    }

    // Transforms the User to a DTO
    public UserOutputDTO setUserToDTO(User user) {
        UserOutputDTO userOutputDTO = new UserOutputDTO();
        userOutputDTO.setId(user.getId());
        userOutputDTO.setUsername(user.getUsername());
        userOutputDTO.setFirstName(user.getFirstName());
        userOutputDTO.setLastName(user.getLastName());
        userOutputDTO.setEmail(user.getEmail());
        return userOutputDTO;
    }

    public UserOutputDTO registerUser(UserInputDTO userDto) {
        User user = setDTOtoUser(userDto);

        //Saves the user to the database
        userRepository.save(user);

        //Transforms the user to a DTO and returns it
        return setUserToDTO(user);
    }

    public List<UserOutputDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserOutputDTO> outputDTOS = new ArrayList<>();

        for (User user: users) {
            outputDTOS.add(setUserToDTO(user));
        }
        return outputDTOS;
    }

    public UserOutputDTO findUserById(Long userId) {
        // Gets the user
        Optional<User> optionalUser = userRepository.findById(userId);
        // extracts the user and throws an exception if none is present
        User user = optionalUser.orElseThrow(() -> new BadRequestException("PLACEHOLDER"));
        return setUserToDTO(user);
    }

}


