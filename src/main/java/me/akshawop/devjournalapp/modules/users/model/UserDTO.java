package me.akshawop.devjournalapp.modules.users.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.akshawop.devjournalapp.modules.users.repository.entity.User;
import me.akshawop.devjournalapp.modules.users.util.RegexPatterns;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
        private UUID id;
        private String username;

        @NotBlank(message = "Email is required", groups = { OnSignup.class, OnLogin.class, OnOtpValidate.class })
        @Size(max = 100, message = "Email too long", groups = { OnSignup.class, OnLogin.class,
                        OnOtpValidate.class })
        @Email(regexp = RegexPatterns.EMAIL, message = "Invalid email format", groups = { OnSignup.class, OnLogin.class,
                        OnOtpValidate.class })
        String email;

        @Pattern(regexp = RegexPatterns.PASSWORD, message = "Invalid password format", groups = {
                        OnSignup.class, OnLogin.class })
        @Size(max = 100, message = "Password too long", groups = {
                        OnSignup.class, OnLogin.class })
        String password;

        private Instant joiningDate;
        private List<String> roles;

        @NotBlank(message = "No verification code provided", groups = OnOtpValidate.class)
        @Length(min = 6, max = 6, message = "Invalid code entered", groups = OnOtpValidate.class)
        String code;

        OTP otp;

        public static interface OnSignup {
        }

        public static interface OnOtpValidate {
        }

        public static interface OnLogin {
        }

        public static UserDTO userDTOBuilder(User user) {
                return UserDTO.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .joiningDate(user.getJoiningDate())
                                .roles(user.getRoles())
                                .build();
        }
}
