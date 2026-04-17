package com.controller;

import com.model.bean.Food;
import com.model.bean.User;
import com.model.dao.FoodDAO;
import java.io.IOException;
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

            
                // Nếu đã đăng nhập -> Lọc món ăn theo dị ứng của user đó
                displayList = foodDAO.getSafeFoodsForUser(user.getId());

            // Gửi danh sách này sang JSP
            request.setAttribute("foodList", displayList);
            request.getRequestDispatcher("/views/auth/food_list.jsp").forward(request, response);
        }
  
}