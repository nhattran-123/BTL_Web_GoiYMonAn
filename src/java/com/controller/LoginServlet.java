package com.controller;

import com.model.bean.User;
import com.model.dao.UserDAO;
import com.model.dao.DataDAO;
import com.util.SecurityUtil; 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Lấy dữ liệu người dùng nhập
        String email = request.getParameter("email");
        String rawPassword = request.getParameter("password");
        
        // 2. Băm mật khẩu vừa nhập để đem đi so sánh với DB
        String hashedPassword = SecurityUtil.hashPassword(rawPassword);
        
        // 3. Gọi DAO kiểm tra
        UserDAO dao = new UserDAO();
        User user = dao.checkLogin(email, hashedPassword); // Truyền mật khẩu ĐÃ BĂM
        
        // 4. Xử lý kết quả
        if (user != null) {
            // Đăng nhập thành công -> Lưu user vào Session
            HttpSession session = request.getSession();
            // Lĩnh nhớ cái tên "currentUser" này để tý nữa gọi ra ở trang home nhé
            session.setAttribute("currentUser", user);
            
            DataDAO dataDAO = new DataDAO();
            dataDAO.saveLoginRecord(user.getId());
            
            // --- ĐOẠN LOGIC PHÂN LUỒNG MỚI (ADMIN vs USER) ---
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                // Nếu là Admin -> Bay vào trang Quản trị (Gọi đến AdminDashboardServlet)
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                // Nếu là User bình thường -> Bay vào trang Chủ của khách (Gọi đến HomeServlet)
                response.sendRedirect(request.getContextPath() + "/home");
            }
        } else {
            // Đăng nhập thất bại -> Giữ nguyên Forward để truyền thông báo lỗi
            request.setAttribute("error", "Email hoặc mật khẩu không chính xác!");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
        }
    }
}