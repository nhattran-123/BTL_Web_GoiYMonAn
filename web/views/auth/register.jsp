<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Ký - Hệ thống Sức khỏe</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/register.css">
    
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

    <style>
        /* Sửa một chút CSS để Select2 khớp với giao diện của Lĩnh */
        .select2-container--default .select2-selection--multiple {
            border: 1px solid #e2e8f0;
            border-radius: 8px;
            padding: 5px;
            min-height: 45px;
        }
        .select2-container--default.select2-container--focus .select2-selection--multiple {
            border-color: #10b981; /* Màu xanh lá giống theme của Lĩnh */
        }
        .error-border + .select2-container--default .select2-selection--multiple {
            border-color: #ef4444 !important;
        }
    </style>
</head>
<body>

    <div class="login-container" id="main-container" style="transition: all 0.3s; width: 400px;">
        
        <form action="${pageContext.request.contextPath}/register" method="post" id="regForm">
            
            <c:if test="${not empty requestScope.error}">
                <div class="msg error-msg">${requestScope.error}</div>
            </c:if>

            <div class="step-container active" id="step1">
                 <div class="brand" style="justify-content: center; margin-bottom: 32px; padding: 0; display: flex; align-items: center; gap: 12px;">
    <div class="brand-icon" style="background-color: #10b981; width: 45px; height: 45px; border-radius: 8px; display: flex; align-items: center; justify-content: center; color: white; font-size: 20px;">
        <i class="fa-solid fa-leaf"></i>
    </div>
