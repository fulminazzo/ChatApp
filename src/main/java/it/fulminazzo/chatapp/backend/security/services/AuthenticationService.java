package it.fulminazzo.chatapp.backend.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final IChatUserDetailsService userDetailsService;

    @Override
    public UserDetails authenticate(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public UserDetails register(String username, String password) {
        return userDetailsService.createUser(username, password);
    }

}
