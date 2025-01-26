package it.fulminazzo.chatapp.backend.security;

import it.fulminazzo.chatapp.backend.domain.entities.User;
import it.fulminazzo.chatapp.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class ChatUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    //TODO: temporary
    public ChatUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        userRepository.findByUsername("fulminazzo").orElseGet(() -> userRepository.save(User.builder()
                .username("fulminazzo")
                .password("password")
                .build()
        ));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(ChatUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
