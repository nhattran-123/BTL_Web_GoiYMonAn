package com.controller;

import com.model.bean.User;
import com.model.dao.UserDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Đường dẫn này khi chạy web sẽ là: localhost:8080/btl-web-N11/admin/users
@WebServlet(name = "ManageUserServlet", urlPatterns = {"/admin/users"})
public class ManageUserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        UserDAO dao = new UserDAO();
        
        // 1. Xử lý tính năng XÓA (nếu có tham số action=deactivate)
        String action = request.getParameter("action");
        if ("deactivate".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("id"));
            dao.deleteUser(userId);
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        // 2. LẤY TRANG HIỆN TẠI (Người dùng đang bấm vào số mấy)
        String indexPage = request.getParameter("page");
        if (indexPage == null) {
            indexPage = "1"; // Nếu mới vào trang quản lý thì mặc định là trang 1
        }
        int index = Integer.parseInt(indexPage);

        // 3. LẤY TỪ KHÓA TÌM KIẾM VÀ LỌC QUYỀN (Nếu có)
        String keyword = request.getParameter("keyword");
        String role = request.getParameter("role");

        // 4. TÍNH TỔNG SỐ TRANG
        // Ví dụ có 25 người -> 25/10 = 2 trang dư 5 -> phải có 3 trang
        int total = dao.getTotalUsers(keyword, role);
        int endPage = total / 10;
        if (total % 10 != 0) {
            endPage++;
        }

        // 5. LẤY DANH SÁCH 10 NGƯỜI CỦA TRANG ĐÓ
        List<User> list = dao.getUsersPaged(keyword, role, index);

        // 6. GỬI DỮ LIỆU SANG JSP ĐỂ HIỂN THỊ
        request.setAttribute("listU", list);      // Danh sách 10 người
        request.setAttribute("endP", endPage);   // Tổng số trang để in ra các số 1, 2, 3...
        request.setAttribute("tag", index);      // Trang hiện tại để tô màu nút (active)
        request.setAttribute("txtSearch", keyword);
        request.setAttribute("txtRole", role);
        request.setAttribute("stats", dao.getUserStats()); // Dữ liệu cho 4 cái thẻ Dashboard

        // Nhớ check lại đường dẫn file JSP của Lĩnh (admin/manage-user.jsp)
        request.getRequestDispatcher("/admin/manage-user.jsp").forward(request, response);
    }
}