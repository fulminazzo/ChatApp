package it.fulminazzo.chatapp.backend.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IChatUserDetailsService extends UserDetailsService {

    UserDetails createUser(String username, String password);

}
