package nl.jordy.petplacer.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.UserRepository;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/register")
        public ResponseEntity<User> registerUser(@RequestBody User user) {


            // Save the user to the database
            User savedUser = userRepository.save(user);

            return ResponseEntity.ok(savedUser);
        }

}
