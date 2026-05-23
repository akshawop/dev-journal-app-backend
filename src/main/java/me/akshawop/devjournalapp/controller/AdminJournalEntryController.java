package me.akshawop.devjournalapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.akshawop.devjournalapp.entity.JournalEntry;
import me.akshawop.devjournalapp.exception.JournalNotFoundException;
import me.akshawop.devjournalapp.service.JournalEntryService;
import me.akshawop.devjournalapp.util.OtherUtils;

@RestController
@RequestMapping("/admin/journal")
public class AdminJournalEntryController {

    @Autowired
    private JournalEntryService journalService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries() {

        List<JournalEntry> entries = journalService.getAllEntries();
        if (entries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String id) {

        Integer journalId = OtherUtils.tryParseInt(id).orElseThrow(() -> new JournalNotFoundException(id));
        JournalEntry entry = journalService.getEntryByIdAdmin(journalId);
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<HttpStatus> deleteJournalById(@PathVariable String id) {

        Integer journalId = OtherUtils.tryParseInt(id).orElseThrow(() -> new JournalNotFoundException(id));
        journalService.deleteEntryByIdAdmin(journalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
