package com.ams.backend.controller;

import com.ams.backend.entity.AuthenticateRequest;
import com.ams.backend.entity.User;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.UserMail;
import com.ams.backend.security.JwtTokenUtil;
import com.ams.backend.service.interfaces.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/user")
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") int userId)
            throws ResourceNotFoundException{
        User user = userService.getUserById(userId);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/user/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody AuthenticateRequest request) {
        User user = userService.getUserByMailAndPassword(request.getMail(), request.getPassword());

        if (user != null && user.getMail() != null) {
            String token = jwtTokenUtil.generateToken(user.getMail(), user.getName(), user.getLastName());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/user/exists")
    public ResponseEntity<Boolean> userExists(@RequestBody UserMail request) {
        boolean exists = userService.getUserByMail(request) != null;
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable(value = "id") int userId,
            @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        final User updatedUser = userService.updateUser(userId, userDetails);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") int userId)
            throws ResourceNotFoundException {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
