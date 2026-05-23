package me.akshawop.devjournalapp.exception;

public class OTPValidationFailedException extends RuntimeException {
    public OTPValidationFailedException(String message) {
        super(message);
    }
}
