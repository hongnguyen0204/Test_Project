package com.example.test_project.Utils.HandleException.Exceptions;

public class EmailNotExistException extends RuntimeException {
    /**
     * Email does not existed.
     */
    public EmailNotExistException() {
        super("Email does not exist.");
    }
}
