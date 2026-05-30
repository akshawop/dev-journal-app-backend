package me.akshawop.devjournalapp.modules.users.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.akshawop.devjournalapp.modules.users.repository.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    void deleteByUsername(String username);
}
