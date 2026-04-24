package com.goiymonan.service;

import com.goiymonan.dto.RegisterRequest;
import com.goiymonan.model.entity.User;
import com.goiymonan.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return false;
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

        return userRepository.insert(user) > 0;
    }

    public Optional<User> login(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findActiveByEmail(email);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();
        if (BCrypt.checkpw(rawPassword, user.getPassword())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
