<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách xe</title>
        <style>
            body {
                font-family: Arial;
                margin: 30px;
            }
            h2 {
                text-align: center;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                border: 1px solid #ccc;
                padding: 8px;
                text-align: center;
            }
            th {
                background-color: #f2f2f2;
            }
            a.btn {
                display: inline-block;
                padding: 6px 12px;
                background: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 4px;
            }
            a.btn:hover {
                background: #0056b3;
            }
            button.delete {
                background: #dc3545;
                border: none;
                padding: 6px 12px;
                color: white;
                border-radius: 4px;
                cursor: pointer;
            }
            button.delete:hover {
                background: #b02a37;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/header.jsp" />
        <h2>🚗 Danh sách xe</h2>
        <a href="car?action=insert" class="btn">➕ Thêm xe mới</a>
        <form method="get" action="car">
            <input type="text" name="search" placeholder="Tìm theo tên xe" value="${search}" />

            <label for="sort">Sắp xếp:</label>
            <select name="sort" id="sort">
                <option value="">-- Không sắp xếp --</option>
                <option value="key-asc" ${sort == 'key-asc' ? 'selected' : ''}>Global Key A-Z</option>
                <option value="key-desc" ${sort == 'key-desc' ? 'selected' : ''}>Global Key Z-A</option>
                <option value="price-asc" ${sort == 'price-asc' ? 'selected' : ''}>Giá tăng dần</option>
                <option value="price-desc" ${sort == 'price-desc' ? 'selected' : ''}>Giá giảm dần</option>
            </select>

            <button type="submit">🔍 Tìm kiếm</button>
        </form>
        <table>
            <thead>
                <tr>
                    <th>Global Key</th>
                    <th>Tên xe</th>
                    <th>Màu</th>
                    <th>Năm</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Chức năng</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="car" items="${cars}">
                    <tr>
                        <td>${car.globalKey}</td>
                        <td>${car.carName}</td>
                        <td>${car.color}</td>
                        <td>${car.year}</td>
                        <td>${car.salePrice}</td>
                        <td>${car.stockQuantity}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/dashboard/car?action=edit&id=${car.carId}" class="btn">Sửa</a>
                            <form action="${pageContext.request.contextPath}/dashboard/car" method="post" style="display:inline;" 
                                  onsubmit="return confirm('Xác nhận xóa xe: ${car.carName}?');">
                                <input type="hidden" name="action" value="delete"/>
                                <input type="hidden" name="id" value="${car.carId}"/>
                                <button type="submit" class="delete">Xóa</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
