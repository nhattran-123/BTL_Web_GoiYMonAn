package com.controller;

import com.model.bean.Food;
import com.model.bean.User;
import com.model.dao.FoodDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "FoodListServlet", urlPatterns = {"/foods"})
public class FoodListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("currentUser");

            FoodDAO foodDAO = new FoodDAO();
            List<Food> displayList;

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
                return;
            }

            List<Food> suggestedFoods = foodDAO.getTopSuitableFoodsForUser(user.getId(), 30);
            displayList = new ArrayList<>();
            for (Food food : suggestedFoods) {
                if (food.getSuitabilityScore() >= 50) {
                    displayList.add(food);
                }
            }

            // Gửi danh sách này sang JSP
            request.setAttribute("foodList", displayList);
            request.getRequestDispatcher("/views/auth/food_list.jsp").forward(request, response);
        }
  
}