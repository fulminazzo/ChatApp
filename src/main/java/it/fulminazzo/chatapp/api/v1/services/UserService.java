package it.fulminazzo.chatapp.api.v1.services;

import it.fulminazzo.chatapp.api.v1.domain.entities.User;
import it.fulminazzo.chatapp.api.v1.exceptions.UserNotFoundException;
import it.fulminazzo.chatapp.api.v1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User findUserByIdOrThrow(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findUserByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

}
