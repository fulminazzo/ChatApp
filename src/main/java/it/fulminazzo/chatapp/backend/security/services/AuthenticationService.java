package it.fulminazzo.chatapp.backend.security.services;

import it.fulminazzo.chatapp.backend.security.exceptions.InvalidJwtException;
import it.fulminazzo.chatapp.backend.security.objects.AuthResponse;
import it.fulminazzo.chatapp.backend.security.utils.JwtProvider;
import it.fulminazzo.chatapp.structures.Tuple;
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

    private final JwtProvider jwtProvider;

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

    @Override
    public UserDetails validateToken(String token) throws InvalidJwtException {
        String username = jwtProvider.extractUsernameFromJwtToken(token);
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public AuthResponse generateToken(UserDetails userDetails) {
        Tuple<String, Long> tokenData = jwtProvider.generateJwtTokenFromUsername(userDetails.getUsername());
        return AuthResponse.fromTuple(tokenData);
    }

}
