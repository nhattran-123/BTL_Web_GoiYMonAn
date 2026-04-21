<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tìm kiếm món ăn</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
    
    <style>
        /* CSS tuỳ chỉnh cho trang Tìm kiếm bám sát thiết kế Figma */
        .search-header { margin-bottom: 25px; }
        .search-header h1 { font-size: 26px; font-weight: bold; color: #333; margin-bottom: 8px; }
        .search-header .sub-text { color: #888; font-size: 15px; }

        /* Khung thanh tìm kiếm */
        .search-bar-wrapper { display: flex; gap: 15px; margin-bottom: 15px; align-items: center; }
        .search-input-box { 
            flex: 1; display: flex; align-items: center; background: white; 
            border: 1px solid #e0e0e0; border-radius: 8px; padding: 8px 15px; 
        }
        .search-input-box i.fa-magnifying-glass { color: #888; margin-right: 12px; font-size: 16px; }
        .search-input-box input { border: none; outline: none; width: 100%; font-size: 15px; color: #333; }
        .btn-clear { color: #bbb; text-decoration: none; font-size: 16px; margin-left: 10px; }
        .btn-clear:hover { color: #333; }
        
        .btn-filter { 
            background: white; border: 1px solid #e0e0e0; border-radius: 8px; 
            padding: 10px 20px; cursor: pointer; font-weight: 600; color: #333; 
            display: flex; align-items: center; gap: 8px; font-size: 14px;
        }

        .result-count { font-size: 12px; color: #888; margin-bottom: 20px; }

        /* Lưới thẻ món ăn */
        .grid-search { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; }
        .food-card { 
            background: white; border-radius: 12px; overflow: hidden; 
            border: 1px solid #eee; text-decoration: none; color: inherit; display: block;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .food-card:hover { transform: translateY(-3px); box-shadow: 0 6px 15px rgba(0,0,0,0.08); }
        
        .img-wrapper { position: relative; height: 180px; }
        .img-wrapper img { width: 100%; height: 100%; object-fit: cover; }
        
        /* Các icon trên ảnh */
        .icon-heart { 
            position: absolute; top: 10px; left: 10px; background: rgba(255,255,255,0.9); 
            width: 28px; height: 28px; border-radius: 50%; display: flex; 
            justify-content: center; align-items: center; color: #aaa; font-size: 13px; 
        }
        .badge-safe { 
            position: absolute; top: 10px; right: 10px; background: #28a745; 
            color: white; padding: 4px 8px; border-radius: 6px; font-size: 10px; 
            font-weight: bold; display: flex; align-items: center; gap: 4px; 
        }
        
        /* Thông tin món ăn */
        .card-info { padding: 15px; }
        .card-info h3 { font-size: 15px; font-weight: 700; margin: 0 0 6px 0; color: #333; }
        .card-info .calo { font-size: 12px; color: #888; margin-bottom: 12px; }
        .card-info .match { font-size: 12px; font-weight: 700; color: #28a745; display: flex; align-items: center; gap: 4px; }
        
        .empty-state { grid-column: 1 / -1; text-align: center; padding: 60px 0; color: #888; }
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
            <li class="nav-item"><a href="${pageContext.request.contextPath}/home" class="nav-link"><i class="fa-solid fa-house"></i> Trang chủ</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/profile" class="nav-link"><i class="fa-regular fa-user"></i> Hồ sơ sức khỏe</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/foods" class="nav-link"><i class="fa-solid fa-utensils"></i> Món ăn</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/meal_plan" class="nav-link"><i class="fa-regular fa-calendar-days"></i> Thực đơn</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/progress" class="nav-link"><i class="fa-solid fa-chart-line"></i> Tiến trình</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/favorites" class="nav-link"><i class="fa-solid fa-heart"></i> Yêu thích</a></li>
            
            <li class="nav-item"><a href="${pageContext.request.contextPath}/search" class="nav-link active"><i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm</a></li>
            
            <li class="nav-item"><a href="${pageContext.request.contextPath}/settings" class="nav-link"><i class="fa-solid fa-gear"></i> Cài đặt</a></li>
        </ul>
        <div class="logout-box">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a>
        </div>
    </aside>

    <main class="main-content">
        <div class="search-header">
            <h1>Tìm kiếm món ăn</h1>
            <p class="sub-text">Tìm kiếm món ăn phù hợp với sức khỏe của bạn</p>
        </div>

        <form action="${pageContext.request.contextPath}/search" method="get" class="search-bar-wrapper">
            <div class="search-input-box">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" name="txtSearch" value="${saveKeyword}" placeholder="Tìm kiếm món ăn....">
                
                <c:if test="${not empty saveKeyword}">
                    <a href="${pageContext.request.contextPath}/search" class="btn-clear" title="Xóa từ khóa"><i class="fa-solid fa-circle-xmark"></i></a>
                </c:if>
            </div>
            <button type="button" class="btn-filter"><i class="fa-solid fa-sliders"></i> Tìm Kiếm</button>
        </form>

        <div class="result-count">Tìm thấy <c:out value="${listF != null ? listF.size() : 0}"/> món ăn</div>

        <div class="grid-search">
            <c:choose>
                <c:when test="${not empty listF}">
                    <c:forEach items="${listF}" var="f">
                        <a href="${pageContext.request.contextPath}/food-detail?id=${f.food_id}" class="food-card">
                            <div class="img-wrapper">
                                <img src="${pageContext.request.contextPath}/assets/images/${f.image_url}" alt="${f.food_name}" onerror="this.onerror=null; this.src='https://via.placeholder.com/300x200?text=No+Image'">
                                <div class="icon-heart"><i class="fa-regular fa-heart"></i></div>
                                 <div class="badge-safe"><i class="fa-solid fa-shield-check"></i> ${f.allergyConflictCount == 0 ? "An toàn" : "Có dị ứng"}</div>
                            </div>
                            <div class="card-info">
                                <div class="calo"><fmt:formatNumber value="${f.calories}" maxFractionDigits="0"/> calo</div>
                                <div class="match"><i class="fa-solid fa-star"></i> <fmt:formatNumber value="${f.suitabilityScore}" maxFractionDigits="0"/>% phù hợp</div>
                            </div>
                        </a>
                    </c:forEach>
                </c:when>
                
                <c:otherwise>
                    <div class="empty-state">
                        <i class="fa-regular fa-face-frown" style="font-size: 50px; margin-bottom: 15px; color: #ddd;"></i>
                        <h3 style="color: #555;">Không tìm thấy món ăn nào</h3>
                        <p>Không có kết quả đối sánh cho từ khóa "<strong>${saveKeyword}</strong>". Vui lòng thử lại!</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
</body>
</html>