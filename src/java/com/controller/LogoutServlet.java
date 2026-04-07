package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Lấy thẻ phiên làm việc (Session) hiện tại của người dùng
        // Tham số 'false' nghĩa là: Nếu chưa có session thì không tạo cái mới làm gì cả
        HttpSession session = request.getSession(false);
        
        // 2. Nếu tìm thấy thẻ Session -> Xóa bỏ toàn bộ (Hủy đăng nhập)
        if (session != null) {
            session.invalidate(); 
        }
        
        // 3. Đá người dùng văng ra ngoài màn hình Đăng nhập
        response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
    }
}