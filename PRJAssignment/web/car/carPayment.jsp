<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    model.Car car = (model.Car) request.getAttribute("carToBuy");
    if (car == null) {
        response.sendRedirect(request.getContextPath() + "/error.jsp");
        return;
    }
    model.Customer loggedInCustomer = (model.Customer) session.getAttribute("loggedInCustomer");
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thanh toán - ${carToBuy.carName}</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/car/payment.css" />
    </head>
    <body>
        <jsp:include page="/header.jsp" />
        <div class="payment-main-container">
            <div class="car-summary-section">
                <h2>Thông tin xe</h2>
                <c:choose>
                    <c:when test="${not empty carToBuy.imageLink}">
                        <img src="${pageContext.request.contextPath}${carToBuy.imageLink}" alt="Ảnh xe ${carToBuy.carName}">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/images/no-image.png" alt="Ảnh xe ${carToBuy.carName}">
                    </c:otherwise>
                </c:choose>
                <p><strong>Tên xe:</strong> <span>${carToBuy.carName}</span></p>
                <p><strong>Hãng xe:</strong> <span>${carToBuy.carBrandName}</span></p>
                <p><strong>Dòng xe:</strong> <span>${carToBuy.carModelName}</span></p>
                <p><strong>Năm sản xuất:</strong> <span>${carToBuy.year}</span></p>
                <p><strong>Màu sắc:</strong> <span>${carToBuy.color}</span></p>
                <p><strong>Số chỗ ngồi:</strong> <span>${carToBuy.seatCount}</span></p>
                <p class="price"><strong>Giá bán:</strong>
                    <span>
                        <fmt:formatNumber value="${carToBuy.salePrice}" type="currency" currencyCode="VND" pattern="#,###"/>
                    </span>
                </p>
            </div>
            <div class="payment-form-section">
                <h2>Thông tin đặt hàng</h2>
                <form action="${pageContext.request.contextPath}/invoice" method="post">
                    <input type="hidden" name="carId" value="${carToBuy.carId}"/>
                    <input type="hidden" name="price" value="${carToBuy.salePrice}"/>
                    <input type="hidden" name="carName" value="${carToBuy.carName}"/>

                    <label for="customerName">Họ tên:</label>
                    <input type="text" id="customerName" name="customerName" required 
                           value="${loggedInCustomer != null ? loggedInCustomer.fullName : ''}">

                    <label for="phone">Số điện thoại:</label>
                    <input type="text" id="phone" name="phone" required 
                           value="${loggedInCustomer != null ? loggedInCustomer.phone : ''}">

                    <label for="address">Địa chỉ:</label>
                    <input type="text" id="address" name="address" required 
                           value="${loggedInCustomer != null ? loggedInCustomer.address : ''}">

                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required 
                           value="${loggedInCustomer != null ? loggedInCustomer.email : ''}">

                    <label for="employee">Chọn nhân viên tư vấn:</label>
                    <select name="employeeId" id="employee" required>
                        <option value="">-- Chọn nhân viên phụ trách --</option>
                        <c:forEach var="emp" items="${employeeList}">
                            <option value="${emp.employeeId}">${emp.fullName}</option>
                        </c:forEach>
                    </select>

                    <label for="appointmentDate">Ngày tư vấn / mua xe:</label>
                    <input type="date" id="appointmentDate" name="appointmentDate" required
                           min="<%= java.time.LocalDate.now()%>">

                    <label>Phương thức thanh toán:</label>
                    <select name="paymentMethodId" required>
                        <c:forEach var="pm" items="${paymentMethods}">
                            <option value="${pm.paymentMethodId}">${pm.paymentMethodName}</option>
                        </c:forEach>
                    </select>

                    <input type="submit" value="Xác nhận thông tin">
                </form>
            </div>
        </div>
    </body>
</html>
