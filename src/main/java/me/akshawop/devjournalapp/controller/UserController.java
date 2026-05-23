package me.akshawop.devjournalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import me.akshawop.devjournalapp.entity.User;
import me.akshawop.devjournalapp.entity.UserDetailsImpl;
import me.akshawop.devjournalapp.model.UserDTO;
import me.akshawop.devjournalapp.service.UserService;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUserDetails() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userService.getUserByUsername(username);
        UserDTO userData = UserDTO.userDTOBuilder(user);

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @PatchMapping("/change-username")
    public ResponseEntity<UserDTO> changeUsername(
            @NotEmpty(message = "username value is required in query parameter") @Size(min = 4, max = 20, message = "username must be between 4 to 20 characters") @RequestParam String username) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();

        user = userService.changeUsername(user, username);
        UserDTO userData = UserDTO.userDTOBuilder(user);

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        userService.deleteUserByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
