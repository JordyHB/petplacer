package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.dtos.output.UserOutputDTO;
import nl.jordy.petplacer.dtos.patch.UserPatchDTO;
import nl.jordy.petplacer.exceptions.AlreadyExistsException;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.interfaces.AuthorityChecker;
import nl.jordy.petplacer.models.Authority;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.UserRepository;
import nl.jordy.petplacer.specifications.UserSpecification;
import nl.jordy.petplacer.util.AccessValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessValidator accessValidator;
    private final AuthorityChecker authChecker;

    //Injects dependencies
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AccessValidator accessValidator,
                       AuthorityChecker authChecker
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accessValidator = accessValidator;
        this.authChecker = authChecker;
    }

    public User fetchUserByUsername(String username) {
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
        // encodes the password and sets it separately to keep it case-sensitive
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

    public UserOutputDTO findUserByUsername(String username) {
        // Returns a 401 if the user is not the requested user or an admin
        accessValidator.isUserOrAdmin(accessValidator.getAuth(), username);
        // uses private method to fetch and validate the user exists
        return ModelMapperHelper.getModelMapper().map(fetchUserByUsername(username), UserOutputDTO.class);
    }

    public List<UserOutputDTO> findUsersByParams(
            String username,
            String firstName,
            String lastName,
            List<String> roles
    ) {
        return userRepository.findAll(new UserSpecification(username, firstName, lastName, roles)).stream()
                .map(user -> ModelMapperHelper.getModelMapper().map(user, UserOutputDTO.class))
                .toList();
    }

    public UserOutputDTO updateUserByUsername(String username, UserPatchDTO userPatchDTO) {

        // Returns a 401 if the user is not the requested user or an admin
        accessValidator.isUserOrAdmin(accessValidator.getAuth(), username);

        User user = fetchUserByUsername(username);

        ModelMapperHelper.getModelMapper().map(userPatchDTO, user);
        user.setUpdatedAt(new Date());

        userRepository.save(user);
        return ModelMapperHelper.getModelMapper().map(user, UserOutputDTO.class);
    }

    public String deleteUserByUsername(String username) {

        // Returns a 401 if the user is not the requested user or an admin
        accessValidator.isUserOrAdmin(accessValidator.getAuth(), username);

        userRepository.delete(fetchUserByUsername(username));
        return "User: " + username + " has been successfully deleted.";
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public UserOutputDTO promoteToAdmin(String username) {

        User user = fetchUserByUsername(username);

        if (authChecker.fetchedUserHasAuthority(user, "ROLE_ADMIN")) {
            throw new AlreadyExistsException("User: " + username + " is already an admin");
        }

        user.addAuthority(new Authority(user.getUsername(), "ROLE_ADMIN"));
        userRepository.save(user);

        return ModelMapperHelper.getModelMapper().map(user, UserOutputDTO.class);
    }

    public UserOutputDTO demoteAdmin(String username) {
        // uses private method to fetch and validate the user exists
        User user = fetchUserByUsername(username);

        if (!authChecker.fetchedUserHasAuthority(user, "ROLE_ADMIN")) {
            throw new AlreadyExistsException("User: " + username + " is not an admin");
        }

        user.removeAuthority(user.getAuthorities().stream()
                .filter(a -> a.getAuthority().equals("ROLE_ADMIN"))
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException("User: " + username + " is not an admin"))
        );

        userRepository.save(user);

        return ModelMapperHelper.getModelMapper().map(user, UserOutputDTO.class);
    }
}


