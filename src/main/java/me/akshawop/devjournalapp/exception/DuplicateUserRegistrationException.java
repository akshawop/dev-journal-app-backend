package me.akshawop.devjournalapp.exception;

public class DuplicateUserRegistrationException extends RuntimeException {
    public DuplicateUserRegistrationException(String message) {
        super(message);
    }
}
