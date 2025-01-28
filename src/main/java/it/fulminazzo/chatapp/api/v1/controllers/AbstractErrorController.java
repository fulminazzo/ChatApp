package it.fulminazzo.chatapp.api.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractErrorController {

    protected ResponseEntity<?> apiError(HttpStatus statusCode, String message) {
        return new ResponseEntity<>(new ApiError(statusCode.value(), message), statusCode);
    }

    private record ApiError(int code, String message) {}

}
