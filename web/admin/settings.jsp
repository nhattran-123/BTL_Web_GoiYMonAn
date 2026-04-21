<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Cài đặt Admin - Healthy Food</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/settings.css">
</head>
<body>

    <aside class="sidebar">
        <div class="brand">
            <div class="brand-icon"><i class="fa-solid fa-leaf text-white"></i></div>
            <div class="brand-text"><h2>Healthy Food</h2><span>Quản trị viên</span></div>
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
                <a href="${pageContext.request.contextPath}/admin/manage_food" class="nav-link">
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
                <a href="${pageContext.request.contextPath}/admin/settings.jsp" class="nav-link active">
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
            <h1>Cài đặt hệ thống</h1>
            <p>Quản lý thông tin tài khoản Quản trị viên</p>
        </div>

        <div class="settings-container">
            
            <form action="${pageContext.request.contextPath}/admin/settings" method="POST">
                <input type="hidden" name="action" value="updateInfo">
                
                <div class="setting-card">
                    <div class="card-top">
                        <div class="card-title-box">
                            <div class="icon-box"><i class="fa-regular fa-user"></i></div>
                            <div>
                                <h3>Thông tin cá nhân</h3>
                                <p>Cập nhật tên và email quản trị</p>
                            </div>
                        </div>
                        <button type="submit" class="btn-save-green"><i class="fa-regular fa-floppy-disk"></i> Lưu thay đổi</button>
                    </div>

                    <c:if test="${not empty successInfo}"><div class="alert alert-success">${successInfo}</div></c:if>
                    <c:if test="${not empty errorInfo}"><div class="alert alert-error">${errorInfo}</div></c:if>

                    <div class="form-row-custom">
                        <div class="form-group">
                            <label>Họ và Tên</label>
                            <input type="text" name="fullName" class="form-control-custom" value="${sessionScope.currentUser.fullName}" required>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" class="form-control-custom" value="${sessionScope.currentUser.email}" required>
                        </div>
                    </div>
                </div>
            </form>

            <form action="${pageContext.request.contextPath}/admin/settings" method="POST">
                <input type="hidden" name="action" value="changePassword">
                
                <div class="setting-card">
                    <div class="card-top" style="margin-bottom: 25px;">
                        <div class="card-title-box">
                            <div class="icon-box"><i class="fa-solid fa-lock"></i></div>
                            <div>
                                <h3>Đổi mật khẩu</h3>
                                <p>Đảm bảo an toàn cho tài khoản Quản trị viên</p>
                            </div>
                        </div>
                    </div>

                    <c:if test="${not empty successPass}"><div class="alert alert-success">${successPass}</div></c:if>
                    <c:if test="${not empty errorPass}"><div class="alert alert-error">${errorPass}</div></c:if>

                    <div class="form-group" style="margin-bottom: 15px;">
                        <label>Mật khẩu hiện tại</label>
                        <div class="input-with-icon">
                            <i class="fa-solid fa-unlock-keyhole"></i>
                            <input type="password" name="oldPassword" class="form-control-custom" placeholder="Nhập mật khẩu hiện tại" required autocomplete="off">
                        </div>
                    </div>

                    <div class="form-group" style="margin-bottom: 15px;">
                        <label>Mật khẩu mới</label>
                        <div class="input-with-icon">
                            <i class="fa-solid fa-lock"></i>
                            <input type="password" name="newPassword" class="form-control-custom" placeholder="Nhập mật khẩu mới" required autocomplete="new-password">
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Xác nhận mật khẩu mới</label>
                        <div class="input-with-icon">
                            <i class="fa-solid fa-lock"></i>
                            <input type="password" name="confirmPassword" class="form-control-custom" placeholder="Xác nhận mật khẩu mới" required autocomplete="new-password">
                        </div>
                    </div>

                    <div class="card-bottom-action">
                        <button type="submit" class="btn-save-green"><i class="fa-regular fa-floppy-disk"></i> Lưu thay đổi</button>
                    </div>
                </div>
            </form>

        </div>
    </main>

</body>
</html>