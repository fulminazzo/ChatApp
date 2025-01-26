package it.fulminazzo.chatapp.backend.security.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthenticationService {

    UserDetails authenticate(String username, String password);

}
