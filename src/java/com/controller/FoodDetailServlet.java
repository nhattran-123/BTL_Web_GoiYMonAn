package com.controller;

import com.model.bean.Food;
import com.model.dao.FoodDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "FoodDetailServlet", urlPatterns = {"/food-detail"})
public class FoodDetailServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idRaw = request.getParameter("id"); 
        
        
        HttpSession session = request.getSession();
        com.model.bean.User currentUser = (com.model.bean.User) session.getAttribute("currentUser");

        // 2. Nếu chưa đăng nhập -> về trang login
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return; // Dừng luôn, không cho chạy code bên dưới
        }

        // 3. Đã đăng nhập, lấy id user
        int userId = currentUser.getId(); 
        
        try {
            int foodId = Integer.parseInt(idRaw);
            FoodDAO dao = new FoodDAO();
            
            Food food = dao.getFoodById(foodId);
            java.util.List<com.model.bean.IngredientItem> ingredients = dao.getIngredientsByFoodId(foodId);
            if (food == null) {
                response.sendRedirect(request.getContextPath() + "/foods");
                return;
            }
            
            request.setAttribute("food", food);
            request.setAttribute("ingredients", ingredients);
            request.getRequestDispatcher("/views/auth/food_detail.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace(); 
            response.sendRedirect(request.getContextPath() + "/foods");
        }
    }
}