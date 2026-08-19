package com.gaurav.servicehub.servicehub.service.exception;

public class ServiceValidationException extends RuntimeException {

    public ServiceValidationException(String message) {
        super(message);
    }
}