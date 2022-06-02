package com.baranskagata.optometry.exception;

public class WorkWithNameAlreadyExistsException extends RuntimeException{
    public WorkWithNameAlreadyExistsException(String message) {
        super(message);
    }
}
