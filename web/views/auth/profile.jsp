<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<head>
    <meta charset="UTF-8">
    <title>Hồ sơ sức khỏe - Healthy Food</title>
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/profile.css">
    
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

    <style>
        /* CSS bổ sung để Select2 đẹp hơn trong Profile Card */
        .select2-container--default .select2-selection--multiple {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 5px;
            min-height: 45px;
        }
        .select2-container--default.select2-container--focus .select2-selection--multiple {
            border-color: #10b981;
        }
        .btn-save-wrapper {
            margin-top: 30px;
            text-align: right;
            padding-bottom: 50px;
        }
        .form-section-title {
            margin-bottom: 15px;
            font-weight: 600;
            color: #374151;
            display: block;
        }
    </style>
</head>
<body>

    <aside class="sidebar">
        <div class="brand">
            <div class="brand-icon"><i class="fa-solid fa-leaf text-white"></i></div>
            <div class="brand-text">
                <h2>Healthy Food</h2>
                <span>Dinh dưỡng thông minh</span>
            </div>
        </div>
        <ul class="nav-menu">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/home" class="nav-link">
                    <i class="fa-solid fa-house"></i> Trang chủ
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/profile" class="nav-link active">
                    <i class="fa-regular fa-user"></i> Hồ sơ sức khỏe
                </a>
            </li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/foods" class="nav-link"><i class="fa-solid fa-utensils"></i> Món ăn</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-regular fa-calendar-days"></i> Thực đơn</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-chart-line"></i> Tiến trình</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-regular fa-heart"></i> Yêu thích</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/settings" class="nav-link"><i class="fa-solid fa-gear"></i> Cài đặt</a></li>
        </ul>
        <div class="logout-box">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a>
        </div>
    </aside>

    <main class="main-content">
        <div class="page-title">
            <h1>Hồ sơ sức khỏe</h1>
            <p>Cập nhật thông tin để nhận gợi ý món ăn phù hợp</p>
        </div>

        <form action="${pageContext.request.contextPath}/update-profile" method="POST">
            
            <div class="profile-card">
                <div class="card-header">
                    <div class="card-icon"><i class="fa-regular fa-user"></i></div>
                    <div>
                        <h3>Thông tin cơ bản</h3>
                        <p>Thông tin cá nhân của bạn</p>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>Giới tính</label>
                        <select name="gender" class="form-control">
                            <option value="Nam" ${sessionScope.currentUser.gender == 'Nam' ? 'selected' : ''}>Nam</option>
                            <option value="Nữ" ${sessionScope.currentUser.gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Tuổi</label>
                        <input type="number" name="age" class="form-control" value="${sessionScope.currentUser.age}">
                    </div>
                </div>
            </div>

            <div class="profile-card">
                <div class="card-header">
                    <div class="card-icon"><i class="fa-solid fa-scale-balanced"></i></div>
                    <div>
                        <h3>Chỉ số cơ thể</h3>
                        <p>Cân nặng và chiều cao hiện tại & mục tiêu</p>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>Cân nặng hiện tại (kg)</label>
                        <input type="number" name="weight" step="0.1" class="form-control" value="${sessionScope.currentUser.weight}">
                    </div>
                    <div class="form-group">
                        <label>Chiều cao hiện tại (cm)</label>
                        <input type="number" name="height" step="0.1" class="form-control" value="${sessionScope.currentUser.height}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>Cân nặng mong muốn (kg)</label>
                        <input type="number" name="desired_weight" step="0.1" class="form-control" value="${sessionScope.currentUser.desired_weight}">
                    </div>
                    <div class="form-group">
                        <label>Chiều cao mong muốn (cm)</label>
                        <input type="number" name="desired_height" step="0.1" class="form-control" value="${sessionScope.currentUser.desired_height}">
                    </div>
                </div>
            </div>

            <div class="profile-card">
                <div class="card-header">
                    <div class="card-icon"><i class="fa-solid fa-heart-pulse"></i></div>
                    <div>
                        <h3>Tình trạng sức khỏe</h3>
                        <p>Tìm kiếm và chọn các bệnh lý bạn đang mắc phải</p>
                    </div>
                </div>
                <div style="padding: 10px 20px;">
                    <label class="form-section-title">Danh sách bệnh lý</label>
                    <select name="disease_id" class="searchable-dropdown" multiple="multiple" style="width: 100%;">
                        <c:forEach items="${listDisease}" var="d">
                            <option value="${d.id}" 
                                <c:forEach items="${userDiseaseIds}" var="uId">
                                    <c:if test="${uId == d.id}">selected</c:if>
                                </c:forEach>
                            >${d.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="profile-card">
                <div class="card-header">
                    <div class="card-icon" style="background: #fee2e2; color: #ef4444;"><i class="fa-solid fa-shield-halved"></i></div>
                    <div>
                        <h3>Dị ứng thực phẩm</h3>
                        <p>Chọn các nguyên liệu bạn không thể ăn</p>
                    </div>
                </div>
                <div style="padding: 10px 20px;">
                    <label class="form-section-title">Nguyên liệu dị ứng</label>
                    <select name="allergy_id" class="searchable-dropdown" multiple="multiple" style="width: 100%;">
                        <c:forEach items="${listIngredient}" var="i">
                            <option value="${i.id}" 
                                <c:forEach items="${userAllergyIds}" var="uaId">
                                    <c:if test="${uaId == i.id}">selected</c:if>
                                </c:forEach>
                            >${i.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="btn-save-wrapper">
                <button type="submit" class="btn-save"><i class="fa-solid fa-floppy-disk"></i> Lưu hồ sơ sức khỏe</button>
            </div>
        </form>
    </main>

    <script>
        $(document).ready(function() {
            // Khởi tạo Select2 cho cả 2 ô chọn
            $('.searchable-dropdown').select2({
                placeholder: "Gõ để tìm kiếm (ví dụ: Tiểu đường, Tôm, ...)",
                allowClear: true,
                language: {
                    noResults: function() {
                        return "Không tìm thấy dữ liệu phù hợp";
                    }
                }
            });
        });
    </script>
</body>
</html>