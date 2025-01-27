package it.fulminazzo.chatapp.backend.security.objects;

import it.fulminazzo.chatapp.structures.Tuple;

public record AuthResponse(String token, long expirationInSeconds) {

    public static AuthResponse fromTuple(Tuple<String, Long> tuple) {
        return new AuthResponse(tuple.first(), tuple.second());
    }

}
