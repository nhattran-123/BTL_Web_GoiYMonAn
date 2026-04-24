package com.goiymonan.controller;

import com.goiymonan.model.entity.Food;
import com.goiymonan.service.FoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/food-list")
    public String foodList(Model model) {
        model.addAttribute("foodList", foodService.findAll());
        return "auth/food_list";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "q", required = false) String keyword, Model model) {
        List<Food> foods = foodService.search(keyword);
        model.addAttribute("foodList", foods);
        model.addAttribute("keyword", keyword);
        return "auth/search_food";
    }

    @GetMapping("/food-detail")
    public String foodDetail(@RequestParam("id") int id, Model model) {
        Food food = foodService.findById(id).orElse(null);
        model.addAttribute("food", food);
        return "auth/food_detail";
    }
}
