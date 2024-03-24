package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.dtos.output.UserOutputDTO;
import nl.jordy.petplacer.exceptions.AlreadyExistsException;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.ModelMapperHelper;
import nl.jordy.petplacer.models.Authority;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.UserRepository;
import nl.jordy.petplacer.util.AccessValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //Injects dependencies
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public User fetchUserByID(String username) {

        // Returns a 401 if the user is not the requested user or an admin
        AccessValidator.isUserOrAdmin(AccessValidator.getAuth(), username);

        // Fetches the user by id and throws an exception if it doesn't exist
        return userRepository.findByUsername(username).orElseThrow(
                () -> new RecordNotFoundException("No user found with id: " + username)
        );
    }

    public void validateUserUnique(UserInputDTO userDTO) {

        // Checks if the username is unique and email are unique
        if (userRepository.existsByUsernameIgnoreCase(userDTO.getUsername())) {
            throw new AlreadyExistsException("Username: " + userDTO.getUsername() + " already exists");
        } else if (userRepository.existsByEmailIgnoreCase(userDTO.getEmail())) {
            throw new AlreadyExistsException("Email: " + userDTO.getEmail() + " already exists");
        }
    }

    public UserOutputDTO registerUser(UserInputDTO userDTO) {

        validateUserUnique(userDTO);

        User user = ModelMapperHelper.getModelMapper().map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.addAuthority(new Authority(user.getUsername(), "ROLE_USER"));

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

    public UserOutputDTO findUserById(String username) {
        // uses private method to fetch and validate the user exists
        return ModelMapperHelper.getModelMapper().map(fetchUserByID(username), UserOutputDTO.class);
    }

    public UserOutputDTO updateUserByID(String username, UserInputDTO userInputDTO) {
        // uses private method to fetch and validate the user exists
        User user = fetchUserByID(username);

        ModelMapperHelper.getModelMapper().map(userInputDTO, user);

        userRepository.save(user);
        return ModelMapperHelper.getModelMapper().map(user, UserOutputDTO.class);
    }

    public String deleteUserByID(String username) {
        // uses private method to fetch and validate the user exists
        userRepository.delete(fetchUserByID(username));
        return "User: " + username + " has been successfully deleted.";
    }

}


