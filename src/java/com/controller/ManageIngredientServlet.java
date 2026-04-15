/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

/**
 *
 * @author dkhai
 */
import com.model.bean.Ingredient;
import com.model.dao.IngredientDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ManageIngredientServlet", urlPatterns = {"/admin/manage-ingredient"})
public class ManageIngredientServlet extends HttpServlet {
    private IngredientDAO ingredientDAO = new IngredientDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            String action = request.getParameter("action");
            if("delete".equals(action)) {
                try{
                    int id = Integer.parseInt(request.getParameter("id"));
                    ingredientDAO.deleteIngredient(id);
                    response.sendRedirect(request.getContextPath() + "/admin/manage-ingredient?success=deleted");
                    return;
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

        // Xử lý Phân trang
        int page = 1; // Mặc định là trang 1
        int recordsPerPage = 10; // Hiển thị 10 nguyên liệu / trang

        // Lấy số trang từ URL (ví dụ: ?page=2), nếu có thì gán lại
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // Tính toán vị trí bắt đầu (offset)
        int offset = (page - 1) * recordsPerPage;

        // Gọi DAO lấy dữ liệu theo trang và tổng số lượng
        List<Ingredient> listIngredient = ingredientDAO.getIngredientsByPage(offset, recordsPerPage);
        int totalRecords = ingredientDAO.getTotalIngredient();
        
        // Tính tổng số trang (Ví dụ: 25 record / 10 = 2.5 -> làm tròn lên thành 3 trang)
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        // Đẩy dữ liệu sang JSP
        request.setAttribute("listI", listIngredient);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords); // Gửi thêm để hiển thị "(1000)" trên tiêu đề
        
        // Forward sang JSP
        request.getRequestDispatcher("/admin/manage-ingredient.jsp").forward(request, response);
        

    }
}
