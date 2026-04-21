package com.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nhat0
 */

import com.model.bean.Food;
import com.model.dao.FoodDAO;
import com.model.dao.IngredientDAO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
@WebServlet(name = "EditFoodServlet", urlPatterns = {"/admin/edit_food"})
public class EditFoodServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int foodId = Integer.parseInt(request.getParameter("id"));
            FoodDAO foodDAO = new FoodDAO();
            IngredientDAO ingredientDAO = new IngredientDAO();

            Food food = foodDAO.getFoodById(foodId);
            if (food == null) {
                response.sendRedirect(request.getContextPath() + "/admin/manage_food?error=not_found");
                return;
            }

            request.setAttribute("food", food);
            request.setAttribute("foodIngredients", foodDAO.getIngredientsByFoodId(foodId));
            request.setAttribute("ingredients", ingredientDAO.searchIngredients(""));
            request.getRequestDispatcher("/admin/edit_food.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/manage_food?error=not_found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        FoodDAO foodDAO = new FoodDAO();
        try {
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            Food food = foodDAO.getFoodById(foodId);
            if (food == null) {
                response.sendRedirect(request.getContextPath() + "/admin/manage_food?error=not_found");
                return;
            }

            food.setFood_name(request.getParameter("name"));
            food.setDescription(request.getParameter("description"));
            food.setRecipe(request.getParameter("recipe"));

            try {
                food.setCalories(Double.parseDouble(request.getParameter("calories")));
                food.setProtein(Double.parseDouble(request.getParameter("protein")));
                food.setFat(Double.parseDouble(request.getParameter("fat")));
                food.setCarbohydrate(Double.parseDouble(request.getParameter("carbohydrate")));
            } catch (NumberFormatException e) {
                food.setCalories(0);
                food.setProtein(0);
                food.setFat(0);
                food.setCarbohydrate(0);
            }

            Part filePart = request.getPart("imageFile");
            String fileName = filePart != null ? Paths.get(filePart.getSubmittedFileName()).getFileName().toString() : "";
            if (fileName != null && !fileName.isEmpty()) {
                String uploadPath = request.getServletContext().getRealPath("") + File.separator + "assets" + File.separator + "images";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String finalFileName = System.currentTimeMillis() + "_" + fileName;
                filePart.write(uploadPath + File.separator + finalFileName);
                food.setImage_url(finalFileName);
            }

            String[] ingredientIds = request.getParameterValues("ingredientId[]");
            String[] quantities = request.getParameterValues("quantity[]");
            String[] units = request.getParameterValues("unit[]");

            boolean success = foodDAO.updateFoodWithIngredients(food, ingredientIds, quantities, units);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/manage_food?success=updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/edit_food?id=" + foodId + "&error=true");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/manage_food?error=update_failed");
        }
    }
}

