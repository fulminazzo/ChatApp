package it.fulminazzo.chatapp.api.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractErrorController {

    protected ResponseEntity<?> apiError(HttpStatus statusCode, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", statusCode.value());
        if (!message.isEmpty()) map.put("message", message);
        return new ResponseEntity<>(map, statusCode);
    }

}
