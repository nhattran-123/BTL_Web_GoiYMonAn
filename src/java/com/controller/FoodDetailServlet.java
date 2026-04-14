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

        // 2. NẾU CHƯA ĐĂNG NHẬP -> ĐÁ VỀ TRANG LOGIN
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return; // Dừng luôn, không cho chạy code bên dưới
        }

        // 3. ĐÃ ĐĂNG NHẬP -> LẤY ID THỰC TẾ
        int userId = currentUser.getId(); // TO DO: Sau này làm Login thì lấy ID người dùng từ Session
        
        try {
            int foodId = Integer.parseInt(idRaw);
            FoodDAO dao = new FoodDAO();
            
            Food food = dao.getFoodById(foodId);
            
            if (food == null) {
                response.sendRedirect(request.getContextPath() + "/foods");
                return;
            }
            
            request.setAttribute("food", food);
            request.getRequestDispatcher("/views/auth/food_detail.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace(); 
//            response.sendRedirect(request.getContextPath() + "/foods");
        }
    }
}