package com.baranskagata.optometry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkingPlanNotFoundException extends RuntimeException{
    public WorkingPlanNotFoundException(String message) {
        super(message);
    }
}
