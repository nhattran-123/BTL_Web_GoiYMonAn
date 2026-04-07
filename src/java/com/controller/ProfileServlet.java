package com.controller;

import com.model.bean.User;
import com.model.dao.DataDAO;
import com.model.dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
            return;
        }
        
        // 2. Khởi tạo các DAO cần thiết
        DataDAO dataDao = new DataDAO();
        UserDAO userDao = new UserDAO();
        
        // 3. Lấy dữ liệu tổng hợp (20 bệnh và 69 nguyên liệu) để hiển thị danh sách chọn
        request.setAttribute("listDisease", dataDao.getAllDiseases());
        request.setAttribute("listIngredient", dataDao.getAllIngredients());
        
        // 4. Lấy dữ liệu RIÊNG của người dùng này (Những thứ họ đã chọn từ trước)
        // Dữ liệu này dùng để tự động "selected" các ô tương ứng trên giao diện
        request.setAttribute("userDiseaseIds", userDao.getDiseaseIdsByUserId(currentUser.getId()));
        request.setAttribute("userAllergyIds", userDao.getAllergyIdsByUserId(currentUser.getId()));

        // 5. Mở trang Hồ sơ
        request.getRequestDispatcher("/views/auth/profile.jsp").forward(request, response);
    }
}