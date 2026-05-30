package me.akshawop.devjournalapp.shared.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.github.sonus21.rqueue.annotation.RqueueListener;

import lombok.NonNull;
import me.akshawop.devjournalapp.shared.util.queue.QueueConstants;
import me.akshawop.devjournalapp.shared.util.queue.dto.EmailJob;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(@NonNull String to, @NonNull String subject, @NonNull String body) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);

            javaMailSender.send(mail);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in Email service", e);
        }
    }

    public void sendEmail(@NonNull EmailJob job) {
        sendEmail(job.getTo(), job.getSubject(), job.getBody());
    }

    @RqueueListener(value = QueueConstants.EMAIL_QUEUE, numRetries = "3")
    public void emailQueueConsumer(EmailJob job) {
        sendEmail(job.getTo(), job.getSubject(), job.getBody());
    }

}
