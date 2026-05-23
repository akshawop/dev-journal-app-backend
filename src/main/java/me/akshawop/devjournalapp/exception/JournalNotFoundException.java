package me.akshawop.devjournalapp.exception;

public class JournalNotFoundException extends GenericNotFoundException {

    public JournalNotFoundException(String journalId) {
        super("Journal with id " + journalId + " not found");
    }

}
