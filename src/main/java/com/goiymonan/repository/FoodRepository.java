package com.goiymonan.repository;

import com.goiymonan.model.entity.Food;

import java.util.List;
import java.util.Optional;

public interface FoodRepository {
    List<Food> findAll();
    List<Food> search(String keyword);
    Optional<Food> findById(int id);
}
