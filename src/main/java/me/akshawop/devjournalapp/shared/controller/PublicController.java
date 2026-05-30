package me.akshawop.devjournalapp.shared.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/health-check")
    public ResponseEntity<HttpStatus> healthCheck() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
