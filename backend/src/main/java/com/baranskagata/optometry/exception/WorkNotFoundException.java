package com.baranskagata.optometry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class WorkNotFoundException extends RuntimeException{
    public WorkNotFoundException(String message) {
        super(message);
    }
}
