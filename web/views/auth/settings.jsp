<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<head>
    <meta charset="UTF-8">
    <title>Cài đặt tài khoản - Healthy Food</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/settings.css">
</head>
<body>

    <aside class="sidebar">
        <div class="brand">
            <div class="brand-icon"><i class="fa-solid fa-leaf text-white"></i></div>
            <div class="brand-text"><h2>Healthy Food</h2><span>Dinh dưỡng thông minh</span></div>
        </div>
        <ul class="nav-menu">
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/home" class="nav-link">
            <i class="fa-solid fa-house"></i> Trang chủ
        </a>
    </li>
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/profile" class="nav-link">
            <i class="fa-regular fa-user"></i> Hồ sơ sức khỏe
        </a>
    </li>
    <li class="nav-item"><a href="${pageContext.request.contextPath}/foods" class="nav-link"><i class="fa-solid fa-utensils"></i> Món ăn</a></li>
    <li class="nav-item"><a href="${pageContext.request.contextPath}/meal_plan" class="nav-link"><i class="fa-regular fa-calendar-days"></i> Thực đơn</a></li>
    <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-chart-line"></i> Tiến trình</a></li>
    <li class="nav-item"><a href="#" class="nav-link"><i class="fa-regular fa-heart"></i> Yêu thích</a></li>
    <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm</a></li>
    
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/settings" class="nav-link active">
            <i class="fa-solid fa-gear"></i> Cài đặt
        </a>
    </li>
</ul>
        <div class="logout-box"><a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a></div>
    </aside>

    <main class="main-content">
        <div class="page-title">
            <h1>Cài đặt tài khoản</h1>
            <p>Quản lý thông tin tài khoản của bạn</p>
        </div>

        <div class="settings-container">
            
            <form action="${pageContext.request.contextPath}/settings" method="POST">
                <input type="hidden" name="action" value="updateInfo">
                
                <div class="setting-card">
                    <div class="card-top">
                        <div class="card-title-box">
                            <div class="icon-box"><i class="fa-regular fa-user"></i></div>
                            <div>
                                <h3>Thông tin cá nhân</h3>
                                <p>Cập nhật tên và email</p>
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

            <form action="${pageContext.request.contextPath}/settings" method="POST">
                <input type="hidden" name="action" value="changePassword">
                
                <div class="setting-card">
                    <div class="card-top" style="margin-bottom: 25px;">
                        <div class="card-title-box">
                            <div class="icon-box"><i class="fa-solid fa-lock"></i></div>
                            <div>
                                <h3>Đổi mật khẩu</h3>
                                <p>Đảm bảo mật khẩu của bạn được bảo mật</p>
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