package it.fulminazzo.chatapp.api.v1.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class HttpException extends RuntimeException {
    private final HttpStatus httpStatus;

}
