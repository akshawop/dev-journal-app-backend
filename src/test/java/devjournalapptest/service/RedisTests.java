package devjournalapptest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.akshawop.devjournalapp.Application;
import me.akshawop.devjournalapp.entity.User;
import me.akshawop.devjournalapp.service.RedisService;

@SpringBootTest(classes = Application.class)
public class RedisTests {
    @Autowired
    private RedisService redis;

    @Test
    void testRedis() {
        User user = User.builder().email("xyz").username("abc").password("adfs").build();
        redis.set("name", user, 30l);
        User newUser = redis.get("name", User.class);
        assertEquals(user, newUser);
        assertEquals(user.getUsername(), newUser.getUsername());
    }
}