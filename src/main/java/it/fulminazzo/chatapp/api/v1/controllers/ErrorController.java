package it.fulminazzo.chatapp.api.v1.controllers;

import it.fulminazzo.chatapp.api.v1.exceptions.HttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ErrorController extends AbstractErrorController {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<?> handleException(HttpException ex) {
        return new ResponseEntity<>(ex.getHttpStatus());
    }

}
