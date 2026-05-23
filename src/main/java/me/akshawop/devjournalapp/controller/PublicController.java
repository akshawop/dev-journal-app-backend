package me.akshawop.devjournalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.akshawop.devjournalapp.repository.UserRepo;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserRepo repo;

    @GetMapping("/health-check")
    public ResponseEntity<HttpStatus> healthCheck() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
