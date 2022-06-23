package com.example.exceptions;

public class ProfileNotFoundException extends Exception {

    public ProfileNotFoundException() {
        super();
    }

    public ProfileNotFoundException(String message) {
        super(message);
    }
}
