package it.fulminazzo.chatapp.api.v1.security.controllers;

import it.fulminazzo.chatapp.api.v1.controllers.AbstractErrorController;
import it.fulminazzo.chatapp.api.v1.security.exceptions.UserAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class SecurityErrorController extends AbstractErrorController {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<?> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
        return apiError(HttpStatus.CONFLICT, ex.getMessage());
    }

}
