<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Car" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    Car car = (Car) request.getAttribute("carDetail");
    List<Car> suggestionCars = (List<Car>) request.getAttribute("suggestionCars");

    if (car == null) {
        response.sendRedirect(request.getContextPath() + "/error.jsp"); // Hoặc /home
        return;
    }
    if (suggestionCars == null) {
        suggestionCars = new ArrayList<>();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết xe - <%= car.getTenXe() %></title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { display: flex; gap: 40px; }
        .car-image { max-width: 400px; }
        .car-info h2 { margin: 0; }
        .buttons button { margin: 10px 5px; padding: 10px 15px; cursor: pointer; }
        .suggestions { margin-top: 40px; }
        .suggestions h3 { margin-bottom: 10px; }
        .car-suggestion { display: inline-block; width: 200px; margin: 10px; border: 1px solid #ccc; padding: 10px; text-align: center; }
        .car-suggestion img { width: 100%; height: auto; }
        .car-suggestion a { text-decoration: none; color: #000; font-weight: bold; }
    </style>
</head>
<body>

<div class="container">
    <div class="car-image">
        <img src="${pageContext.request.contextPath}${carDetail.getImageOrDefault()}" alt="Ảnh xe" width="100%">
    </div>

    <div class="car-info">
        <h2><%= car.getTenXe() %></h2>
        <p><strong>Giá bán:</strong> <%= car.getFormattedPrice() %></p>
        <p><strong>Tình trạng:</strong> <%= car.getTinhTrang() %></p>
        <p><strong>Động cơ:</strong> <%= car.getEngineInfo() %></p>
        <p><strong>Nhiên liệu:</strong> <%= car.getNhienLieu() %></p>
        <p><strong>Năm sản xuất:</strong> <%= car.getNamSanXuat() %></p>
        <p><strong>Màu sắc:</strong> <%= car.getMauSac() %></p>
        <p><strong>Số chỗ ngồi:</strong> <%= car.getSoChoNgoi() %></p>
        <p><strong>Mô tả:</strong> <%= car.getMoTa() %></p>

        <div class="buttons">
            <button onclick="location.href='${pageContext.request.contextPath}/loanForm?carId=<%= car.getMaXe() %>'">Làm khoản vay</button>
            <button onclick="location.href='${pageContext.request.contextPath}/buyCar?carId=<%= car.getMaXe() %>'">Mua xe</button>
            <button onclick="location.href='${pageContext.request.contextPath}/addFavorite?carId=<%= car.getMaXe() %>'">Thêm vào yêu thích</button>
        </div>
    </div>
</div>

<div class="suggestions">
    <h3>Xe gợi ý</h3>
    <c:forEach var="c" items="${suggestionCars}">
        <div class="car-suggestion">
            <%-- Đổi đường dẫn từ /carDetail/ID sang /detail/globalKey --%>
            <a href="${pageContext.request.contextPath}/detail/${c.globalKey}">
                <img src="${pageContext.request.contextPath}${c.getImageOrDefault()}" alt="Xe gợi ý" width="100%">
                <p>${c.tenXe}</p>
                <p>${c.formattedPrice}</p>
            </a>
        </div>
    </c:forEach>
</div>

</body>
</html>