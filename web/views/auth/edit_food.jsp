<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sửa món ăn - Healthy Food</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
</head>
<body>
    <aside class="sidebar">
        <div class="brand"><div class="brand-icon"><i class="fa-solid fa-leaf text-white"></i></div><div class="brand-text"><h2>Healthy Food</h2><span>Dinh dưỡng thông minh</span></div></div>
        <ul class="nav-menu">
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link"><i class="fa-solid fa-table-cells-large"></i> Tổng quan</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage_food" class="nav-link active"><i class="fa-solid fa-utensils"></i> Quản lý món ăn</a></li>
        </ul>
    </aside>

    <main class="main-content">
        <form action="${pageContext.request.contextPath}/admin/edit_food" method="POST" id="editFoodForm" enctype="multipart/form-data">
            <input type="hidden" name="foodId" value="${food.food_id}">
            <div class="form-card" style="background:#fff;padding:30px;border-radius:12px;">
                <h2>Sửa món ăn</h2>
                <c:if test="${param.error == 'true'}"><p style="color:#dc2626;">Cập nhật thất bại, vui lòng thử lại.</p></c:if>

                <label>Tên món ăn</label>
                <input type="text" name="name" class="form-control" value="${food.food_name}" required>

                <label>Mô tả</label>
                <textarea name="description" class="form-control" rows="3" required>${food.description}</textarea>

                <label>Ảnh mới (không bắt buộc)</label>
                <input type="file" name="imageFile" class="form-control" accept="image/*">

                <div style="display:grid;grid-template-columns:repeat(4,1fr);gap:12px; margin-top:12px;">
                    <input type="number" step="0.1" name="calories" id="totalCal" class="form-control" value="${food.calories}" readonly>
                    <input type="number" step="0.1" name="protein" id="totalPro" class="form-control" value="${food.protein}" readonly>
                    <input type="number" step="0.1" name="fat" id="totalFat" class="form-control" value="${food.fat}" readonly>
                    <input type="number" step="0.1" name="carbohydrate" id="totalCarb" class="form-control" value="${food.carbohydrate}" readonly>
                </div>

                <label style="margin-top:12px; display:block;">Công thức</label>
                <textarea name="recipe" class="form-control" rows="4" required>${food.recipe}</textarea>

                <div style="display:flex;justify-content:space-between;align-items:center;margin-top:20px;">
                    <h3>Nguyên liệu</h3>
                    <button type="button" id="btnAddRow">+ Thêm nguyên liệu</button>
                </div>

                <div id="ingredient-container">
                    <div class="ingredient-row template-row" style="display:none;grid-template-columns:2fr 1fr 1fr auto;gap:10px;margin-bottom:10px;">
                        <select name="ingredientId[]" class="form-control ing-select" disabled>
                            <option value="">Tên nguyên liệu</option>
                            <c:forEach items="${ingredients}" var="ing">
                                <option value="${ing.id}" data-cal="${ing.calories}" data-pro="${ing.protein}" data-fat="${ing.fat}" data-carb="${ing.carbohydrate}">${ing.name} (${ing.category})</option>
                            </c:forEach>
                        </select>
                        <input type="number" name="quantity[]" class="form-control ing-qty" step="0.1" min="0.1" disabled>
                        <select name="unit[]" class="form-control" disabled>
                            <option value="g">g</option>
                            <option value="ml">ml</option>
                        </select>
                        <button type="button" class="btn-remove-row">x</button>
                    </div>

                    <c:forEach items="${foodIngredients}" var="fi">
                        <div class="ingredient-row" style="display:grid;grid-template-columns:2fr 1fr 1fr auto;gap:10px;margin-bottom:10px;">
                            <select name="ingredientId[]" class="form-control ing-select">
                                <option value="">Tên nguyên liệu</option>
                                <c:forEach items="${ingredients}" var="ing">
                                    <option value="${ing.id}" data-cal="${ing.calories}" data-pro="${ing.protein}" data-fat="${ing.fat}" data-carb="${ing.carbohydrate}" ${ing.id == fi.ingredientId ? 'selected' : ''}>${ing.name} (${ing.category})</option>
                                </c:forEach>
                            </select>
                            <input type="number" name="quantity[]" class="form-control ing-qty" step="0.1" min="0.1" value="${fi.quantity}">
                            <select name="unit[]" class="form-control">
                                <option value="g" ${fi.unit == 'g' ? 'selected' : ''}>g</option>
                                <option value="ml" ${fi.unit == 'ml' ? 'selected' : ''}>ml</option>
                            </select>
                            <button type="button" class="btn-remove-row">x</button>
                        </div>
                    </c:forEach>
                </div>

                <div style="margin-top:20px;">
                    <a href="${pageContext.request.contextPath}/admin/manage_food">Hủy</a>
                    <button type="submit">Lưu cập nhật</button>
                </div>
            </div>
        </form>
    </main>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script>
        function calculateMacros() {
            let totalCal = 0, totalPro = 0, totalFat = 0, totalCarb = 0;
            $('.ingredient-row:not(.template-row)').each(function() {
                let opt = $(this).find('.ing-select option:selected');
                let qty = parseFloat($(this).find('.ing-qty').val()) || 0;
                if (opt.val()) {
                    let ratio = qty / 100;
                    totalCal += ratio * parseFloat(opt.data('cal') || 0);
                    totalPro += ratio * parseFloat(opt.data('pro') || 0);
                    totalFat += ratio * parseFloat(opt.data('fat') || 0);
                    totalCarb += ratio * parseFloat(opt.data('carb') || 0);
                }
            });
            $('#totalCal').val(totalCal.toFixed(1));
            $('#totalPro').val(totalPro.toFixed(1));
            $('#totalFat').val(totalFat.toFixed(1));
            $('#totalCarb').val(totalCarb.toFixed(1));
        }
        $(document).ready(function() {
            $('.ing-select').select2({ placeholder: 'Tìm nguyên liệu...', width: '100%' });
            calculateMacros();
            $('#btnAddRow').click(function() {
                const row = $('.template-row').clone().removeClass('template-row').show();
                row.find('input,select').removeAttr('disabled');
                $('#ingredient-container').append(row);
                row.find('.ing-select').select2({ placeholder: 'Tìm nguyên liệu...', width: '100%' });
            });
            $('#ingredient-container').on('change input', '.ing-select, .ing-qty', calculateMacros);
            $('#ingredient-container').on('click', '.btn-remove-row', function() { $(this).closest('.ingredient-row').remove(); calculateMacros(); });
        });
    </script>
</body>
</html>
