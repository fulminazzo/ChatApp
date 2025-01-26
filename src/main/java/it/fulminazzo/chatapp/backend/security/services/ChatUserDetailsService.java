package it.fulminazzo.chatapp.backend.security.services;

import it.fulminazzo.chatapp.backend.domain.entities.User;
import it.fulminazzo.chatapp.backend.repositories.UserRepository;
import it.fulminazzo.chatapp.backend.security.objects.ChatUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
class ChatUserDetailsService implements IChatUserDetailsService {
    private final UserRepository userRepository;

    //TODO: temporary
    public ChatUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        userRepository.findByUsername("fulminazzo").orElseGet(() -> userRepository.save(User.builder()
                .username("fulminazzo")
                .password(passwordEncoder.encode("password"))
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
