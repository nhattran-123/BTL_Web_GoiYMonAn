package com.controller;

import com.model.bean.User;
import com.model.dao.*; // Import tất cả các DAO
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        // KIỂM TRA BẢO MẬT
        if (currentUser == null || !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
            return;
        }

        // 1. Khởi tạo các DAO riêng biệt
        UserDAO userDAO = new UserDAO();
        FoodDAO foodDAO = new FoodDAO();
        MealPlanDAO mealPlanDAO = new MealPlanDAO();
        DataDAO dataDAO = new DataDAO();
        UserFavoriteDAO favoriteDAO = new UserFavoriteDAO();

        // 2. Lấy dữ liệu từ từng DAO
        int totalUsers = userDAO.getTotalUsers();
        int totalFoods = foodDAO.getTotalFoods();
        int totalMenus = mealPlanDAO.getTotalMenus();
        int todayActivities = dataDAO.getTodayActivities();
        
        Map<String, Integer> topFoods = favoriteDAO.getTopFavoriteFoods(3); 
        Map<String, Double> popularGoals = userDAO.getPopularGoals();

        // 3. Gắn dữ liệu gửi sang giao diện (JSP)
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalFoods", totalFoods);
        request.setAttribute("totalMenus", totalMenus);
        request.setAttribute("todayActivities", todayActivities);
        request.setAttribute("topFoods", topFoods);
        request.setAttribute("popularGoals", popularGoals);

        // 4. Chuyển hướng
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}