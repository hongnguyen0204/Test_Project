package com.example.test_project.Utils.HandleException.Exceptions;

public class EmailExistedException extends RuntimeException {
    /**
     * Email is not existed.
     */
    public EmailExistedException() {
        super("Email already exists.");
    }
}
