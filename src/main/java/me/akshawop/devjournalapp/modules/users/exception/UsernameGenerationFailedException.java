package me.akshawop.devjournalapp.modules.users.exception;

public class UsernameGenerationFailedException extends RuntimeException {

    public UsernameGenerationFailedException(String message) {
        super(message);
    }

}
