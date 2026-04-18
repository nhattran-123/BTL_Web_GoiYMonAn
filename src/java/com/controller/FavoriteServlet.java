/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

/**
 *
 * @author Nhat0
 */
import com.model.bean.User;
import com.model.dao.UserFavoriteDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "FavoriteServlet", urlPatterns = {"/favorites"})
public class FavoriteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserFavoriteDAO favoriteDAO = new UserFavoriteDAO();
        String tab = request.getParameter("tab");
        if (!"customized".equals(tab)) {
            tab = "favorites";
        }
        String keyword = request.getParameter("q");

        request.setAttribute("activeTab", tab);
        request.setAttribute("keyword", keyword == null ? "" : keyword.trim());
        request.setAttribute("favoriteFoods", favoriteDAO.getFavoritesByUser(currentUser.getId(), keyword));
        request.setAttribute("customizedFoods", favoriteDAO.getCustomizedFoodsByUser(currentUser.getId(), keyword));
        request.getRequestDispatcher("/views/auth/favorites.jsp").forward(request, response);
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

        if (!"remove".equals(action) || foodIdRaw == null) {
            response.sendRedirect(request.getContextPath() + "/favorites");
            return;
        }

        try {
            int foodId = Integer.parseInt(foodIdRaw);
            UserFavoriteDAO favoriteDAO = new UserFavoriteDAO();
            favoriteDAO.removeFavorite(currentUser.getId(), foodId);
            response.sendRedirect(request.getContextPath() + "/favorites?tab=favorites&removed=true");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/favorites");
        }
    }
}
