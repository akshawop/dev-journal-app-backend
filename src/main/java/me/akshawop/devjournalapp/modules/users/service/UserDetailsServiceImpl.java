package me.akshawop.devjournalapp.modules.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import me.akshawop.devjournalapp.modules.users.repository.UserRepo;
import me.akshawop.devjournalapp.modules.users.repository.entity.User;
import me.akshawop.devjournalapp.shared.service.RedisService;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RedisService redis;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = redis.get("user:" + username, User.class);
        if (user == null) {
            // get the user from db if not found in redis
            user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("No user found with the username: " + username));
            redis.set("user:" + username, user, 1800l);
        }

        // give spring security the references to where each required data points are
        // present
        return new UserDetailsImpl(user);
    }
}
