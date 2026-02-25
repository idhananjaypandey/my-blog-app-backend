package com.dhananjay.blog.controller;

import com.dhananjay.blog.dto.UpdateProfileRequest;
import com.dhananjay.blog.entity.User;
import com.dhananjay.blog.repository.UserRepository;
import com.dhananjay.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getLoggedInUser(Principal principal) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(
            @RequestBody UpdateProfileRequest request,
            Principal principal) {

        return ResponseEntity.ok(
                userService.updateProfile(request, principal.getName())
        );
    }

}