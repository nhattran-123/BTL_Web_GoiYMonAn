package com.controller;

import com.model.dao.IngredientDAO;
import com.model.bean.Ingredient;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddIngredientServlet", urlPatterns = {"/admin/add_ingredient"})
public class AddIngredientServlet extends HttpServlet {

    private IngredientDAO ingredientDAO = new IngredientDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/add_ingredient.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        request.setCharacterEncoding("UTF-8");

        try {
            // Lấy dữ liệu từ Form
            Ingredient ing = new Ingredient();
            ing.setName(request.getParameter("name"));
            ing.setCategory(request.getParameter("category"));
            
            // Xử lý các số liệu (Dùng try-catch ẩn hoặc parse trực tiếp vì input HTML đã chặn nhập chữ)
            ing.setCalories(Double.parseDouble(request.getParameter("calories")));
            ing.setProtein(Double.parseDouble(request.getParameter("protein")));
            ing.setFat(Double.parseDouble(request.getParameter("fat")));
            ing.setCarbohydrate(Double.parseDouble(request.getParameter("carbs")));

            // Gọi DAO lưu vào Database
            boolean isAdded = ingredientDAO.insertIngredient(ing);

            if (isAdded) {
                // Thành công: Quay về trang danh sách và hiện thông báo
                response.sendRedirect(request.getContextPath() + "/admin/manage_ingredient?success=added");
            } else {
                response.getWriter().println("<h1>Đã xảy ra lỗi khi lưu vào Database! Vui lòng kiểm tra Console.</h1>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h1>Lỗi định dạng dữ liệu đầu vào!</h1>");
        }
    }
}