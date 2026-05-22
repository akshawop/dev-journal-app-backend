package me.akshawop.journalApp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import me.akshawop.journalApp.entity.JournalEntry;
import me.akshawop.journalApp.exception.AccessDeniedException;
import me.akshawop.journalApp.exception.JournalNotFoundException;
import me.akshawop.journalApp.model.JournalEntryDTO;
import me.akshawop.journalApp.repository.JournalEntryRepo;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalRepo;

    @SuppressWarnings("null")
    public JournalEntry saveEntry(@NonNull JournalEntryDTO newEntry, @NonNull UUID userId) {

        JournalEntry entry = JournalEntry.builder()
                .title(newEntry.getTitle())
                .content(newEntry.getContent())
                .userId(userId)
                .build();

        return journalRepo.save(entry);
    }

    public JournalEntry getEntryById(Integer journalId, UUID userId) {

        // check if the journal exists
        @SuppressWarnings("null")
        JournalEntry journalEntry = journalRepo.findById(journalId)
                .orElseThrow(() -> new JournalNotFoundException(journalId.toString()));

        // checks if the entry belongs to the user
        if (!journalEntry.getUserId().equals(userId))
            throw new AccessDeniedException("You are NOT the owner of this journal");

        return journalEntry;
    }

    public JournalEntry getEntryByIdAdmin(@NonNull Integer id) {
        return (journalRepo.findById(id)).orElseThrow(() -> new JournalNotFoundException(id.toString()));
    }

    public List<JournalEntry> getAllEntriesForUser(@NonNull UUID userId) {
        return journalRepo.findAllByUserId(userId);
    }

    public List<JournalEntry> getAllEntries() {
        return journalRepo.findAll();
    }

    public JournalEntry updateEntry(@NonNull Integer journalId, @NonNull JournalEntryDTO newEntryData,
            @NonNull UUID userId) {

        // check if the journal exists
        JournalEntry oldEntry = journalRepo.findById(journalId)
                .orElseThrow(() -> new JournalNotFoundException(journalId.toString()));

        // checks if the entry belongs to the user
        if (!oldEntry.getUserId().equals(userId))
            throw new AccessDeniedException("You are NOT authorized to update this journal");

        if (newEntryData.getTitle() != null)
            oldEntry.setTitle(newEntryData.getTitle());
        oldEntry.setContent(newEntryData.getContent());

        return journalRepo.save(oldEntry);
    }

    @Transactional
    public void deleteEntryById(@NonNull Integer journalId, @NonNull UUID userId) {
        // check if the journal exists
        JournalEntry entry = journalRepo.findById(journalId)
                .orElseThrow(() -> new JournalNotFoundException(journalId.toString()));

        // checks if the entry belongs to the user
        if (!entry.getUserId().equals(userId))
            throw new AccessDeniedException("You are NOT authorized to delete this journal");

        journalRepo.deleteById(journalId);
    }

    @Transactional
    public void deleteEntryByIdAdmin(@NonNull Integer journalId) {

        // check if the journal exists
        journalRepo.findById(journalId)
                .orElseThrow(() -> new JournalNotFoundException(journalId.toString()));

        journalRepo.deleteById(journalId);
    }

    @KafkaListener(topics = "user.account.deleted", containerFactory = "manualImmediateFactory", groupId = "user-deletion-journal-cleanup-group")
    @Transactional
    public void deleteEntryByUserId(UUID userId, Acknowledgment ack) {
        journalRepo.deleteAllByUserId(userId);

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        ack.acknowledge();
                    }
                });
    }
}
