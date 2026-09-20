package com.hospital.exception;

/**
 * Custom exception thrown when login credentials are invalid.
 */
public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}