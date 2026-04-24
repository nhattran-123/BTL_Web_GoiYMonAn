package com.goiymonan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping("/admin/dashboard")
    public String dashboard() { return "admin/dashboard"; }

    @GetMapping("/admin/manage-user")
    public String manageUser() { return "admin/manage_user"; }

    @GetMapping("/admin/manage-food")
    public String manageFood() { return "admin/manage_food"; }

    @GetMapping("/admin/add-food")
    public String addFood() { return "admin/add_food"; }

    @GetMapping("/admin/edit-food")
    public String editFood() { return "admin/edit_food"; }

    @GetMapping("/admin/manage-ingredient")
    public String manageIngredient() { return "admin/manage_ingredient"; }

    @GetMapping("/admin/add-ingredient")
    public String addIngredient() { return "admin/add_ingredient"; }

    @GetMapping("/admin/edit-ingredient")
    public String editIngredient() { return "admin/edit_ingredient"; }

    @GetMapping("/admin/manage-disease")
    public String manageDisease() { return "admin/manage_disease"; }

    @GetMapping("/admin/settings")
    public String settings() { return "admin/settings"; }
}