</div>
                <h1 class="login-title">Đăng ký tài khoản</h1>
                <p class="login-subtitle">Tạo tài khoản mới để bắt đầu hành trình sức khỏe</p>

                <div class="input-group">
                    <label>Họ và tên</label>
                    <input type="text" id="fullName" name="fullName" value="${requestScope.fullName}" placeholder="Nguyễn văn A">
                </div>
                <div class="input-group">
                    <label>Email</label>
                    <input type="email" id="email" name="email" value="${requestScope.email}" placeholder="NguyenvanA@email.com">
                </div>
                <div class="input-group">
                    <label>Mật khẩu</label>
                    <input type="password" id="password" name="password" placeholder="............">
                </div>
                <div class="input-group">
                    <label>Xác nhận mật khẩu</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="............">
                    <div id="passError" class="error-text">Mật khẩu xác nhận không khớp!</div>
                </div>

                <button type="button" class="btn-login" onclick="nextStep(1)">Tiếp tục</button>
                <div class="register-link">Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></div>
            </div>

            <div class="step-container" id="step2">
                <div class="progress-bar"><div class="progress-step active"></div><div class="progress-step active"></div><div class="progress-step"></div><div class="progress-step"></div></div>
                <h1 class="login-title text-left">Chỉ số cơ thể</h1>
                <p class="login-subtitle text-left">Cho chúng tôi biết về bạn nhé</p>

                <div class="form-row">
                    <div class="input-group">
                        <label>Giới tính</label>
                        <select name="gender" class="form-control"><option value="Nam">Nam</option><option value="Nữ">Nữ</option></select>
                    </div>
                    <div class="input-group">
                        <label>Độ tuổi</label>
                        <input type="number" id="age" name="age" placeholder="30">
                    </div>
                </div>
                <div class="form-row">
                    <div class="input-group">
                        <label>Chiều cao (cm)</label>
                        <input type="number" id="height" name="height" placeholder="170">
                    </div>
                    <div class="input-group">
                        <label>Cân nặng (kg)</label>
                        <input type="number" id="weight" name="weight" placeholder="70">
                    </div>
                </div>
                <div class="btn-footer">
                    <button type="button" class="btn-back" onclick="changeStep(2, 1)">&#8592; Quay lại</button>
                    <button type="button" class="btn-next-step" onclick="nextStep(2)">Tiếp tục &#8594;</button>
                </div>
            </div>

            <div class="step-container" id="step3">
                <div class="progress-bar"><div class="progress-step active"></div><div class="progress-step active"></div><div class="progress-step active"></div><div class="progress-step"></div></div>
                <h1 class="login-title text-left">Mục tiêu sức khỏe</h1>
                <p class="login-subtitle text-left">Giúp chúng tôi tính toán chính xác hơn</p>

                <div class="input-group">
                    <label>Chiều cao mục tiêu (cm)</label>
                    <input type="number" id="targetHeight" name="desired_height" placeholder="180">
                </div>
                <div class="input-group">
                    <label>Cân nặng mục tiêu (kg)</label>
                    <input type="number" id="targetWeight" name="desired_weight" placeholder="65">
                </div>
                <div class="input-group">
                    <label>Mục tiêu</label>
                    <div class="radio-group">
                        <input type="radio" id="goal1" name="goal" value="Giảm cân"><label for="goal1">Giảm cân</label>
                        <input type="radio" id="goal2" name="goal" value="Duy trì" checked><label for="goal2">Duy trì</label>
                        <input type="radio" id="goal3" name="goal" value="Tăng cân"><label for="goal3">Tăng cân</label>
                    </div>
                </div>
                <div class="btn-footer">
                    <button type="button" class="btn-back" onclick="changeStep(3, 2)">&#8592; Quay lại</button>
                    <button type="button" class="btn-next-step" onclick="nextStep(3)">Tiếp tục &#8594;</button>
                </div>
            </div>

            <div class="step-container" id="step4">
                <div class="progress-bar"><div class="progress-step active"></div><div class="progress-step active"></div><div class="progress-step active"></div><div class="progress-step active"></div></div>
                <h1 class="login-title text-left">Dị ứng và bệnh lý</h1>
                <p class="login-subtitle text-left">Giúp chúng tôi gợi ý món ăn an toàn hơn (Không bắt buộc)</p>

                <div class="input-group">
                    <label>Dị ứng thực phẩm (Gõ để tìm kiếm)</label>
                    <select name="allergy_id" id="allergy-select" class="form-control searchable-dropdown" multiple="multiple" style="width: 100%;">
                        <c:forEach items="${listIngredient}" var="i">
                            <option value="${i.id}">${i.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="input-group" style="margin-top: 20px;">
                    <label>Bệnh lý (Gõ để tìm kiếm)</label>
                    <select name="disease_id" id="disease-select" class="form-control searchable-dropdown" multiple="multiple" style="width: 100%;">
                        <c:forEach items="${listDisease}" var="d">
                            <option value="${d.id}">${d.name}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="btn-footer" style="margin-top: 30px;">
                    <button type="button" class="btn-back" onclick="changeStep(4, 3)">&#8592; Quay lại</button>
                    <button type="submit" class="btn-next-step">Đăng ký &#8594;</button>
                </div>
            </div>

        </form>
    </div>

    <script>
        // --- 1. KHỞI TẠO SELECT2 SEARCH ---
        $(document).ready(function() {
            $('.searchable-dropdown').select2({
                placeholder: "Bắt đầu gõ để tìm...",
                allowClear: true,
                language: {
                    noResults: function() { return "Không tìm thấy dữ liệu!"; }
                }
            });
        });

        // --- 2. LOGIC CHUYỂN BƯỚC ---
        async function nextStep(currentStep) {
            let isValid = true;
            let fieldsToCheck = [];

            if (currentStep === 1) {
                fieldsToCheck = ['fullName', 'email', 'password', 'confirmPassword'];
            } else if (currentStep === 2) {
                fieldsToCheck = ['age', 'height', 'weight'];
            } else if (currentStep === 3) {
                fieldsToCheck = ['targetHeight', 'targetWeight'];
            }

            fieldsToCheck.forEach(id => {
                let el = document.getElementById(id);
                if (el.value.trim() === '') {
                    el.classList.add('error-border');
                    isValid = false;
                } else {
                    el.classList.remove('error-border');
                }
            });

            // Kiểm tra mật khẩu khớp
            if (isValid && currentStep === 1) {
                let pass = document.getElementById('password').value;
                let cPass = document.getElementById('confirmPassword').value;
                if (pass !== cPass) {
                    document.getElementById('confirmPassword').classList.add('error-border');
                    document.getElementById('passError').style.display = 'block';
                    isValid = false;
                } else {
                    document.getElementById('passError').style.display = 'none';
                }
                
                // Check email ngầm (AJAX) - Giữ nguyên logic của Lĩnh
                if (isValid) {
                    try {
                        let emailVal = document.getElementById('email').value;
                        let contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
                        let response = await fetch(contextPath + '/check-email?email=' + encodeURIComponent(emailVal));
                        let data = await response.json();
                        if (data.exists) {
                            document.getElementById('email').classList.add('error-border');
                            alert("❌ Email này đã tồn tại!");
                            isValid = false;
                        }
                    } catch (e) { console.error(e); }
                }
            }

            if (isValid) {
                changeStep(currentStep, currentStep + 1);
            }
        }

        function changeStep(current, next) {
            document.getElementById('step' + current).classList.remove('active');
            document.getElementById('step' + next).classList.add('active');
            
            if (next === 1) {
                document.getElementById('main-container').style.width = '400px';
            } else {
                document.getElementById('main-container').style.width = '480px';
            }
        }
    </script>
</body>
</html>