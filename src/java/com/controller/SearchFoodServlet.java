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

@WebServlet(name = "SearchFoodServlet", urlPatterns = {"/search"})
public class SearchFoodServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8"); // Đảm bảo đọc được tiếng Việt có dấu

        // 1. Lấy từ khóa tìm kiếm từ ô input (name="txtSearch")
        String keyword = request.getParameter("txtSearch");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
            return;
        }
        
        FoodDAO dao = new FoodDAO();
        List<Food> list = dao.searchSuitableFoodsForUser(user.getId(), keyword == null ? "" : keyword.trim(), 200);

        // 3. Đẩy dữ liệu và từ khóa sang JSP để hiển thị
        request.setAttribute("listF", list);
        request.setAttribute("saveKeyword", keyword); 
        
        // Chuyển hướng sang trang giao diện
        // Sửa dòng này:
        request.getRequestDispatcher("/views/auth/SearchFood.jsp").forward(request, response);
        
    }
}