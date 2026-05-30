package me.akshawop.devjournalapp.modules.users.exception;

public class OTPValidationFailedException extends RuntimeException {
    public OTPValidationFailedException(String message) {
        super(message);
    }
}
