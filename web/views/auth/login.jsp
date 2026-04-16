<%-- 
    Document   : login
    Created on : Apr 3, 2026, 11:13:54 PM
    Author     : DINH THE LINH
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập - Hệ thống Sức khỏe</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
</head>
<body>

    <div class="login-container">
        <div class="brand" style="justify-content: center; margin-bottom: 32px; padding: 0; display: flex; align-items: center; gap: 12px;">
    <div class="brand-icon" style="background-color: #10b981; width: 45px; height: 45px; border-radius: 8px; display: flex; align-items: center; justify-content: center; color: white; font-size: 20px;">
        <i class="fa-solid fa-leaf"></i>
    </div>
</div>

        <h1 class="login-title">Đăng Nhập</h1>
        <p class="login-subtitle">Nhập email và mật khẩu để tiếp tục</p>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <c:if test="${not empty requestScope.success}">
                <div class="msg success-msg">${requestScope.success}</div>
            </c:if>
            <c:if test="${not empty requestScope.error}">
                <div class="error-msg">${requestScope.error}</div>
            </c:if>

            <div class="input-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="${requestScope.email}" placeholder="NguyenvanA@email.com" required>
            </div>

            <div class="input-group">
                <label for="password">Mật khẩu</label>
                <input type="password" id="password" name="password" placeholder="••••••••••" required>
            </div>

            <button type="submit" class="btn-login">Đăng nhập</button>
        </form>

        <div class="register-link">
            Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a>
        </div>
    </div>

</body>
</html>