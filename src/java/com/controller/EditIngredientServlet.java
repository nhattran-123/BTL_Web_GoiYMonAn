/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

/**
 *
 * @author dkhai
 */
import com.model.dao.IngredientDAO;
import com.model.bean.Ingredient;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EditIngredientServlet", urlPatterns = {"/admin/edit_ingredient"})
public class EditIngredientServlet extends HttpServlet {
    private IngredientDAO dao = new IngredientDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Ingredient ing = dao.getIngredientById(id);
        if (ing != null) {
            request.setAttribute("ing", ing);
            request.getRequestDispatcher("/admin/edit_ingredient.jsp").forward(request, response);
        } else {
            response.sendRedirect("manage_ingredient");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Ingredient ing = new Ingredient();
        ing.setId(Integer.parseInt(request.getParameter("id")));
        ing.setName(request.getParameter("name"));
        ing.setCategory(request.getParameter("category"));
        ing.setCalories(Double.parseDouble(request.getParameter("calories")));
        ing.setProtein(Double.parseDouble(request.getParameter("protein")));
        ing.setFat(Double.parseDouble(request.getParameter("fat")));
        ing.setCarbohydrate(Double.parseDouble(request.getParameter("carbs")));

        if (dao.updateIngredient(ing)) {
            response.sendRedirect("manage_ingredient?success=updated");
        } else {
            response.getWriter().println("Lỗi cập nhật!");
        }
    }
}