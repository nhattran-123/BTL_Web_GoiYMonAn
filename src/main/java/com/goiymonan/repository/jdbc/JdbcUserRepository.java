package com.goiymonan.repository.jdbc;

import com.goiymonan.model.entity.User;
import com.goiymonan.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> mapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("User_id"));
        user.setFullName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("Role"));
        user.setIs_activate(rs.getInt("is_activate"));
        return user;
    };

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean existsByEmail(String email) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM Users WHERE email = ?", Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public Optional<User> findActiveByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT TOP 1 * FROM Users WHERE email = ? AND is_activate = 1", mapper, email);
        return users.stream().findFirst();
    }

    @Override
    public int insert(User user) {
        return jdbcTemplate.update(
                "INSERT INTO Users(name, email, password, Role, is_activate, create_at) VALUES (?, ?, ?, 'USER', 1, GETDATE())",
                user.getFullName(), user.getEmail(), user.getPassword()
        );
    }
}
