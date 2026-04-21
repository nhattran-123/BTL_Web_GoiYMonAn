package com.controller;

import com.model.bean.Food;
import com.model.bean.User;
import com.model.dao.FoodDAO;
import com.model.dao.RecipeDAO;
import com.model.dao.UserFavoriteDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "FoodDetailServlet", urlPatterns = {"/food-detail"})
public class FoodDetailServlet extends HttpServlet {
    
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idRaw = request.getParameter("id"); 
        
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return; 
        }

        // 3. Đã đăng nhập, lấy id user
        try {
            int foodId = Integer.parseInt(idRaw);
            FoodDAO foodDAO = new FoodDAO();
             RecipeDAO recipeDAO = new RecipeDAO();
            UserFavoriteDAO favoriteDAO = new UserFavoriteDAO();
            Food food = foodDAO.getFoodById(foodId);
            java.util.List<com.model.bean.IngredientItem> ingredients = foodDAO.getIngredientsByFoodId(foodId);
            if (food == null) {
                response.sendRedirect(request.getContextPath() + "/foods");
                return;
            }
            String source = request.getParameter("source");
            if ("customized".equals(source)) {
                com.model.bean.AdjustedRecipe adjustedRecipe = recipeDAO.getAdjustedRecipeByFoodAndUser(foodId, currentUser.getId());
                if (adjustedRecipe != null) {
                    food.setRecipe(adjustedRecipe.getRecipe());
                    food.setCalories(adjustedRecipe.getCalories());
                    food.setFat(adjustedRecipe.getFat());
                    food.setProtein(adjustedRecipe.getProtein());
                    food.setCarbohydrate(adjustedRecipe.getCarbohydrate());
                }
            }
            
            boolean isFavorite = favoriteDAO.isFavorite(currentUser.getId(), foodId);
            
            request.setAttribute("food", food);
            request.setAttribute("ingredients", ingredients);
            request.setAttribute("isFavorite", isFavorite);
            request.getRequestDispatcher("/views/auth/food_detail.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace(); 
            response.sendRedirect(request.getContextPath() + "/foods");
        }
    }
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        String foodIdRaw = request.getParameter("foodId");

        if ( foodIdRaw == null) {
            response.sendRedirect(request.getContextPath() + "/foods");
            return;
        }

        try {
            int foodId = Integer.parseInt(foodIdRaw);
            UserFavoriteDAO favoriteDAO = new UserFavoriteDAO();
             if ("favorite".equals(action)) {
                favoriteDAO.addFavorite(currentUser.getId(), foodId);
                response.sendRedirect(request.getContextPath() + "/food-detail?id=" + foodId + "&favoriteAdded=true");
                return;
            }

            if ("unfavorite".equals(action)) {
                favoriteDAO.removeFavorite(currentUser.getId(), foodId);
                response.sendRedirect(request.getContextPath() + "/food-detail?id=" + foodId + "&favoriteRemoved=true");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/foods");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/foods");
        }
        }
    }

