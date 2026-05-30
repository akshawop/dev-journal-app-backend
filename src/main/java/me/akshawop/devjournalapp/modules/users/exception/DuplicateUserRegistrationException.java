package me.akshawop.devjournalapp.modules.users.exception;

public class DuplicateUserRegistrationException extends RuntimeException {
    public DuplicateUserRegistrationException(String message) {
        super(message);
    }
}
