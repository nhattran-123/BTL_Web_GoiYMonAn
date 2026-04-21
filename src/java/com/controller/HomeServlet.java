package com.controller;

import com.model.bean.Food;
import com.model.bean.User;
import com.model.dao.FoodDAO;
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

// Đây chính là cái địa chỉ mà nút Trang chủ đang tìm kiếm!
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
     private final MealPlanDAO mealPlanDAO = new MealPlanDAO();
    private final FoodDAO foodDAO = new FoodDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Kiểm tra xem người dùng đã đăng nhập chưa (bảo mật)
        HttpSession session = request.getSession();
        if (session.getAttribute("currentUser") == null) {
            // Nếu chưa đăng nhập (hoặc hết hạn session), đuổi về trang Login
            response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
            return;
        }
        
        User currentUser = (User) session.getAttribute("currentUser");
        double todayCalories = mealPlanDAO.getTotalCalories(currentUser.getId(), LocalDate.now());
        Map<String, Object> goalData = userDAO.getLatestGoalByUserId(currentUser.getId());
        Map<String, Double> healthData = userDAO.getLatestHealthIndexByUserId(currentUser.getId());
        List<Map<String, Object>> todayMeals = mealPlanDAO.getMealSummaryByDate(currentUser.getId(), LocalDate.now());
        List<Food> suggestions = foodDAO.getTopSuitableFoodsForUser(currentUser.getId(), 3);

        double bmi = healthData.isEmpty() ? calculateBMI(currentUser.getWeight(), currentUser.getHeight()) : healthData.get("bmi");
        double bmr = healthData.isEmpty() ? calculateBMR(currentUser) : healthData.get("bmr");
        String goalLabel = goalData.containsKey("goalType") ? String.valueOf(goalData.get("goalType")) : inferGoal(currentUser);
        double targetCalories = goalData.containsKey("targetCalories") ? (double) goalData.get("targetCalories") : bmr * 1.4;
        double remainCalories = Math.max(0, targetCalories - todayCalories);

        request.setAttribute("todayCalories", todayCalories);
        request.setAttribute("targetCalories", targetCalories);
        request.setAttribute("remainCalories", remainCalories);
        request.setAttribute("bmi", bmi);
        request.setAttribute("bmr", bmr);
        request.setAttribute("goalLabel", goalLabel);
        request.setAttribute("todayMeals", todayMeals);
        request.setAttribute("homeSuggestions", suggestions);
        request.getRequestDispatcher("/views/auth/home.jsp").forward(request, response);
    }
private double calculateBMI(double weightKg, double heightCm) {
        if (weightKg <= 0 || heightCm <= 0) {
            return 0;
        }
        double meter = heightCm / 100.0;
        return weightKg / (meter * meter);
    }

    private double calculateBMR(User user) {
        if (user.getAge() <= 0 || user.getWeight() <= 0 || user.getHeight() <= 0) {
            return 0;
        }
        if ("Nam".equals(user.getGender())) {
            return 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
        }
        return 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161;
    }

    private String inferGoal(User user) {
        if (user.getDesired_weight() < user.getWeight()) {
            return "Giảm cân";
        }
        if (user.getDesired_weight() > user.getWeight()) {
            return "Tăng cân";
        }
        return "Duy trì";
    }
}