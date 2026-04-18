/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

/**
 *
 * @author Nhat0
 */

import com.model.bean.User;
import com.model.dao.MealPlanDAO;
import com.model.dao.UserDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/progress")
public class ProgressServlet extends HttpServlet {

    private final MealPlanDAO mealPlanDAO = new MealPlanDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
            return;
        }

        mealPlanDAO.archivePastMealsToHistory(currentUser.getId());
        List<Map<String, Object>> recentMealHistory = mealPlanDAO.getRecentMealHistory(currentUser.getId(), 3);

        double bmi = calculateBMI(currentUser.getWeight(), currentUser.getHeight());
        double progressPercent = calculateProgressPercent(currentUser.getWeight(), currentUser.getDesired_weight());
        double heightProgressPercent = calculateProgressPercent(currentUser.getHeight(), currentUser.getDesired_height());
        String goalLabel = currentUser.getDesired_weight() < currentUser.getWeight() ? "Giảm cân" : "Tăng cân";
        int totalDaysFollowed = Math.max(1, recentMealHistory.size());

        request.setAttribute("todayCalories", mealPlanDAO.getTotalCalories(currentUser.getId(), LocalDate.now()));
        request.setAttribute("bmi", bmi);
        request.setAttribute("goalLabel", goalLabel);
        request.setAttribute("progressPercent", progressPercent);
        request.setAttribute("heightProgressPercent", heightProgressPercent);
        request.setAttribute("recentMealHistory", recentMealHistory);
        request.setAttribute("totalDaysFollowed", totalDaysFollowed);
        request.getRequestDispatcher("/views/auth/progress.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
            return;
        }

        try {
            float currentHeight = Float.parseFloat(request.getParameter("current_height"));
            float currentWeight = Float.parseFloat(request.getParameter("current_weight"));

            currentUser.setHeight(currentHeight);
            currentUser.setWeight(currentWeight);
            userDAO.updateHealthProfile(currentUser);
            session.setAttribute("currentUser", currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/progress");
    }

    private double calculateBMI(double weightKg, double heightCm) {
        if (weightKg <= 0 || heightCm <= 0) {
            return 0;
        }
        double meter = heightCm / 100.0;
        return weightKg / (meter * meter);
    }

    private double calculateProgressPercent(double currentWeight, double goalWeight) {
        if (currentWeight <= 0 || goalWeight <= 0 || currentWeight == goalWeight) {
            return 100;
        }
        double delta = Math.abs(currentWeight - goalWeight);
        double base = Math.max(currentWeight, goalWeight);
        double percent = (1 - (delta / base)) * 100;
        if (percent < 0) {
            return 0;
        }
        if (percent > 100) {
            return 100;
        }
        return percent;
    }
}

