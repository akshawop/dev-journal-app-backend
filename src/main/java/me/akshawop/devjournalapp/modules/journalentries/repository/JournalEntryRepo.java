package me.akshawop.devjournalapp.modules.journalentries.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.akshawop.devjournalapp.modules.journalentries.repository.entity.JournalEntry;

@Repository
public interface JournalEntryRepo extends JpaRepository<JournalEntry, Integer> {
    List<JournalEntry> findAllByUserId(UUID userId);

    void deleteByUserId(UUID userId);

    void deleteAllByUserId(UUID userId);
}
