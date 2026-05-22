package me.akshawop.journalApp.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.akshawop.journalApp.entity.JournalEntry;
import me.akshawop.journalApp.entity.UserDetailsImpl;
import me.akshawop.journalApp.exception.JournalNotFoundException;
import me.akshawop.journalApp.model.JournalEntryDTO;
import me.akshawop.journalApp.service.JournalEntryService;
import me.akshawop.journalApp.util.OtherUtils;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalService;

    @GetMapping
    public ResponseEntity<List<JournalEntryDTO>> getAllJournalEntriesOfUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = ((UserDetailsImpl) auth.getPrincipal()).getUser().getId();

        List<JournalEntry> entries = journalService.getAllEntriesForUser(userId);
        if (entries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<JournalEntryDTO> entriesData = entries.stream()
                    .map(entry -> JournalEntryDTO.journalEntryDTOBuilder(entry)).toList();

            return new ResponseEntity<>(entriesData, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntryDTO> getJournalEntryById(@PathVariable String id) {

        Integer journalId = OtherUtils.tryParseInt(id).orElseThrow(() -> new JournalNotFoundException(id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = ((UserDetailsImpl) auth.getPrincipal()).getUser().getId();

        JournalEntry journalEntry = journalService.getEntryById(journalId, userId);
        JournalEntryDTO journalEntryData = JournalEntryDTO.journalEntryDTOBuilder(journalEntry);

        return new ResponseEntity<>(journalEntryData, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntryDTO> createEntry(
            @Validated(JournalEntryDTO.OnCreate.class) @RequestBody JournalEntryDTO entry) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = ((UserDetailsImpl) auth.getPrincipal()).getUser().getId();

        JournalEntry journalEntry = journalService.saveEntry(entry, userId);
        JournalEntryDTO journalEntryData = JournalEntryDTO.journalEntryDTOBuilder(journalEntry);

        return new ResponseEntity<>(journalEntryData, HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntryDTO> updateJournalById(@PathVariable String id,
            @RequestBody JournalEntryDTO entry) {

        Integer journalId = OtherUtils.tryParseInt(id).orElseThrow(() -> new JournalNotFoundException(id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = ((UserDetailsImpl) auth.getPrincipal()).getUser().getId();

        JournalEntry journalEntry = journalService.updateEntry(journalId, entry, userId);
        JournalEntryDTO journalEntryData = JournalEntryDTO.journalEntryDTOBuilder(journalEntry);

        return new ResponseEntity<>(journalEntryData, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<HttpStatus> deleteJournalById(@PathVariable String id) {

        Integer journalId = OtherUtils.tryParseInt(id).orElseThrow(() -> new JournalNotFoundException(id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = ((UserDetailsImpl) auth.getPrincipal()).getUser().getId();

        journalService.deleteEntryById(journalId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
