package me.akshawop.devjournalapp.modules.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.akshawop.devjournalapp.modules.users.model.OTP;
import me.akshawop.devjournalapp.modules.users.model.UserDTO;
import me.akshawop.devjournalapp.modules.users.util.SecretGenerator;
import me.akshawop.devjournalapp.modules.users.util.otp.OTPGenerator;
import me.akshawop.devjournalapp.modules.users.util.otp.OTPHasher;
import me.akshawop.devjournalapp.shared.service.RedisService;

@Service
public class OTPService {

    @Autowired
    private RedisService redis;

    @Autowired
    private OTPGenerator otpGenerator;

    private static String secret = SecretGenerator.generateBase64Secret(32);

    public String getOTP(UserDTO user) {
        try {

            String otp = otpGenerator.generateOtp();
            OTP otpDTO = new OTP(OTPHasher.hmacSha256(otp, secret), "K0");
            user.setOtp(otpDTO);

            redis.set("OTP:" + user.getEmail(), user, 300l);
            return otp;

        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in OTP service", e);
        }
    }

    public boolean validate(String email, String otp) {
        try {

            UserDTO user = redis.get("OTP:" + email, UserDTO.class);
            if (user == null)
                return false;

            String otpHash = OTPHasher.hmacSha256(otp, secret);
            return OTPHasher.secureCompare(otpHash, user.getOtp().otpHash());

        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in OTP service", e);
        }
    }
}
