package nl.jordy.petplacer.controllers;

import nl.jordy.petplacer.exceptions.BadLoginException;
import nl.jordy.petplacer.security.LoginRequest;
import nl.jordy.petplacer.security.LoginResponse;
import nl.jordy.petplacer.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws BadLoginException {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadLoginException exception) {
            throw new BadLoginException();
        };

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        final String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}
