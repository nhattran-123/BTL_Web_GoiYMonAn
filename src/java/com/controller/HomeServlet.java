package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Đây chính là cái địa chỉ mà nút Trang chủ đang tìm kiếm!
@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Kiểm tra xem người dùng đã đăng nhập chưa (bảo mật)
        HttpSession session = request.getSession();
        if (session.getAttribute("currentUser") == null) {
            // Nếu chưa đăng nhập (hoặc hết hạn session), đuổi về trang Login
            response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
            return;
        }
        
        // 2. Đã đăng nhập thì "Mở cửa" dẫn vào file home.jsp
        // (Lưu ý: Nếu Lĩnh để file home.jsp ở thư mục khác thì nhớ sửa lại đường dẫn này cho đúng nhé)
        request.getRequestDispatcher("/views/auth/home.jsp").forward(request, response);
    }
}