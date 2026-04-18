<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Nguyên liệu - Healthy Food</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/manage_ingredient.css">
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
        <div class="page-title d-flex" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px;">
            <div>
                <h1>Quản lý Nguyên liệu</h1>
                <p>Thêm sửa xóa nguyên liệu trong hệ thống</p>
            </div>
            <a href="${pageContext.request.contextPath}/admin/add_ingredient" class="btn-add" style="background: #10b981; color: white; padding: 10px 20px; border-radius: 8px; text-decoration: none; font-weight: 500;">
                + Thêm nguyên liệu
            </a>
        </div>

        <c:if test="${param.success == 'deleted'}">
            <div style="background: #d1fae5; color: #059669; padding: 15px; border-radius: 8px; margin-bottom: 20px;">
                <i class="fa-solid fa-circle-check"></i> Đã xóa nguyên liệu thành công!
            </div>
        </c:if>
                
        <c:if test="${param.success == 'updated'}">
            <div style="background: #e0f2fe; color: #0284c7; padding: 15px; border-radius: 8px; margin-bottom: 20px;">
                <i class="fa-solid fa-circle-info"></i> Đã cập nhật nguyên liệu thành công!
            </div>
        </c:if>
                
        <c:if test="${param.success == 'added'}">
            <div style="background: #d1fae5; color: #059669; padding: 15px; border-radius: 8px; margin-bottom: 20px;">
                <i class="fa-solid fa-circle-check"></i> Đã thêm nguyên liệu mới thành công!
            </div>
        </c:if>
        
        <div class="search-card">
            <div class="search-box">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" placeholder="Tìm kiếm theo tên hoặc phân loại...">
            </div>
        </div>

        <div class="table-card">
            <div class="table-header">Danh sách nguyên liệu (${totalRecords != null ? totalRecords : 0})</div>
            
            <table class="custom-table">
                <thead>
                    <tr>
                        <th>Tên nguyên liệu</th>
                        <th>Phân loại</th>
                        <th>Calo</th>
                        <th>Protein</th>
                        <th>fat</th>
                        <th>carbohydrate</th>
                        <th>Đơn vị</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listI}" var="item">
                        <tr>
                            <td style="font-weight: 500;">${item.name}</td>
                            <td><span class="badge-category">${item.category}</span></td>
                            <td>${item.calories}</td>
                            <td>${item.protein}</td>
                            <td>${item.fat}</td>
                            <td>${item.carbohydrate}</td>
                            <td>100g</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/edit_ingredient?id=${item.id}" class="action-btn btn-edit">
                                    <i class="fa-regular fa-pen-to-square"></i>
                                </a>
                                <a href="javascript:void(0);" 
                                   class="action-btn btn-delete"
                                   onclick="showDeleteModal('${item.id}', '${item.name}')">
                                    <i class="fa-regular fa-trash-can"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    
                    <c:if test="${empty listI}">
                        <tr>
                            <td colspan="5" style="text-align: center; padding: 30px; color: #9ca3af;">
                                Chưa có dữ liệu nguyên liệu nào.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>

            <c:if test="${totalPages > 1}">
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="manage_ingredient?page=${currentPage - 1}"><i class="fa-solid fa-angle-left"></i></a>
                    </c:if>
                    
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <a href="manage_ingredient?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                    </c:forEach>
                    
                    <c:if test="${currentPage < totalPages}">
                        <a href="manage_ingredient?page=${currentPage + 1}"><i class="fa-solid fa-angle-right"></i></a>
                    </c:if>
                </div>
            </c:if>
        </div>
    </main>

    <div class="modal-overlay" id="deleteModal">
        <div class="modal-content">
            <div class="modal-icon">
                <i class="fa-solid fa-circle-exclamation"></i>
            </div>
            <h3>Xác nhận xóa?</h3>
            <p>Bạn có chắc chắn muốn xóa nguyên liệu <strong id="deleteIngredientName"></strong> không? Hành động này không thể hoàn tác.</p>
            <div class="modal-actions">
                <button class="btn-cancel" onclick="closeDeleteModal()">Hủy bỏ</button>
                <a href="#" id="confirmDeleteBtn" class="btn-confirm-delete">Xóa !</a>
            </div>
        </div>
    </div>

    <script>
        function showDeleteModal(id, name) {
            // Hiện overlay
            document.getElementById('deleteModal').style.display = 'flex';
            // Set tên vào câu hỏi
            document.getElementById('deleteIngredientName').innerText = name;
            // Tạo link Action gửi về Servlet và set vào nút Xóa
            let deleteUrl = '${pageContext.request.contextPath}/admin/manage_ingredient?action=delete&id=' + id;
            document.getElementById('confirmDeleteBtn').href = deleteUrl;
        }

        function closeDeleteModal() {
            // Ẩn overlay
            document.getElementById('deleteModal').style.display = 'none';
        }
    </script>
</body>
</html>