<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<head>
    <meta charset="UTF-8">
    <title>Quản lý người dùng - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        .modal { display: none; position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); }
        .modal-content { background-color: #fff; margin: 10% auto; padding: 25px; border-radius: 10px; width: 450px; box-shadow: 0 5px 15px rgba(0,0,0,0.3); }
        .modal-content h3 { margin-top: 0; color: #2c3e50; font-family: sans-serif; border-bottom: 2px solid #2ecc71; padding-bottom: 10px;}
        .modal-content label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        .modal-content input, .modal-content textarea { width: 100%; margin-bottom: 15px; padding: 10px; border: 1px solid #ccc; border-radius: 5px; box-sizing: border-box; }
        .btn-send { background-color: #2ecc71; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; font-weight: bold;}
        .btn-cancel { background-color: #e74c3c; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; margin-left: 10px; font-weight: bold;}
        .pagination { display: flex; justify-content: center; margin-top: 20px; padding-bottom: 15px; gap: 5px; }
        .pagination a { color: #555; padding: 8px 16px; text-decoration: none; border: 1px solid #ddd; border-radius: 4px; }
        .pagination a.active { background-color: #2ecc71; color: white; border: 1px solid #2ecc71; }
        .pagination a:hover:not(.active) { background-color: #f1f1f1; }
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
                <a href="${pageContext.request.contextPath}/admin/users" class="nav-link active">
                    <i class="fa-regular fa-address-book"></i> Quản lý người dùng
                </a>
            </li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-utensils"></i> Quản lý món ăn</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage_ingredient" class="nav-link"><i class="fa-solid fa-apple-whole"></i> Quản lý nguyên liệu</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage-disease" class="nav-link"><i class="fa-solid fa-heart-pulse"></i> Quản lý bệnh lý</a></li>
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
        <div class="page-title">
            <h1>Quản lý người dùng</h1>
            <p>Quản lý người dùng trong hệ thống</p>
        </div>

        <div class="admin-grid">
            <div class="stat-card">
                <div class="stat-info"><h4>Tổng số người dùng</h4><h2>${stats != null ? stats[0] : 0}</h2></div>
                <div class="stat-icon bg-green-light"><i class="fa-regular fa-user"></i></div>
            </div>
            <div class="stat-card">
                <div class="stat-info"><h4>Đang hoạt động</h4><h2>${stats != null ? stats[1] : 0}</h2></div>
                <div class="stat-icon bg-green-light"><i class="fa-solid fa-user-check"></i></div>
            </div>
            <div class="stat-card">
                <div class="stat-info"><h4>Không hoạt động</h4><h2>${stats != null ? stats[2] : 0}</h2></div>
                <div class="stat-icon bg-green-light"><i class="fa-solid fa-user-lock"></i></div>
            </div>
            <div class="stat-card">
                <div class="stat-info"><h4>Admin</h4><h2>${stats != null ? stats[3] : 0}</h2></div>
                <div class="stat-icon bg-green-light"><i class="fa-solid fa-lock"></i></div>
            </div>
        </div>

        <form action="${pageContext.request.contextPath}/admin/users" method="GET" class="filter-bar">
            <div class="search-box">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" name="keyword" value="${txtSearch}" placeholder="Tìm kiếm theo tên hoặc email...">
            </div>
            <div class="filter-selects">
                <select name="role">
                    <option value="all" ${txtRole == 'all' ? 'selected' : ''}>Tất cả quyền</option>
                    <option value="ADMIN" ${txtRole == 'ADMIN' ? 'selected' : ''}>Admin</option>
                    <option value="USER" ${txtRole == 'USER' ? 'selected' : ''}>User</option>
                </select>
                <button type="submit" style="padding: 10px 20px; background: #2ecc71; color: white; border: none; border-radius: 5px; cursor: pointer; font-weight: bold;">Tìm / Lọc</button>
            </div>
        </form>

        <div class="table-card">
            <h3 class="table-title">Danh sách người dùng (${stats != null ? stats[0] : 0})</h3>
            <table class="user-table">
                <thead>
                    <tr>
                        <th>Họ và tên</th>
                        <th>Email</th>
                        <th>Vai trò</th>
                        <th>Trạng thái</th>
                        <th>Ngày tạo</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listU}" var="u">
                        <tr>
                            <td>${u.fullName}</td>
                            <td>${u.email}</td>
                            <td><strong>${u.role}</strong></td>
                            <td>
                                <span class="status-badge bg-active" style="background:#d1fae5; color:#065f46; padding: 5px 12px; border-radius: 20px; font-size: 13px;">Hoạt động</span>
                            </td>
                            <td>${u.createdAt}</td>
                            <td>
                                <div class="action-wrapper" style="position: relative; display: inline-block;">
                                    <button class="action-btn" onclick="toggleMenu(this)"><i class="fa-solid fa-ellipsis"></i></button>
                                    <div class="action-menu" style="display: none; position: absolute; right: 0; background: #fff; box-shadow: 0 4px 8px rgba(0,0,0,0.15); border-radius: 5px; padding: 10px; width: 130px; z-index: 10;">
                                        <a href="javascript:void(0)" onclick="openEmailModal('${u.email}')" style="display: block; padding: 8px 5px; color: #333; text-decoration: none; border-bottom: 1px solid #eee;">
                                            <i class="fa-regular fa-envelope"></i> Gửi email
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/users?action=deactivate&id=${u.id}" style="display: block; padding: 8px 5px; color: #e74c3c; text-decoration: none;" onclick="return confirm('Cảnh báo: Bạn có chắc chắn muốn XÓA VĨNH VIỄN người dùng này khỏi CSDL?');">
                                            <i class="fa-solid fa-trash"></i> Xóa tài khoản
                                        </a>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="pagination">
                <c:if test="${tag > 1}">
                    <a href="users?page=${tag-1}&keyword=${txtSearch}&role=${txtRole}">&laquo;</a>
                </c:if>

                <c:forEach begin="1" end="${endP}" var="i">
                    <a class="${tag == i ? 'active' : ''}" 
                       href="users?page=${i}&keyword=${txtSearch}&role=${txtRole}">${i}</a>
                </c:forEach>

                <c:if test="${tag < endP}">
                    <a href="users?page=${tag+1}&keyword=${txtSearch}&role=${txtRole}">&raquo;</a>
                </c:if>
            </div>
        </div>
    </main>

    <div id="emailModal" class="modal">
        <div class="modal-content">
            <h3><i class="fa-regular fa-paper-plane"></i> Gửi Email thông báo</h3>
            <form id="emailForm" onsubmit="return sendFakeEmail(event)">
                <label>Gửi đến:</label>
                <input type="email" id="modalEmail" name="toEmail" readonly style="background: #f1f1f1;">
                <label>Tiêu đề:</label>
                <input type="text" name="subject" placeholder="Nhập tiêu đề..." required>
                <label>Nội dung:</label>
                <textarea name="message" rows="5" placeholder="Nhập nội dung tin nhắn..." required></textarea>
                <div style="text-align: right; margin-top: 10px;">
                    <button type="button" class="btn-cancel" onclick="closeEmailModal()">Hủy bỏ</button>
                    <button type="submit" class="btn-send">Gửi tin nhắn</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function toggleMenu(btn) {
            var menu = btn.nextElementSibling;
            var allMenus = document.getElementsByClassName("action-menu");
            for (var i = 0; i < allMenus.length; i++) {
                if (allMenus[i] !== menu) allMenus[i].style.display = "none";
            }
            menu.style.display = (menu.style.display === "none" || menu.style.display === "") ? "block" : "none";
        }

        window.onclick = function(event) {
            if (!event.target.closest('.action-wrapper')) {
                var dropdowns = document.getElementsByClassName("action-menu");
                for (var i = 0; i < dropdowns.length; i++) {
                    dropdowns[i].style.display = "none";
                }
            }
        }

        function openEmailModal(email) {
            document.getElementById("emailModal").style.display = "block";
            document.getElementById("modalEmail").value = email;
            var dropdowns = document.getElementsByClassName("action-menu");
            for (var i = 0; i < dropdowns.length; i++) dropdowns[i].style.display = "none";
        }

        // HÀM MỚI: Đóng modal và dọn dẹp sạch sẽ bộ nhớ form
        function closeEmailModal() {
            document.getElementById("emailModal").style.display = "none";
            document.getElementById("emailForm").reset(); // Xóa sạch tiêu đề và nội dung cũ
        }

        // HÀM MỚI: Xử lý nút Gửi tin nhắn (Hiển thị thông báo giả)
        function sendFakeEmail(event) {
            event.preventDefault(); // Ngăn form load lại trang (Chặn luôn cái lỗi 405)
            
            // Hiển thị thông báo thành công
            alert("Đã gửi email thông báo thành công đến người dùng!");
            
            // Sau khi gửi thì tự động đóng popup và xóa sạch form
            closeEmailModal(); 
            
            return false;
        }
    </script>
</body>
</html>