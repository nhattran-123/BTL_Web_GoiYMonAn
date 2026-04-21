/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

/**
 *
 * @author dkhai
 */

import com.model.dao.FoodDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ManageFoodServlet", urlPatterns = {"/admin/manage_food"})
public class ManageFoodServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        FoodDAO dao = new FoodDAO();
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean isDeleted = dao.deleteFood(id);
                
                if (isDeleted) {
                    response.sendRedirect(request.getContextPath() + "/admin/manage_food?success=deleted");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/manage_food?error=delete_failed");
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        request.setAttribute("listF", dao.getAllFoods()); 
        request.getRequestDispatcher("/admin/manage_food.jsp").forward(request, response);
    }
}
