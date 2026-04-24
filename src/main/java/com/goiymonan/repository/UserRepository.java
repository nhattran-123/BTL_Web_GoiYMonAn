package com.goiymonan.repository;

import com.goiymonan.model.entity.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByEmail(String email);
    Optional<User> findActiveByEmail(String email);
    int insert(User user);
}
