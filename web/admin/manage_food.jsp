<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Món ăn - Healthy Food</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .food-img-td { 
            width: 50px; 
            height: 50px; 
            border-radius: 50%; 
            object-fit: cover; 
            display: block;
        }
        /* CSS cho Modal (Hộp thoại xác nhận xóa) */
        .modal-overlay {
            display: none; /* Ẩn mặc định, chỉ hiện khi gọi bằng JS */
            position: fixed;
            top: 0; 
            left: 0; 
            width: 100%; 
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5); /* Nền đen mờ */
            z-index: 9999;
            align-items: center;
            justify-content: center;
        }
        .modal-content {
            background: white;
            padding: 30px;
            border-radius: 12px;
            width: 400px;
            text-align: center;
            box-shadow: 0 4px 20px rgba(0,0,0,0.15);
        }
        .modal-icon {
            font-size: 48px;
            color: #dc2626;
            margin-bottom: 15px;
        }
        .modal-content h3 {
            margin: 0 0 10px 0;
            color: #111827;
            font-size: 20px;
        }
        .modal-content p {
            color: #6b7280;
            font-size: 14px;
            line-height: 1.5;
            margin-bottom: 0;
        }
        .modal-actions {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 25px;
        }
        .btn-cancel {
            padding: 10px 20px;
            background: white;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            color: #374151;
            font-weight: 500;
            cursor: pointer;
            font-size: 14px;
        }
        .btn-cancel:hover { background: #f9fafb; }
        
        .btn-confirm-delete {
            padding: 10px 20px;
            background: #dc2626;
            color: white;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 500;
            font-size: 14px;
        }
        .btn-confirm-delete:hover { background: #b91c1c; color: white;}
    </style>
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
                <a href="${pageContext.request.contextPath}/admin/users" class="nav-link ${param.active == 'users' ? 'active' : ''}">
                    <i class="fa-regular fa-address-book"></i> Quản lý người dùng
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/manage_food" class="nav-link active">
                    <i class="fa-solid fa-utensils"></i> Quản lý món ăn
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/manage_ingredient" class="nav-link">
                    <i class="fa-solid fa-apple-whole"></i> Quản lý nguyên liệu
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/manage-disease" class="nav-link">
                    <i class="fa-solid fa-heart-pulse"></i> Quản lý bệnh lý
                </a>
            </li>
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
                <h1>Quản lý món ăn</h1>
                <p>Thêm sửa xóa món ăn trong hệ thống</p>
            </div>
            <a href="${pageContext.request.contextPath}/admin/add_food" class="btn-add" style="background: #10b981; color: white; padding: 10px 20px; border-radius: 8px; text-decoration: none;">
                + Thêm món ăn
            </a>
        </div>
                
        <c:if test="${param.success == 'deleted'}">
            <div style="background: #d1fae5; color: #059669; padding: 15px; border-radius: 8px; margin-bottom: 20px;">
                <i class="fa-solid fa-circle-check"></i> Đã xóa món ăn thành công!
            </div>
        </c:if>
        <c:if test="${param.success == 'updated'}">
            <div style="background: #d1fae5; color: #059669; padding: 15px; border-radius: 8px; margin-bottom: 20px;">
                <i class="fa-solid fa-circle-check"></i> Đã cập nhật món ăn thành công!
            </div>
        </c:if>
        <c:if test="${param.error == 'delete_failed'}">
            <div style="background: #fee2e2; color: #dc2626; padding: 15px; border-radius: 8px; margin-bottom: 20px;">
                <i class="fa-solid fa-circle-exclamation"></i> Có lỗi xảy ra, không thể xóa món ăn này!
            </div>
        </c:if>
                
        <div class="search-box" style="margin-bottom: 20px;">
            <i class="fa-solid fa-magnifying-glass"></i>
            <input type="text" placeholder="Tìm kiếm theo tên..." autocomplete="off">
        </div>

        <div class="table-card">
            <div class="table-header">Danh sách món ăn</div>
            <table class="custom-table" style="width: 100%; border-collapse: collapse; text-align: left;">
                <thead>
                    <tr style="border-bottom: 1px solid #eee;">
                        <th style="padding: 15px;">Hình</th>
                        <th>Tên món ăn</th>
                        <th>Calo</th>
                        <th>Protein</th>
                        <th>Chất béo</th>
                        <th>Carbs</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listF}" var="f">
                        <tr style="border-bottom: 1px dashed #eee;">
                            <td style="padding: 10px 15px;">
                                <img src="${pageContext.request.contextPath}/assets/images/${f.image_url}" 
                                     alt="${f.food_name}" 
                                     class="food-img-td"
                                     onerror="this.onerror=null; this.src='https://via.placeholder.com/150?text=No+Image'">
                            </td>
                            <td style="font-weight: 500;">${f.food_name}</td>
                            <td>${f.calories}</td>
                            <td>${f.protein}g</td>
                            <td>${f.fat}g</td>
                            <td>${f.carbohydrate}g</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/edit_food?id=${f.food_id}" class="action-btn btn-edit"><i class="fa-regular fa-pen-to-square"></i></a>
                                <a href="javascript:void(0);" class="action-btn btn-delete" onclick="showDeleteModal('${f.food_id}', '${f.food_name}')">
                                    <i class="fa-regular fa-trash-can"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    
                    <c:if test="${empty listF}">
                        <tr>
                            <td colspan="7" style="text-align: center; padding: 30px; color: #9ca3af;">
                                Chưa có dữ liệu món ăn.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </main>
    <div class="modal-overlay" id="deleteModal">
        <div class="modal-content">
            <div class="modal-icon">
                <i class="fa-solid fa-circle-exclamation"></i>
            </div>
            <h3>Xác nhận xóa?</h3>
            <p>Bạn có chắc chắn muốn xóa món ăn <strong id="deleteFoodName"></strong> không? Toàn bộ thực đơn và đánh giá liên quan sẽ bị xóa. Hành động này không thể hoàn tác.</p>
            <div class="modal-actions">
                <button class="btn-cancel" onclick="closeDeleteModal()">Hủy bỏ</button>
                <a href="#" id="confirmDeleteBtn" class="btn-confirm-delete">Xóa !</a>
            </div>
        </div>
    </div>

    <script>
        function showDeleteModal(id, name) {
            document.getElementById('deleteModal').style.display = 'flex';
            document.getElementById('deleteFoodName').innerText = name;
            let deleteUrl = '${pageContext.request.contextPath}/admin/manage_food?action=delete&id=' + id;
            document.getElementById('confirmDeleteBtn').href = deleteUrl;
        }

        function closeDeleteModal() {
            document.getElementById('deleteModal').style.display = 'none';
        }
    </script>                
</body>
</html>