package com.goiymonan.service;

import com.goiymonan.model.entity.Food;
import com.goiymonan.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public List<Food> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        return foodRepository.search(keyword.trim());
    }

    public Optional<Food> findById(int id) {
        return foodRepository.findById(id);
    }
}
