package me.akshawop.devjournalapp.modules.users.infrastruture.email;

import lombok.NonNull;
import me.akshawop.devjournalapp.modules.users.repository.entity.User;
import me.akshawop.devjournalapp.shared.util.queue.dto.EmailJob;

public class EmailJobs {
    public static EmailJob getOTPVerificationMail(@NonNull String to, @NonNull String otp) {
        String subject = "Verification Code to register to Journal App";
        String body = String
                .format("Your OTP to verify your Email account is %s. Please do not share the OTP to anyone.", otp);
        return new EmailJob("v1", to, subject, body);
    }

    public static EmailJob getSignupSuccessMail(@NonNull User user) {

        String subject = "Journal App account signup Successful";
        String body = String.format("Thank you for signing up in our application :)\nYour username is %s\n\nEnjoy!",
                user.getUsername());
        return new EmailJob("v1", user.getEmail(), subject, body);

    }
}
