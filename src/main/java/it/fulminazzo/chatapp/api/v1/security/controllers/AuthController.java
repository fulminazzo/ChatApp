package it.fulminazzo.chatapp.api.v1.security.controllers;

import it.fulminazzo.chatapp.api.v1.security.objects.LoginRequest;
import it.fulminazzo.chatapp.api.v1.security.services.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = authenticationService.authenticate(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        return new ResponseEntity<>(authenticationService.generateToken(userDetails), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = authenticationService.register(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        return new ResponseEntity<>(authenticationService.generateToken(userDetails), HttpStatus.OK);
    }

}
