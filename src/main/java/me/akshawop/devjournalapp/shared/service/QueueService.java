package me.akshawop.devjournalapp.shared.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sonus21.rqueue.core.RqueueMessageEnqueuer;

import lombok.RequiredArgsConstructor;
import me.akshawop.devjournalapp.shared.util.queue.QueueConstants;
import me.akshawop.devjournalapp.shared.util.queue.dto.EmailJob;

@Service
@RequiredArgsConstructor
public class QueueService {

    @Autowired
    private RqueueMessageEnqueuer enqueuer;

    public void publishEmail(EmailJob job) {

        enqueuer.enqueue(
                QueueConstants.EMAIL_QUEUE,
                job);
    }

    public void publishDeleteUserData(UUID userId) {

        enqueuer.enqueue(
                QueueConstants.USER_DATA_CLEANUP_QUEUE,
                userId);
    }

}
