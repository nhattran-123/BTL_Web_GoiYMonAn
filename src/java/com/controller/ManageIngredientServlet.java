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

@WebServlet(name = "ManageIngredientServlet", urlPatterns = {"/admin/manage_ingredient"})
public class ManageIngredientServlet extends HttpServlet {
    IngredientDAO ingredientDAO = new IngredientDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        // 1. Lấy và xử lý từ khóa tìm kiếm
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        
        // 2. Xử lý chức năng Xóa 
        String action = request.getParameter("action");
        if("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                ingredientDAO.deleteIngredient(id);
                response.sendRedirect(request.getContextPath() + "/admin/manage_ingredient?success=deleted");
                return;
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        // 3. Xử lý Phân trang
        int page = 1; 
        int recordsPerPage = 10; 

        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        int offset = (page - 1) * recordsPerPage;

        //  Lấy danh sách nguyên liệu có LỌC theo keyword VÀ PHÂN TRANG
        List<Ingredient> listI = ingredientDAO.searchIngredientsByPage(keyword, offset, recordsPerPage);
        
        //Đếm tổng số lượng bản ghi CÓ LỌC theo keyword
        int totalRecords = ingredientDAO.getTotalIngredientByKeyword(keyword);
        
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        // 6. Đẩy dữ liệu sang JSP
        request.setAttribute("listI", listI); 
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("searchKeyword", keyword);
        
        request.getRequestDispatcher("/admin/manage_ingredient.jsp").forward(request, response);
    }
}