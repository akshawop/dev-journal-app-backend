package me.akshawop.devjournalapp.model;

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
import me.akshawop.devjournalapp.entity.User;
import me.akshawop.devjournalapp.util.RegexPatterns;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
        private UUID id;
        private String username;

        @NotBlank(message = "Email is required", groups = { OnSignup.class, OnLogin.class, OnOtpValidate.class })
        @Size(max = 100, message = "Email should not exceed 100 characters", groups = { OnSignup.class, OnLogin.class,
                        OnOtpValidate.class })
        @Email(regexp = RegexPatterns.EMAIL, message = "Invalid email format", groups = { OnSignup.class, OnLogin.class,
                        OnOtpValidate.class })
        String email;

        @Pattern(regexp = RegexPatterns.PASSWORD, message = "Password must be atleast 8 characters long and contain atleast 1 capital letter, 1 small letter, 1 digit and 1 special character", groups = {
                        OnSignup.class, OnLogin.class })
        String password;

        private Instant joiningDate;
        private List<String> roles;

        @NotBlank(message = "No verification code provided", groups = OnOtpValidate.class)
        @Length(min = 6, max = 6, message = "Code should be of 6 digits", groups = OnOtpValidate.class)
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
