package com.dhananjay.blog.service;

import com.dhananjay.blog.dto.UpdateProfileRequest;
import com.dhananjay.blog.entity.User;
import com.dhananjay.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String updateProfile(UpdateProfileRequest request, String loggedEmail) {

        User user = userRepository.findByEmail(loggedEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update name
        if (request.getName() != null)
            user.setName(request.getName());

        // Update phone
        if (request.getPhone() != null)
            user.setPhone(request.getPhone());

        // Update email
        if (request.getEmail() != null &&
                !request.getEmail().equals(user.getEmail())) {

            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }

            user.setEmail(request.getEmail());
        }

        // Change password
        if (request.getNewPassword() != null &&
                request.getCurrentPassword() != null) {

            if (!passwordEncoder.matches(
                    request.getCurrentPassword(),
                    user.getPassword())) {

                throw new RuntimeException("Current password incorrect");
            }

            user.setPassword(
                    passwordEncoder.encode(request.getNewPassword()));
        }

        userRepository.save(user);

        return "Profile updated successfully";
    }
}