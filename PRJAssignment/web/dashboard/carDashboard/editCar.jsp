<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Car" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="car" type="model.Car" scope="request" />

<!DOCTYPE html>
<html>
    <head>
        <title>Chỉnh sửa xe</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 30px;
                background: #f6f9fc;
            }

            form {
                max-width: 750px;
                margin: auto;
                background: #fff;
                padding: 25px 30px;
                border-radius: 10px;
                box-shadow: 0 0 12px rgba(0, 0, 0, 0.1);
            }

            h2 {
                text-align: center;
                color: #333;
            }

            label {
                display: block;
                margin-top: 12px;
                font-weight: bold;
                color: #555;
            }

            input, select, textarea {
                width: 100%;
                padding: 8px 10px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 14px;
            }

            textarea {
                resize: vertical;
            }

            button {
                margin-top: 20px;
                padding: 12px 20px;
                border: none;
                background: #007bff;
                color: white;
                border-radius: 5px;
                cursor: pointer;
                font-size: 15px;
            }

            button:hover {
                background: #0056b3;
            }

            a {
                display: block;
                margin-top: 20px;
                text-align: center;
                color: #007bff;
                text-decoration: none;
            }

            img {
                margin-top: 10px;
                max-width: 150px;
            }
        </style>
    </head>
    <body>
        <h2>✏️ Chỉnh sửa xe</h2>
        <form action="car" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="update"/>
            <input type="hidden" name="id" value="${car.carId}"/>
            <input type="hidden" name="oldImageLink" value="${car.imageLink}" />

            <label>Dòng xe:</label>
            <select name="modelId" required>
                <c:forEach var="m" items="${models}">
                    <option value="${m.carModelId}" ${car.carModel.carModelId == m.carModelId ? 'selected' : ''}>
                        ${m.carBrand.carBrandName} - ${m.carModelName}
                    </option>
                </c:forEach>
            </select>

            <label>Nhà cung cấp:</label>
            <select name="supplierId" required>
                <c:forEach var="s" items="${suppliers}">
                    <option value="${s.supplierId}" ${car.supplier.supplierId == s.supplierId ? 'selected' : ''}>
                        ${s.supplierName}
                    </option>
                </c:forEach>
            </select>

            <label>Tên xe:</label>
            <input name="carName" value="${car.carName}" required/>

            <label>Màu sắc:</label>
            <input name="color" value="${car.color}" required/>

            <label>Năm sản xuất:</label>
            <input name="year" value="${car.year}" type="number" required/>

            <label>Dung tích động cơ (cc):</label>
            <input name="engineCapacity" type="number" value="${car.engineCapacity}" min="0"/>

            <label>Công suất (hp):</label>
            <input name="power" type="number" value="${car.power}" min="0"/>

            <label>Hộp số:</label>
            <select name="transmission" required>
                <option value="Số sàn" ${car.transmission == 'Số sàn' ? 'selected' : ''}>Số sàn</option>
                <option value="Số tự động" ${car.transmission == 'Số tự động' ? 'selected' : ''}>Số tự động</option>
                <option value="CVT" ${car.transmission == 'CVT' ? 'selected' : ''}>CVT</option>
            </select>

            <label>Trạng thái:</label>
            <select name="status" required>
                <option value="Có sẵn" ${car.status == 'Có sẵn' ? 'selected' : ''}>Có sẵn</option>
                <option value="Đã bán" ${car.status == 'Đã bán' ? 'selected' : ''}>Đã bán</option>
                <option value="Đang sửa chữa" ${car.status == 'Đang sửa chữa' ? 'selected' : ''}>Đang sửa chữa</option>
                <option value="Tạm khóa" ${car.status == 'Tạm khóa' ? 'selected' : ''}>Tạm khóa</option>
            </select>

            <label>Tình trạng:</label>
            <select name="condition" id="condition" onchange="toggleKmField()" required>
                <option value="Mới" ${car.condition == 'Mới' ? 'selected' : ''}>Mới</option>
                <option value="Cũ" ${car.condition == 'Cũ' ? 'selected' : ''}>Cũ</option>
                <option value="Tân trang" ${car.condition == 'Tân trang' ? 'selected' : ''}>Tân trang</option>
            </select>

            <div id="kmField" style="display:none;">
                <label>Số km đã đi:</label>
                <input type="number" name="kmDaDi" id="kmDaDi" value="${car.mileage}" min="0"/>
            </div>

            <label>Giá nhập:</label>
            <input name="importPrice" type="number" value="${car.importPrice}" min="0"/>

            <label>Giá bán:</label>
            <input name="price" type="number" value="${car.salePrice}" min="0" required/>

            <label>Số lượng tồn:</label>
            <input name="stockQuantity" type="number" value="${car.stockQuantity}" min="0" required/>

            <label>Số khung:</label>
            <input name="chassisNumber" value="${car.chassisNumber}" readonly/>

            <label>Số máy:</label>
            <input name="engineNumber" value="${car.engineNumber}" readonly/>

            <label>Global Key:</label>
            <input name="globalKey" value="${car.globalKey}" required/>

            <label>Mô tả:</label>
            <textarea name="description">${car.description}</textarea>

            <label>Ảnh hiện tại:</label>
            <img src="${pageContext.request.contextPath}/${car.imageLink}" alt="Ảnh xe"/>

            <label>Ảnh mới (nếu muốn thay):</label>
            <input type="file" name="carImage" accept="image/*" />

            <button type="submit">💾 Cập nhật</button>
            <a href="car">⬅ Quay lại danh sách</a>
        </form>

        <script>
            function toggleKmField() {
                const condition = document.getElementById("condition").value;
                const kmField = document.getElementById("kmField");
                const kmInput = document.getElementById("kmDaDi");
                if (condition === "Cũ") {
                    kmField.style.display = "block";
                    kmInput.required = true;
                } else {
                    kmField.style.display = "none";
                    kmInput.required = false;
                    kmInput.value = 0;
                }
            }
            window.onload = toggleKmField;
        </script>
    </body>
</html>
