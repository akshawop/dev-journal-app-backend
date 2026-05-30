package me.akshawop.devjournalapp.shared.util.queue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailJob {

    private String eventVersion;

    private String to;
    private String subject;
    private String body;
}