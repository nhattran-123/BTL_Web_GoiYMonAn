package com.controller;

import com.model.bean.User;
import com.model.dao.MealPlanDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MealPlanServlet extends HttpServlet {

    private final MealPlanDAO mealPlanDAO = new MealPlanDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = requireAuth(request, response);
        if (currentUser == null) {
            return;
        }

        LocalDate selectedDate = parseSelectedDate(request.getParameter("date"));

        List<Map<String, Object>> mealSections = mealPlanDAO.getMealSections(currentUser.getId(), selectedDate);
        double totalCalories = mealPlanDAO.getTotalCalories(currentUser.getId(), selectedDate);

        request.setAttribute("selectedDate", selectedDate.toString());
        request.setAttribute("mealSections", mealSections);
        request.setAttribute("totalCalories", totalCalories);
        request.setAttribute("allFoods", mealPlanDAO.getAllFoods());
        request.getRequestDispatcher("/views/auth/meal_plan.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = requireAuth(request, response);
        if (currentUser == null) {
            return;
        }

        String action = request.getParameter("action");
        LocalDate selectedDate = parseSelectedDate(request.getParameter("selectedDate"));

        if ("addMealFood".equals(action)) {
            int mealTypeId = parseInt(request.getParameter("mealTypeId"));
            int foodId = parseInt(request.getParameter("foodId"));
            if (mealTypeId > 0 && foodId > 0) {
                mealPlanDAO.addMealFood(currentUser.getId(), selectedDate, mealTypeId, foodId);
            }
        } else if ("removeDetail".equals(action)) {
            int detailId = parseInt(request.getParameter("detailId"));
            if (detailId > 0) {
                mealPlanDAO.deleteMealDetail(detailId);
            }
        }

        response.sendRedirect(request.getContextPath() + "/meal_plan?date=" + selectedDate);
    }

    private User requireAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("currentUser");
        if (!(userObj instanceof User)) {
            response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
            return null;
        }
        return (User) userObj;
    }

    private LocalDate parseSelectedDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException ex) {
            return LocalDate.now();
        }
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return -1;
        }
    }
}
