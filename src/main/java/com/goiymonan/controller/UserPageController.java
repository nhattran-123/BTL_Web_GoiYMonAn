package com.goiymonan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    @GetMapping("/home")
    public String home() { return "auth/home"; }

    @GetMapping("/favorites")
    public String favorites() { return "auth/favorites"; }

    @GetMapping("/meal-plan")
    public String mealPlan() { return "auth/meal_plan"; }

    @GetMapping("/profile")
    public String profile() { return "auth/profile"; }

    @GetMapping("/settings")
    public String settings() { return "auth/settings"; }

    @GetMapping("/progress")
    public String progress() { return "auth/progress"; }

    @GetMapping("/customize-recipe")
    public String customizeRecipe() { return "auth/customize_recipe"; }
}
