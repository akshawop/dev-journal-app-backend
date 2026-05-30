package me.akshawop.devjournalapp.modules.journalentries.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.akshawop.devjournalapp.modules.journalentries.repository.entity.JournalEntry;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JournalEntryDTO {

        private Integer id;

        @NotBlank(message = "Title cannot be empty", groups = { OnCreate.class })
        @Size(max = 200, message = "Title should not exceed 200 characters")
        String title;

        @NotBlank(message = "Content cannot be empty")
        @Size(max = 10000, message = "Content cannot exceed 10000 characters")
        String content;

        private Instant createdAt;
        private Instant updatedAt;

        public static interface OnCreate {
        }

        public static JournalEntryDTO journalEntryDTOBuilder(JournalEntry entry) {
                return JournalEntryDTO.builder()
                                .id(entry.getId())
                                .title(entry.getTitle())
                                .content(entry.getContent())
                                .createdAt(entry.getCreatedAt())
                                .updatedAt(entry.getUpdatedAt())
                                .build();
        }
}
