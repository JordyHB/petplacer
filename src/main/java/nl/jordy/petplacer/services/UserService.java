package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.dtos.output.UserOutputDTO;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
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
    private User setDTOtoUser(UserInputDTO userInputDTO) {
        User user = new User();
        user.setUsername(userInputDTO.getUsername());
        user.setFirstName(userInputDTO.getFirstName());
        user.setLastName(userInputDTO.getLastName());
        user.setEmail(userInputDTO.getEmail());
        return user;
    }

    // Transforms the User to a DTO
    private UserOutputDTO setUserToDTO(User user) {
        UserOutputDTO userOutputDTO = new UserOutputDTO();
        userOutputDTO.setId(user.getId());
        userOutputDTO.setUsername(user.getUsername());
        userOutputDTO.setFirstName(user.getFirstName());
        userOutputDTO.setLastName(user.getLastName());
        userOutputDTO.setEmail(user.getEmail());
        return userOutputDTO;
    }

    private User fetchUserByID(Long userID) {
        // Fetches the user by id and throws an exception if it doesn't exist
        return userRepository.findById(userID).orElseThrow(
                () -> new RecordNotFoundException("No user found with id: " + userID)
        );
    }

    public UserOutputDTO registerUser(UserInputDTO userDTO) {
        User user = setDTOtoUser(userDTO);

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

    public UserOutputDTO findUserById(Long userID) {
        // uses private method to fetch and validate the user exists
        return setUserToDTO(fetchUserByID(userID));
    }

    public UserOutputDTO updateUserByID(Long userID, UserInputDTO userInputDTO) {
        // uses private method to fetch and validate the user exists
        User user = fetchUserByID(userID);
        user.setUsername(userInputDTO.getUsername());
        user.setFirstName(userInputDTO.getFirstName());
        user.setLastName(userInputDTO.getLastName());
        user.setEmail(userInputDTO.getEmail());
        userRepository.save(user);
        return setUserToDTO(user);
    }

    public String deleteUserByID(Long userID) {
        // uses private method to fetch and validate the user exists
        userRepository.delete(fetchUserByID(userID));
        return "User: " + userID + " has been successfully deleted.";
    }

}


