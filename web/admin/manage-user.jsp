<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý người dùng - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
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
                <a href="${pageContext.request.contextPath}/admin/users" class="nav-link active">
                    <i class="fa-regular fa-address-book"></i> Quản lý người dùng
                </a>
            </li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-utensils"></i> Quản lý món ăn</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-apple-whole"></i> Quản lý nguyên liệu</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-heart-pulse"></i> Quản lý bệnh lý</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-gear"></i> Cài đặt</a></li>
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
                <div class="stat-info"><h4>Tổng số người dùng</h4><h2>1,650</h2></div>
                <div class="stat-icon bg-green-light"><i class="fa-regular fa-user"></i></div>
            </div>
            <div class="stat-card">
                <div class="stat-info"><h4>Đang hoạt động</h4><h2>350</h2></div>
                <div class="stat-icon bg-green-light"><i class="fa-solid fa-user-check"></i></div>
            </div>
            <div class="stat-card">
                <div class="stat-info"><h4>Không hoạt động</h4><h2>1300</h2></div>
                <div class="stat-icon bg-green-light"><i class="fa-solid fa-user-lock"></i></div>
            </div>
            <div class="stat-card">
                <div class="stat-info"><h4>Admin</h4><h2>1</h2></div>
                <div class="stat-icon bg-green-light"><i class="fa-solid fa-lock"></i></div>
            </div>
        </div>

        <div class="filter-bar">
            <div class="search-box">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" placeholder="Tìm kiếm theo tên hoặc email...">
            </div>
            <div class="filter-selects">
                <select><option>Tất cả quyền</option><option>Admin</option><option>User</option></select>
                <select><option>Tất cả trạng thái</option><option>Hoạt động</option><option>Khóa</option></select>
            </div>
        </div>

        <div class="table-card">
            <h3 class="table-title">Danh sách người dùng (1650)</h3>
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
                    <tr>
                        <td>Nguyễn Văn A</td>
                        <td>NguyenA@gmail.com</td>
                        <td><select class="role-select"><option selected>User</option><option>Admin</option></select></td>
                        <td><span class="status-badge bg-active">Hoạt động</span></td>
                        <td>01-05-2024</td>
                        <td>
                            <div class="action-wrapper">
                                <button class="action-btn"><i class="fa-solid fa-ellipsis"></i></button>
                                <div class="action-menu">
                                    <a href="#">Thao tác</a>
                                    <a href="#">Gửi email</a>
                                    <a href="#" class="text-red">Vô hiệu hóa</a>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </tbody>
            </table>
        </div>
    </main>

</body>
</html>