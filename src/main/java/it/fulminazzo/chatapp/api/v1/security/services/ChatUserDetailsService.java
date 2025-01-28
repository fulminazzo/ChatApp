package it.fulminazzo.chatapp.api.v1.security.services;

import it.fulminazzo.chatapp.api.v1.domain.entities.User;
import it.fulminazzo.chatapp.api.v1.repositories.UserRepository;
import it.fulminazzo.chatapp.api.v1.security.exceptions.UserAlreadyRegisteredException;
import it.fulminazzo.chatapp.api.v1.security.objects.ChatUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChatUserDetailsService implements IChatUserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails createUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent())
            throw new UserAlreadyRegisteredException(username);
        else return new ChatUserDetails(userRepository.save(User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
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
