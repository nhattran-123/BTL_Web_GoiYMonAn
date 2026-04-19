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

@WebServlet(name = "SearchFoodServlet", urlPatterns = {"/search"})
public class SearchFoodServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8"); // Đảm bảo đọc được tiếng Việt có dấu

        // 1. Lấy từ khóa tìm kiếm từ ô input (name="txtSearch")
        String keyword = request.getParameter("txtSearch");
        
        FoodDAO dao = new FoodDAO();
        List<Food> list;

        // 2. Logic gộp: Kiểm tra từ khóa
        if (keyword == null || keyword.trim().isEmpty()) {
            // Trường hợp luồng chính bước 2: Hiển thị danh sách gợi ý (tất cả)
            list = dao.getAllFoods();
        } else {
            // Trường hợp luồng chính bước 4: Lọc dữ liệu theo từ khóa
            list = dao.searchFoodByName(keyword);
        }

        // 3. Đẩy dữ liệu và từ khóa sang JSP để hiển thị
        request.setAttribute("listF", list);
        request.setAttribute("saveKeyword", keyword); 
        
        // Chuyển hướng sang trang giao diện
        // Sửa dòng này:
        request.getRequestDispatcher("views/auth/SearchFood.jsp").forward(request, response);
        
    }
}