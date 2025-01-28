package it.fulminazzo.chatapp.api.v1.security.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException(String username) {
        super(String.format("An user with username '%s' is already registered", username));
    }

}
