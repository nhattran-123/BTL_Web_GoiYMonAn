package com.controller;

import com.model.bean.Food;
import com.model.dao.FoodDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FoodListServlet", urlPatterns = {"/foods"})
public class FoodListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FoodDAO dao = new FoodDAO();
        List<Food> list = dao.getAllFoods();
        
        request.setAttribute("foodList", list);
        request.getRequestDispatcher("/views/auth/food_list.jsp").forward(request, response);
    }
}