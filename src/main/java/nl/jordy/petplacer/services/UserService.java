package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.dtos.output.UserOutputDTO;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.ModelMapperHelper;
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

    public User fetchUserByID(Long userID) {
        // Fetches the user by id and throws an exception if it doesn't exist
        return userRepository.findById(userID).orElseThrow(
                () -> new RecordNotFoundException("No user found with id: " + userID)
        );
    }

    public UserOutputDTO registerUser(UserInputDTO userDTO) {
        User user = ModelMapperHelper.getModelMapper().map(userDTO, User.class);

        //Saves the user to the database
        userRepository.save(user);

        //Transforms the user to a DTO and returns it
        return ModelMapperHelper.getModelMapper().map(user, UserOutputDTO.class);
    }

    public List<UserOutputDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> ModelMapperHelper.getModelMapper().map(user, UserOutputDTO.class))
                .toList();
    }

    public UserOutputDTO findUserById(Long userID) {
        // uses private method to fetch and validate the user exists
        return ModelMapperHelper.getModelMapper().map(fetchUserByID(userID), UserOutputDTO.class);
    }

    public UserOutputDTO updateUserByID(Long userID, UserInputDTO userInputDTO) {
        // uses private method to fetch and validate the user exists
        User user = fetchUserByID(userID);

        ModelMapperHelper.getModelMapper().map(userInputDTO, user);

        userRepository.save(user);
        return ModelMapperHelper.getModelMapper().map(user, UserOutputDTO.class);
    }

    public String deleteUserByID(Long userID) {
        // uses private method to fetch and validate the user exists
        userRepository.delete(fetchUserByID(userID));
        return "User: " + userID + " has been successfully deleted.";
    }

}


