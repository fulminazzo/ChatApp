package it.fulminazzo.chatapp.api.v1.security.services;

import it.fulminazzo.chatapp.api.v1.security.exceptions.InvalidJwtException;
import it.fulminazzo.chatapp.api.v1.security.objects.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthenticationService {

    UserDetails authenticate(String username, String password);

    UserDetails register(String username, String password);

    UserDetails validateToken(String token) throws InvalidJwtException;

    AuthResponse generateToken(UserDetails userDetails);

}
