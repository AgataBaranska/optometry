package com.baranskagata.optometry.exception;

public class AppointmentAvailabilityChangedException extends RuntimeException{
    public AppointmentAvailabilityChangedException(String message) {
        super(message);
    }
}
