package it.fulminazzo.chatapp.api.v1.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IChatUserDetailsService extends UserDetailsService {

    UserDetails createUser(String username, String password);

}
