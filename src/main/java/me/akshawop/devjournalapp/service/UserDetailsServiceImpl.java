package me.akshawop.devjournalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import me.akshawop.devjournalapp.entity.User;
import me.akshawop.devjournalapp.entity.UserDetailsImpl;
import me.akshawop.devjournalapp.repository.UserRepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private RedisService redis;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = redis.get("user:" + username, User.class);
        if (user == null) {
            // get the user from db if not found in redis
            user = repo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("No user found with the username: " + username));
            redis.set("user:" + username, user, 1800l);
        }

        // give spring security the references to where each required data points are
        // present
        return new UserDetailsImpl(user);
    }
}
