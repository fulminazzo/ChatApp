package it.fulminazzo.chatapp.api.v1.exceptions;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public final class UserNotFoundException extends HttpException {

    public UserNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "Could not find user with id " + id);
    }

    public UserNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, "Could not find user with username: " + username);
    }
}
