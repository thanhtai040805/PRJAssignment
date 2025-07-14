<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>➕ Thêm xe mới</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                padding: 40px;
            }

            h2 {
                text-align: center;
                color: #343a40;
            }

            form {
                max-width: 700px;
                margin: 0 auto;
                background-color: #ffffff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            label {
                display: block;
                margin-top: 15px;
                font-weight: bold;
                color: #495057;
            }

            input[type="text"],
            input[type="number"],
            input[type="file"],
            select,
            textarea {
                width: 100%;
                padding: 8px;
                margin-top: 5px;
                border: 1px solid #ced4da;
                border-radius: 5px;
                font-size: 14px;
            }

            textarea {
                resize: vertical;
            }

            button {
                margin-top: 20px;
                padding: 10px 20px;
                font-size: 16px;
                background-color: #007bff;
                color: #fff;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            button:hover {
                background-color: #0056b3;
            }

            a {
                display: inline-block;
                margin-top: 20px;
                text-decoration: none;
                color: #6c757d;
            }

            a:hover {
                text-decoration: underline;
            }

            #kmField {
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <h2>➕ Thêm xe mới</h2>
        <form action="car" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="insert"/>

            <label>Tên xe:</label>
            <input name="carName" required />

            <label>Nhà cung cấp:</label>
            <select name="supplierId" required>
                <c:forEach var="s" items="${suppliers}">
                    <option value="${s.supplierId}">${s.supplierName}</option>
                </c:forEach>
            </select>

            <label>Dòng xe (Car Model):</label>
            <select name="modelId" required>
                <c:forEach var="m" items="${models}">
                    <option value="${m.carModelId}">
                        ${m.carModelName} (${m.carBrand.carBrandName})
                    </option>
                </c:forEach>
            </select>

            <label>Màu sắc:</label>
            <input name="color" required />

            <label>Năm sản xuất:</label>
            <input name="year" type="number" required />

            <label>Giá bán:</label>
            <input name="price" type="number" min="0" required />

            <label>Giá nhập:</label>
            <input name="importPrice" type="number" min="0" />

            <label>Số lượng tồn:</label>
            <input name="stockQuantity" type="number" required />

            <label>Global Key:</label>
            <input name="globalKey" required />

            <label>Tình trạng:</label>
            <select name="condition" id="condition" onchange="toggleKmField()" required>
                <option value="Mới">Mới</option>
                <option value="Cũ">Cũ</option>
                <option value="Tân trang">Tân trang</option>
            </select>

            <label>Trạng thái:</label>
            <select name="status" required>
                <option value="Có sẵn">Có sẵn</option>
                <option value="Đã bán">Đã bán</option>
                <option value="Tạm khóa">Tạm khóa</option>
            </select>

            <div id="kmField" style="display:none;">
                <label>Số km đã đi:</label>
                <input type="number" name="kmDaDi" id="kmDaDi" min="0" />
            </div>

            <label>Dung tích động cơ (cc):</label>
            <input name="engineCapacity" type="number" min="0" />

            <label>Công suất (HP):</label>
            <input name="power" type="number" min="0" />

            <label>Hộp số:</label>
            <input name="transmission" />

            <label>Số khung:</label>
            <input name="chassisNumber" />

            <label>Số máy:</label>
            <input name="engineNumber" />

            <label>Mô tả:</label>
            <textarea name="description" rows="4"></textarea>

            <label>Ảnh xe:</label>
            <input type="file" name="carImage" accept="image/*" required />

            <button type="submit">➕ Thêm xe</button>
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
                    kmInput.value = "";
                }
            }
            window.onload = toggleKmField;
        </script>
    </body>
</html>
