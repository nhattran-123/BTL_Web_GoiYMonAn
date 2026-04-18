<%-- 
    Document   : edit_ingredient
    Created on : Apr 16, 2026, 12:12:33 AM
    Author     : dkhai
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<head>
    <meta charset="UTF-8">
    <title>Sửa nguyên liệu - Healthy Food</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/add_ingredient.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

    <aside class="sidebar">
        <div class="brand">
            <div class="brand-icon"><i class="fa-solid fa-leaf text-white"></i></div>
            <div class="brand-text"><h2>Healthy Food</h2><span>Dinh dưỡng thông minh</span></div>
        </div>
        <ul class="nav-menu">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link">
                    <i class="fa-solid fa-table-cells-large"></i> Tổng quan
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/users" class="nav-link">
                    <i class="fa-regular fa-address-book"></i> Quản lý người dùng
                </a>
            </li>
            <li class="nav-item">
                <a href="#" class="nav-link"><i class="fa-solid fa-utensils"></i> Quản lý món ăn</a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/manage_ingredient" class="nav-link active">
                    <i class="fa-solid fa-apple-whole"></i> Quản lý nguyên liệu
                </a>
            </li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-heart-pulse"></i> Quản lý bệnh lý</a></li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/settings.jsp" class="nav-link">
                    <i class="fa-solid fa-gear"></i> Cài đặt
                </a>
            </li>
        </ul>
        <div class="logout-box">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link">
                <i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất
            </a>
        </div>
    </aside>

    <main class="main-content">
        <div class="add-form-wrapper">
            <div class="add-form-card">
                <a href="${pageContext.request.contextPath}/admin/manage_ingredient" class="close-btn">&times;</a>
                
                <div class="form-title">
                    <h2>Sửa nguyên liệu</h2>
                    <p>Cập nhật lại thông tin định danh và chỉ số dinh dưỡng</p>
                </div>

                <form action="${pageContext.request.contextPath}/admin/edit_ingredient" method="POST">
                    
                    <input type="hidden" name="id" value="${ing.id}">
                    
                    <div class="form-group">
                        <label>Tên nguyên liệu</label>
                        <input type="text" name="name" class="form-control" value="${ing.name}" required>
                    </div>

                    <div class="form-group">
                        <label>Phân loại</label>
                        <select name="category" class="form-control" required>
                            <option value="Thit" ${ing.category == 'Thit' ? 'selected' : ''}>Thịt</option>
                            <option value="Hai san" ${ing.category == 'Hai san' ? 'selected' : ''}>Hải sản</option>
                            <option value="Rau cu" ${ing.category == 'Rau cu' ? 'selected' : ''}>Rau củ</option>
                            <option value="Ngu coc" ${ing.category == 'Ngu coc' ? 'selected' : ''}>Ngũ cốc</option>
                            <option value="Trung" ${ing.category == 'Trung' ? 'selected' : ''}>Trứng</option>
                            <option value="Sua" ${ing.category == 'Sua' ? 'selected' : ''}>Sữa</option>
                            <option value="Noi tang" ${ing.category == 'Noi tang' ? 'selected' : ''}>Nội tạng</option>
                            <option value="Nam" ${ing.category == 'Nam' ? 'selected' : ''}>Nấm</option>
                            <option value="Tinh bot" ${ing.category == 'Tinh bot' ? 'selected' : ''}>Tinh bột</option>
                            <option value="Gia vi" ${ing.category == 'Gia vi' ? 'selected' : ''}>Gia vi</option>
                            <option value="Hat" ${ing.category == 'Hat' ? 'selected' : ''}>Hạt</option>
                            <option value="Trai cay" ${ing.category == 'Trai cay' ? 'selected' : ''}>Trái cây</option>
                            <option value="Che pham" ${ing.category == 'Che pham' ? 'selected' : ''}>Chế phẩm</option>
                            <option value="Topping" ${ing.category == 'Topping' ? 'selected' : ''}>Topping</option>
                            <option value="Rau song" ${ing.category == 'Rau song' ? 'selected' : ''}>Rau sống</option>
                            <option value="Nen dung" ${ing.category == 'Nen dung' ? 'selected' : ''}>Nền dùng</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Chỉ số dinh dưỡng (Tính trên 100g)</label>
                        <div class="macro-grid">
                            <div>
                                <input type="number" step="0.1" min="0" name="calories" class="form-control" value="${ing.calories}" required>
                                <small style="color: #9ca3af;">Calo</small>
                            </div>
                            <div>
                                <input type="number" step="0.1" min="0" name="protein" class="form-control" value="${ing.protein}" required>
                                <small style="color: #9ca3af;">Protein (g)</small>
                            </div>
                            <div>
                                <input type="number" step="0.1" min="0" name="fat" class="form-control" value="${ing.fat}" required>
                                <small style="color: #9ca3af;">Fat (g)</small>
                            </div>
                            <div>
                                <input type="number" step="0.1" min="0" name="carbs" class="form-control" value="${ing.carbohydrate}" required>
                                <small style="color: #9ca3af;">Carbs (g)</small>
                            </div>
                        </div>
                    </div>

                    <div class="form-actions" style="margin-top: 30px;">
                        <a href="${pageContext.request.contextPath}/admin/manage_ingredient" class="btn-cancel">Hủy</a>
                        <button type="submit" class="btn-save">Cập nhật thay đổi</button>
                    </div>
                </form>
            </div>
        </div>
    </main>

</body>
</html>


                    