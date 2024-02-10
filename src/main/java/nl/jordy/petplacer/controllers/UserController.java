package nl.jordy.petplacer.controllers;


import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.UserDto;
import nl.jordy.petplacer.exceptions.BadRequestException;
import nl.jordy.petplacer.helpers.BuildUri;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
        public ResponseEntity<String> registerUser(
                @Valid
                @RequestBody UserDto userDto,
                BindingResult bindingResult
    ) {
        //catches errors and sends them back to the user
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult);
        }

        //hands it off for saving and returns the created user
        User user = userService.registerUser(userDto);




        return ResponseEntity.created(BuildUri.buildUri(user)).body("User created");
    }

}
