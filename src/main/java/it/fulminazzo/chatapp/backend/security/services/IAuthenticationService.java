package it.fulminazzo.chatapp.backend.security.services;

import it.fulminazzo.chatapp.backend.security.exceptions.InvalidJwtException;
import it.fulminazzo.chatapp.backend.security.objects.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthenticationService {

    UserDetails authenticate(String username, String password);

    UserDetails register(String username, String password);

    UserDetails validateToken(String token) throws InvalidJwtException;

    AuthResponse generateToken(UserDetails userDetails);

}
