package com.goiymonan.repository.jdbc;

import com.goiymonan.model.entity.Food;
import com.goiymonan.repository.FoodRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcFoodRepository implements FoodRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Food> mapper = (rs, rowNum) -> {
        Food food = new Food();
        food.setFood_id(rs.getInt("Food_id"));
        food.setFood_name(rs.getString("name"));
        food.setDescription(rs.getString("description"));
        food.setCalories(rs.getDouble("calories"));
        food.setImage_url(rs.getString("image"));
        return food;
    };

    public JdbcFoodRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Food> findAll() {
        return jdbcTemplate.query("SELECT * FROM Food ORDER BY Food_id DESC", mapper);
    }

    @Override
    public List<Food> search(String keyword) {
        return jdbcTemplate.query(
                "SELECT * FROM Food WHERE name LIKE ? ORDER BY Food_id DESC",
                mapper,
                "%" + keyword + "%"
        );
    }

    @Override
    public Optional<Food> findById(int id) {
        List<Food> foods = jdbcTemplate.query("SELECT * FROM Food WHERE Food_id = ?", mapper, id);
        return foods.stream().findFirst();
    }
}
