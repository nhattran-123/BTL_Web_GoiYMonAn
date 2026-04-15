<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Điều chỉnh công thức</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/customize_recipe.css">
</head>
<body>
    <div class="container">
        <h2>Điều chỉnh công thức theo ý mình</h2>
        
        <c:if test="${not empty errorMsg}">
            <div class="alert-error">${errorMsg}</div>
        </c:if>
        
        <c:if test="${not empty successMsg}">
            <script>
                // Đợi trang load xong giao diện thì mới bắn thông báo
                document.addEventListener("DOMContentLoaded", function() {
                    var toast = document.getElementById("toast");
                    // Chèn icon checkmark và nội dung chữ
                    toast.innerHTML = '<i class="fa-solid fa-circle-check"></i> ' + '${successMsg}';
                    toast.className = "show";
                    setTimeout(function(){ 
                        toast.className = toast.className.replace("show", ""); 
                    }, 3000);
                });
            </script>
        </c:if>

        <form action="${pageContext.request.contextPath}/customize-recipe" method="POST" id="customForm">
            <input type="hidden" name="foodId" value="${food.food_id}">
            
            <div class="row-flex">
                <div class="col-flex">
                    <label>Tên món ăn</label>
                    <input type="text" class="input-soft" value="${food.food_name}" readonly>
                </div>
                
                <div class="col-flex">
                    <label>Dinh dưỡng tạm tính:</label>
                    <div style="display: flex; gap: 10px;">
                        <div style="flex: 1; background: #f0fdf4; padding: 10px; border-radius: 8px; text-align: center;">
                            <span style="font-size: 12px; color: #6b7280; display: block;">Calo</span>
                            <input type="text" id="displayCalo" value="${food.calories}" readonly style="background: transparent; border: none; text-align: center; width: 100%; font-weight: bold; color: #111; outline: none; padding: 0;">
                        </div>
                        <div style="flex: 1; background: #fffbeb; padding: 10px; border-radius: 8px; text-align: center;">
                            <span style="font-size: 12px; color: #6b7280; display: block;">Fat (g)</span>
                            <input type="text" id="displayFat" value="0" readonly style="background: transparent; border: none; text-align: center; width: 100%; font-weight: bold; color: #d97706; outline: none; padding: 0;">
                        </div>
                        <div style="flex: 1; background: #eff6ff; padding: 10px; border-radius: 8px; text-align: center;">
                            <span style="font-size: 12px; color: #6b7280; display: block;">Protein (g)</span>
                            <input type="text" id="displayProtein" value="0" readonly style="background: transparent; border: none; text-align: center; width: 100%; font-weight: bold; color: #2563eb; outline: none; padding: 0;">
                        </div>
                        <div style="flex: 1; background: #fef2f2; padding: 10px; border-radius: 8px; text-align: center;">
                            <span style="font-size: 12px; color: #6b7280; display: block;">Carbs (g)</span>
                            <input type="text" id="displayCarbs" value="0" readonly style="background: transparent; border: none; text-align: center; width: 100%; font-weight: bold; color: #dc2626; outline: none; padding: 0;">
                        </div>
                    </div>

                    <input type="hidden" id="calculatedCalories" name="calculatedCalories" value="${food.calories}">
                    <input type="hidden" id="calculatedFat" name="calculatedFat" value="0">
                    <input type="hidden" id="calculatedProtein" name="calculatedProtein" value="0">
                    <input type="hidden" id="calculatedCarbs" name="calculatedCarbs" value="0">
                </div>
            </div>

            <div style="margin-bottom: 30px;">
                <div class="header-flex">
                    <label style="margin: 0;">Nguyên liệu</label>
                    <button type="button" class="btn-add" onclick="addIngredient()">
                        <i class="fa-solid fa-plus"></i> Thêm nguyên liệu
                    </button>
                </div>
                
                <div id="ingredient-container">
                    <c:choose>
                        <c:when test="${not empty ingredientList}">
                            <c:forEach var="item" items="${ingredientList}">
                                <div class="ingredient-item">
                                    <input type="hidden" name="ingredientId" value="${item.ingredientId}">
                                    <input type="text" class="name" value="${item.ingredientName}" readonly>
                                    <input type="number" class="qty-input qty" name="ingredientQty" value="${item.quantity}" 
                                           data-calo="${item.caloPerGram}" data-fat="${item.fatPerGram}" 
                                           data-protein="${item.proteinPerGram}" data-carbs="${item.carbsPerGram}" 
                                           oninput="updateMacros()"> 
                                    <span>${item.unit}</span>
                                    <button type="button" class="btn-trash" onclick="this.parentElement.remove(); updateMacros();"><i class="fa-regular fa-trash-can"></i></button>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p id="no-data-msg" style="color: #9ca3af; font-style: italic; text-align: center; padding: 20px;">
                                Chưa có nguyên liệu. Hãy bấm "Thêm nguyên liệu" để bắt đầu!
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div style="margin-bottom: 20px;">
                <label>Công thức</label>
                <textarea name="recipeText" class="textarea-bordered" rows="6" placeholder="Nếu không điền các bước nấu sẽ giữ nguyên....."></textarea>
            </div>

            <div class="bottom-actions">
                <button type="button" class="btn-back" onclick="window.history.back()">
                    <i class="fa-solid fa-arrow-left"></i> Quay lại
                </button>
                <button type="submit" class="btn-save">
                    <i class="fa-regular fa-floppy-disk"></i> Lưu thay đổi
                </button>
            </div>
        </form>
    </div>

    <div id="ingredient-template" style="display: none;">
        <select class="input-soft name" name="ingredientId" onchange="onIngredientChange(this)" style="flex: 1; padding: 10px;">
            <option value="" disabled selected>-- Chọn nguyên liệu từ danh sách --</option>
            <c:forEach var="ing" items="${allIngredients}">
                <option value="${ing.ingredientId}" data-calo="${ing.caloPerGram}" data-fat="${ing.fatPerGram}" data-protein="${ing.proteinPerGram}" data-carbs="${ing.carbsPerGram}">
                    ${ing.ingredientName}
                </option>
            </c:forEach>
        </select>
    </div>

    <script>
        // Giữ nguyên toàn bộ phần Script của bạn ở đây...
        function updateMacros() {
            let totalCalo = 0; let totalFat = 0; let totalProtein = 0; let totalCarbs = 0;
            let inputs = document.querySelectorAll('.qty-input');
            inputs.forEach(input => {
                let qty = parseFloat(input.value) || 0;
                let caloPerGram = parseFloat(input.getAttribute('data-calo')) || 0;
                let fatPerGram = parseFloat(input.getAttribute('data-fat')) || 0;
                let proteinPerGram = parseFloat(input.getAttribute('data-protein')) || 0;
                let carbsPerGram = parseFloat(input.getAttribute('data-carbs')) || 0;
                
                if (caloPerGram === 0 && qty > 0) caloPerGram = 1; 
                totalCalo += (qty * caloPerGram); totalFat += (qty * fatPerGram);
                totalProtein += (qty * proteinPerGram); totalCarbs += (qty * carbsPerGram);
            });

            document.getElementById('displayCalo').value = Math.round(totalCalo);
            document.getElementById('displayFat').value = totalFat.toFixed(1);
            document.getElementById('displayProtein').value = totalProtein.toFixed(1);
            document.getElementById('displayCarbs').value = totalCarbs.toFixed(1);

            document.getElementById('calculatedCalories').value = Math.round(totalCalo);
            document.getElementById('calculatedFat').value = totalFat.toFixed(2);
            document.getElementById('calculatedProtein').value = totalProtein.toFixed(2);
            document.getElementById('calculatedCarbs').value = totalCarbs.toFixed(2);
        }
        window.onload = function() { updateMacros(); };
        function addIngredient() {
            const container = document.getElementById('ingredient-container');
            const noDataMsg = document.getElementById('no-data-msg');
            if(noDataMsg) noDataMsg.remove();
            const div = document.createElement('div');
            div.className = 'ingredient-item';
            
            // Lấy HTML từ mẫu ẩn
            let selectHTML = document.getElementById('ingredient-template').innerHTML;
            div.innerHTML = selectHTML + `
                <input type="number" class="qty-input qty" value="100" name="ingredientQty" data-calo="0" data-fat="0" data-protein="0" data-carbs="0" oninput="updateMacros()"> 
                <span>gram</span>
                <button type="button" class="btn-trash" onclick="this.parentElement.remove(); updateMacros();"><i class="fa-regular fa-trash-can"></i></button>`;
            container.appendChild(div); updateMacros();
        }
        function onIngredientChange(selectElement) {
            let selectedOption = selectElement.options[selectElement.selectedIndex];
            let row = selectElement.closest('.ingredient-item');
            let qtyInput = row.querySelector('.qty-input');
            qtyInput.setAttribute('data-calo', selectedOption.getAttribute('data-calo'));
            qtyInput.setAttribute('data-fat', selectedOption.getAttribute('data-fat'));
            qtyInput.setAttribute('data-protein', selectedOption.getAttribute('data-protein'));
            qtyInput.setAttribute('data-carbs', selectedOption.getAttribute('data-carbs'));
            updateMacros();
        }
        document.getElementById('customForm').addEventListener('submit', function(e) {
            let inputs = document.querySelectorAll('.qty-input');
            for(let i = 0; i < inputs.length; i++) {
                if(inputs[i].value < 0 || inputs[i].value === '') {
                    e.preventDefault(); alert('Khối lượng nguyên liệu không hợp lệ!');
                    inputs[i].focus(); return;
                }
            }
        });
    </script>
    <div id="toast"></div>
</body>
</html>