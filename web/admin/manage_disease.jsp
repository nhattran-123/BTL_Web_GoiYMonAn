<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Bệnh lý - Healthy Food</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/manage_ingredient.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        .sidebar .nav-link { opacity: 1 !important; }
        .tabs-container { display: flex; gap: 10px; margin-bottom: 25px; }
        .tab-btn {
            padding: 8px 16px; background: #f9fafb; border: 1px solid #e5e7eb;
            border-radius: 8px; color: #6b7280; font-size: 14px;
            cursor: pointer; display: flex; align-items: center; gap: 8px;
            transition: 0.2s; font-family: inherit; outline: none;
        }
        .tab-btn.active {
            background: #fff; color: #111827; border-color: #d1d5db;
            font-weight: 600; box-shadow: 0 1px 2px rgba(0,0,0,0.05);
        }
        .tab-btn:hover:not(.active) { background: #f3f4f6; }
        .action-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .search-box-custom { position: relative; width: 400px; }
        .search-box-custom input {
            width: 100%; padding: 10px 15px 10px 40px; border-radius: 8px;
            border: 1px solid #e5e7eb; outline: none; font-size: 14px;
        }
        .search-box-custom i { position: absolute; left: 15px; top: 50%; transform: translateY(-50%); color: #9ca3af; }
        .table-card {
            background: #fff; border-radius: 12px; padding: 25px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.02); border: 1px solid #f3f4f6;
        }
        .badge-disease { background: #d1fae5; color: #059669; padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: 600; }
        .modal-overlay {
            position: fixed; top: 0; left: 0; width: 100%; height: 100%;
            background: rgba(0,0,0,0.5); display: none; justify-content: center; align-items: center; z-index: 1000;
        }
        .modal-content { background: #fff; padding: 30px; border-radius: 12px; width: 500px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); }
        .modal-header h3 { margin: 0 0 20px 0; font-size: 18px; color: #111827; border-bottom: 1px solid #eee; padding-bottom: 10px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 8px; font-weight: 500; font-size: 14px; }
        .form-group input, .form-group textarea { width: 100%; padding: 10px; border: 1px solid #e5e7eb; border-radius: 8px; outline: none; }
        .modal-actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 20px; }
        .btn-cancel { background: #f3f4f6; color: #374151; border: none; padding: 10px 20px; border-radius: 8px; cursor: pointer; }
        .btn-submit { background: #10b981; color: white; border: none; padding: 10px 20px; border-radius: 8px; cursor: pointer; }
    </style>
</head>
<body>

    <aside class="sidebar">
        <div class="brand">
            <div class="brand-icon"><i class="fa-solid fa-leaf text-white"></i></div>
            <div class="brand-text"><h2>Healthy Food</h2><span>Dinh dưỡng thông minh</span></div>
        </div>
        <ul class="nav-menu">
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link"><i class="fa-solid fa-table-cells-large"></i> Tổng quan</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/users" class="nav-link"><i class="fa-regular fa-address-book"></i> Quản lý người dùng</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage_food" class="nav-link"><i class="fa-solid fa-utensils"></i> Quản lý món ăn</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage_ingredient" class="nav-link"><i class="fa-solid fa-apple-whole"></i> Quản lý nguyên liệu</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage-disease" class="nav-link active"><i class="fa-solid fa-heart-pulse"></i> Quản lý bệnh lý</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/settings.jsp" class="nav-link"><i class="fa-solid fa-gear"></i> Cài đặt</a></li>
        </ul>
        <div class="logout-box">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a>
        </div>
    </aside>

    <main class="main-content">
        <div class="page-title" style="margin-bottom: 25px;">
            <h1 style="margin:0; font-size: 24px; color:#111827;">Quản lý bệnh lý</h1>
            <p style="margin:5px 0 0 0; font-size: 14px; color:#6b7280;">Quản lý bệnh lý và đánh giá tương thích với món ăn</p>
        </div>

        <div class="tabs-container">
            <button class="tab-btn active" onclick="switchTab(event, 'disease-section')"><i class="fa-regular fa-heart"></i> Bệnh lý</button>
            <button class="tab-btn" onclick="switchTab(event, 'compatibility-section')"><i class="fa-solid fa-link"></i> Tương thích</button>
        </div>

        <div id="disease-section" class="tab-content" style="display: block;">
            <div class="action-header">
                <div class="search-box-custom">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <input type="text" placeholder="Tìm kiếm bệnh lý...">
                </div>
                <button class="btn-add" onclick="openAddModal()" style="background: #10b981; color: white; padding: 10px 20px; border-radius: 8px; border: none; font-weight: 500; cursor: pointer;">
                    + Thêm bệnh lý
                </button>
            </div>

            <div class="table-card">
                <h3 style="margin-top: 0; margin-bottom: 20px; font-size: 16px; color: #111827;">Danh sách bệnh lý (${diseaseList != null ? diseaseList.size() : 0})</h3>
                <table class="custom-table" style="width: 100%; text-align: left; border-collapse: collapse;">
                    <thead>
                        <tr style="border-bottom: 1px solid #f3f4f6;">
                            <th style="padding: 15px 10px;">Tên bệnh lý</th>
                            <th style="padding: 15px 10px;">Mô tả chi tiết</th>
                            <th style="padding: 15px 10px; text-align: center;">Số món ăn</th>
                            <th style="padding: 15px 10px; text-align: center;">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${diseaseList}" var="d">
                            <tr style="border-bottom: 1px solid #f3f4f6;">
                                <td style="padding: 15px 10px; font-weight: 500;">${d.name}</td>
                                <td style="padding: 15px 10px; color: #6b7280;">${d.description}</td>
                                <td style="padding: 15px 10px; text-align: center;"><span class="badge-disease">${d.foodCount}</span></td>
                                <td style="padding: 15px 10px; text-align: center;">
                                    <a href="javascript:void(0);" onclick="openEditModal('${d.id}', '${d.name}', '${d.description}')" style="color: #6b7280; margin-right: 10px;"><i class="fa-regular fa-pen-to-square"></i></a>
                                    <a href="${pageContext.request.contextPath}/admin/manage-disease?action=delete&id=${d.id}" onclick="return confirm('Bạn có chắc chắn muốn xóa không?');" style="color: #ef4444;"><i class="fa-regular fa-trash-can"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div id="compatibility-section" class="tab-content" style="display: none;">
            
            <c:if test="${param.error == 'duplicate_rating'}">
                <div id="error-alert" style="background-color: #fee2e2; color: #ef4444; padding: 12px; border-radius: 8px; margin-bottom: 20px; border: 1px solid #fecaca; display: flex; align-items: center; gap: 10px; font-weight: 500;">
                    <i class="fa-solid fa-circle-exclamation"></i>
                    <span>Quy tắc tương thích cho cặp dữ liệu này đã được thiết lập!</span>
                </div>
            </c:if>

            <div class="action-header">
                <span style="color: #4b5563; font-size: 14px;">Đánh giá mức độ tương thích giữa món ăn và bệnh lý</span>
                <button class="btn-add" onclick="openRatingModal()" style="background: #15803d; color: white; padding: 10px 20px; border-radius: 8px; border: none; font-weight: 500; cursor: pointer;">
                    + Thêm đánh giá
                </button>
            </div>

            <div class="table-card">
                <table class="custom-table" style="width: 100%; text-align: left; border-collapse: collapse;">
                    <thead>
                        <tr style="border-bottom: 1px solid #f3f4f6;">
                            <th style="padding: 15px 10px;">Món ăn</th>
                            <th style="padding: 15px 10px;">Bệnh lý</th>
                            <th style="padding: 15px 10px;">Mức độ tương thích</th>
                            <th style="padding: 15px 10px; text-align: center;">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${compatibilityList}" var="item">
                            <tr style="border-bottom: 1px solid #f3f4f6;">
                                <td style="padding: 15px 10px; font-weight: 500;">${item.foodName}</td>
                                <td style="padding: 15px 10px;"><span style="background-color: #e0f2fe; color: #0284c7; padding: 4px 10px; border-radius: 20px; font-size: 13px;">${item.diseaseName}</span></td>
                                <td style="padding: 15px 10px;">
                                    <span style="color: #f59e0b; font-size: 18px; letter-spacing: 2px;">
                                        <c:forEach begin="1" end="5" var="i">
                                            <c:choose><c:when test="${i <= item.rating}">★</c:when><c:otherwise>☆</c:otherwise></c:choose>
                                        </c:forEach>
                                    </span>
                                </td>
                                <td style="padding: 15px 10px; text-align: center;">
                                    <a href="manage-disease?action=deleteRating&id=${item.id}" class="btn-sm" style="color: #ef4444; text-decoration: none;" onclick="return confirm('Xóa liên kết này?');"><i class="fas fa-trash"></i> Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </main>

    <div class="modal-overlay" id="addDiseaseModal">
        <div class="modal-content">
            <div class="modal-header"><h3>Thêm bệnh lý mới</h3></div>
            <form action="${pageContext.request.contextPath}/admin/manage-disease" method="POST" id="addForm">
                <input type="hidden" name="action" value="add">
                <div class="form-group"><label>Tên bệnh lý</label><input type="text" name="diseaseName" required></div>
                <div class="form-group"><label>Mô tả</label><textarea name="diseaseDescription" rows="4" required></textarea></div>
                <div class="modal-actions">
                    <button type="button" class="btn-cancel" onclick="closeAddModal()">Hủy</button>
                    <button type="submit" class="btn-submit">Lưu</button>
                </div>
            </form>
        </div>
    </div>

    <div class="modal-overlay" id="editDiseaseModal">
        <div class="modal-content">
            <div class="modal-header"><h3>Sửa bệnh lý</h3></div>
            <form action="${pageContext.request.contextPath}/admin/manage-disease" method="POST" id="editForm">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="diseaseId" id="editDiseaseId">
                <div class="form-group"><label>Tên bệnh lý</label><input type="text" name="diseaseName" id="editDiseaseName" required></div>
                <div class="form-group"><label>Mô tả</label><textarea name="diseaseDescription" id="editDiseaseDescription" rows="4" required></textarea></div>
                <div class="modal-actions">
                    <button type="button" class="btn-cancel" onclick="closeEditModal()">Hủy</button>
                    <button type="submit" class="btn-submit">Cập nhật</button>
                </div>
            </form>
        </div>
    </div>

    <div class="modal-overlay" id="addRatingModal">
        <div class="modal-content">
            <div class="modal-header"><h3>Thêm Đánh Giá Mới</h3></div>
            <form action="${pageContext.request.contextPath}/admin/manage-disease" method="POST" id="addRatingForm">
                <input type="hidden" name="action" value="addRating">
                <div class="form-group">
                    <label>Chọn món ăn:</label>
                    <select name="foodId" required style="width:100%; padding:10px; border-radius:8px; border:1px solid #ccc;">
                        <c:forEach items="${foodList}" var="food"><option value="${food.id}">${food.name}</option></c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Bệnh lý:</label>
                    <select name="diseaseId" required style="width:100%; padding:10px; border-radius:8px; border:1px solid #ccc;">
                        <c:forEach items="${diseaseList}" var="disease"><option value="${disease.id}">${disease.name}</option></c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Số sao:</label>
                    <select name="rating" required style="width:100%; padding:10px; border-radius:8px; border:1px solid #ccc;">
                        <option value="5">5 sao</option><option value="4">4 sao</option><option value="3">3 sao</option><option value="2">2 sao</option><option value="1">1 sao</option>
                    </select>
                </div>
                <div class="modal-actions">
                    <button type="button" class="btn-cancel" onclick="closeRatingModal()">Hủy</button>
                    <button type="submit" class="btn-submit">Lưu</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function switchTab(evt, sectionName) {
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tab-content");
            for (i = 0; i < tabcontent.length; i++) tabcontent[i].style.display = "none";
            tablinks = document.getElementsByClassName("tab-btn");
            for (i = 0; i < tablinks.length; i++) tablinks[i].className = tablinks[i].className.replace(" active", "");
            document.getElementById(sectionName).style.display = "block";
            if(evt) evt.currentTarget.className += " active";
        }

        function openAddModal() { document.getElementById('addDiseaseModal').style.display = 'flex'; }
        function closeAddModal() { document.getElementById('addForm').reset(); document.getElementById('addDiseaseModal').style.display = 'none'; }
        
        // HÀM MỞ MODAL SỬA - ĐÃ KHÔI PHỤC
        function openEditModal(id, name, desc) {
            document.getElementById('editDiseaseId').value = id;
            document.getElementById('editDiseaseName').value = name;
            document.getElementById('editDiseaseDescription').value = desc;
            document.getElementById('editDiseaseModal').style.display = 'flex';
        }
        function closeEditModal() { document.getElementById('editDiseaseModal').style.display = 'none'; }

        function openRatingModal() { document.getElementById('addRatingModal').style.display = 'flex'; }
        function closeRatingModal() { document.getElementById('addRatingForm').reset(); document.getElementById('addRatingModal').style.display = 'none'; }

        window.onload = function() {
            const urlParams = new URLSearchParams(window.location.search);
            if (urlParams.get('error') === 'duplicate_rating' || urlParams.get('tab') === 'compatibility') {
                const compBtn = document.querySelectorAll('.tab-btn')[1];
                switchTab(null, 'compatibility-section');
                compBtn.className += " active";
                document.querySelectorAll('.tab-btn')[0].className = document.querySelectorAll('.tab-btn')[0].className.replace(" active", "");
            }

            const errorAlert = document.getElementById('error-alert');
            if (errorAlert) {
                setTimeout(() => {
                    errorAlert.style.transition = "opacity 0.5s ease";
                    errorAlert.style.opacity = "0";
                    setTimeout(() => errorAlert.remove(), 500);
                }, 5000);
            }
        };
    </script>
</body>
</html>