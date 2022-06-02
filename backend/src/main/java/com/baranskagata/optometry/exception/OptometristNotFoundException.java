package com.baranskagata.optometry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OptometristNotFoundException extends RuntimeException{
    public OptometristNotFoundException(String message) {
        super(message);
    }
}
