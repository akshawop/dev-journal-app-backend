package me.akshawop.devjournalapp.modules.journalentries.exception;

import me.akshawop.devjournalapp.shared.exception.GenericNotFoundException;

public class JournalNotFoundException extends GenericNotFoundException {

    public JournalNotFoundException(String journalId) {
        super("Journal with id " + journalId + " not found");
    }

}
