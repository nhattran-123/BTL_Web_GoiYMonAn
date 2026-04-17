<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thực đơn - Healthy Food</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/meal_plan.css">
     <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
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
            <li class="nav-item"><a href="${pageContext.request.contextPath}/meal_plan" class="nav-link active"><i class="fa-regular fa-calendar-days"></i> Thực đơn</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-chart-line"></i> Tiến trình</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-regular fa-heart"></i> Yêu thích</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/settings" class="nav-link"><i class="fa-solid fa-gear"></i> Cài đặt</a></li>
        </ul>
        <div class="logout-box">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a>
        </div>
    </aside>

    <main class="main-content meal-page">
        <header class="meal-header">
            <div>
                <h1>Thực đơn</h1>
                <p>Dữ liệu hiển thị theo ngày bạn chọn từ cơ sở dữ liệu.</p>
            </div>
            <div class="calo-badge"><i class="fa-solid fa-fire-flame-curved"></i> <fmt:formatNumber value="${totalCalories}" minFractionDigits="2" maxFractionDigits="2" /> calo</div>
        </header>

        <section class="card slider-card">
            <a class="slider-nav" href="${pageContext.request.contextPath}/meal_plan?date=${weekSlider[0].date}">&lt;</a>
            <div class="week-track">
                <c:forEach var="d" items="${weekSlider}">
                    <a href="${pageContext.request.contextPath}/meal_plan?date=${d.date}" class="day-pill ${d.selected ? 'active' : ''}">
                        <span class="dow">${d.dow}</span>
                        <strong>${d.day}</strong>
                        <span class="dot ${d.hasMeals ? 'has-meal' : ''}"></span>
                    </a>
                </c:forEach>
            </div>
            <a class="slider-nav" href="${pageContext.request.contextPath}/meal_plan?date=${weekSlider[6].date}">&gt;</a>
        </section>

        <section class="card filter-card">
            <form method="get" action="${pageContext.request.contextPath}/meal_plan" class="date-form">
                <label for="date">Ngày thực đơn</label>
                <input id="date" type="date" name="date" value="${selectedDate}">
                <button type="submit" class="btn-secondary">Xem</button>
            </form>
        </section>

        <section class="meal-list">
            <c:forEach var="section" items="${mealSections}">
                <article class="card meal-card-item" data-meal-id="${section.mealTypeId}" data-meal-name="${section.mealName}" data-selected-food-ids="<c:forEach var='food' items='${section.foods}' varStatus='loop'>${food.foodId}<c:if test='${!loop.last}'>,</c:if></c:forEach>">
                    <div class="meal-row-head">
                        <div class="meal-title"><i class="fa-regular fa-sun"></i> ${section.mealName}</div>
                        <div class="meal-meta">
                            <fmt:formatNumber value="${section.usedCalories}" minFractionDigits="2" maxFractionDigits="2" />
                            /
                            <fmt:formatNumber value="${section.targetCalories}" minFractionDigits="2" maxFractionDigits="2" /> calo
                            <button type="button" class="text-btn open-adjust">Điều chỉnh</button>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${empty section.foods}">
                            <p class="empty-text">Chưa có món nào trong bữa này.</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="food" items="${section.foods}">
                                <div class="food-pill">
                                    <img src="${pageContext.request.contextPath}/assets/images/${food.imageUrl}" alt="${food.foodName}">
                                    <div>
                                        <h4>${food.foodName}</h4>
                                        <p><fmt:formatNumber value="${food.calories}" minFractionDigits="2" maxFractionDigits="2" /> calo</p>
                                    </div>
                                    <form method="post" action="${pageContext.request.contextPath}/meal_plan" class="remove-form">
                                        <input type="hidden" name="action" value="removeDetail">
                                        <input type="hidden" name="selectedDate" value="${selectedDate}">
                                        <input type="hidden" name="detailId" value="${food.detailId}">
                                        <button type="submit" class="remove-btn" title="Xóa món">x</button>
                                    </form>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </article>
            </c:forEach>
        </section>

        
    </main>

    <div class="modal" id="adjustModal">
        <div class="modal-card">
            <button type="button" class="close-modal"><i class="fa-solid fa-xmark"></i></button>
            <h3 id="adjustTitle">Điều chỉnh món ăn</h3>
            <form method="post" action="${pageContext.request.contextPath}/meal_plan">
                   <input type="hidden" name="action" value="updateMealFoods">
                <input type="hidden" name="selectedDate" value="${selectedDate}">
                <input type="hidden" id="adjustMealTypeId" name="mealTypeId">
                <div class="form-group">
                    <label>Chọn món ăn</label>
                    <select id="adjustFoodIds" name="foodIds" multiple size="8" class="multi-select searchable-dropdown">
                        <c:forEach var="food" items="${allFoods}">
                            <option value="${food.food_id}">${food.food_name} (<fmt:formatNumber value="${food.calories}" minFractionDigits="2" maxFractionDigits="2" /> calo)</option>
                        </c:forEach>
                    </select>
                </div>
                <button class="btn-save" type="submit"><i class="fa-regular fa-floppy-disk"></i> Lưu thay đổi</button>
            </form>
        </div>
    </div>
    <script>
        const adjustModal = document.getElementById('adjustModal');
        const addModal = document.getElementById('addModal');
        const adjustTitle = document.getElementById('adjustTitle');
        const adjustMealTypeId = document.getElementById('adjustMealTypeId');

const adjustFoodIds = document.getElementById('adjustFoodIds');

        $(document).ready(function() {
            $('.searchable-dropdown').select2({
                placeholder: "Gõ để tìm món ăn...",
                allowClear: true,
                width: '100%',
                language: {
                    noResults: function() {
                        return "Không tìm thấy món ăn phù hợp";
                    }
                }
            });
        });
        
        document.querySelectorAll('.open-adjust').forEach((btn) => {
            btn.addEventListener('click', (e) => {
                const card = e.target.closest('.meal-card-item');
                adjustMealTypeId.value = card.dataset.mealId;
                adjustTitle.textContent = 'Điều chỉnh món ăn cho ' + card.dataset.mealName.toLowerCase();
               const selectedIds = (card.dataset.selectedFoodIds || '')
                        .split(',')
                        .map(id => id.trim())
                        .filter(Boolean);
                for (const option of adjustFoodIds.options) {
                    option.selected = selectedIds.includes(option.value);
                }
                $('#adjustFoodIds').trigger('change'); 
            adjustModal.classList.add('show');
            });
        });

        document.getElementById('openAddMeal').addEventListener('click', () => addModal.classList.add('show'));
        document.querySelectorAll('.close-modal').forEach((btn) => {
            btn.addEventListener('click', () => btn.closest('.modal').classList.remove('show'));
        });

        [adjustModal, addModal].forEach((modal) => {
            modal.addEventListener('click', (e) => {
                if (e.target === modal) modal.classList.remove('show');
            });
        });
    </script>
</body>
</html>