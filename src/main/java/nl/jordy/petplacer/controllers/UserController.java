package nl.jordy.petplacer.controllers;


import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.UserDto;
import nl.jordy.petplacer.exceptions.BadRequestException;
import nl.jordy.petplacer.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

        return ResponseEntity.ok(userService.registerUser(userDto));
    }

}
