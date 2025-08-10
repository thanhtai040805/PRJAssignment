<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Xe Yêu Thích Của Bạn</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/car/favorite.css" />
    </head>
    <body>
        <jsp:include page="/header.jsp" />
        <div class="container">
            <h1>Xe Yêu Thích Của Bạn</h1>
            <div id="favoriteContent">
                <c:choose>
                    <c:when test="${not empty favoriteCars}">
                        <div class="favorite-list" id="favoriteList">
                            <c:forEach var="car" items="${favoriteCars}">
                                <div class="car-card">
                                    <a href="${pageContext.request.contextPath}/detail/${car.globalKey}">
                                        <c:choose>
                                            <c:when test="${not empty car.imageLink}">
                                                <img class="car-img" src="${pageContext.request.contextPath}${car.imageLink}" alt="${car.carName}">
                                            </c:when>
                                            <c:otherwise>
                                                <img class="car-img" src="${pageContext.request.contextPath}/images/no-image.png" alt="No image">
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="car-name">${car.carName}</div>
                                    </a>
                                    <div class="car-brand">${car.carBrandName} - ${car.carModelName}</div>
                                    <div class="car-price">
                                        <fmt:formatNumber value="${car.salePrice}" type="currency" currencyCode="VND" pattern="#,###"/> VNĐ
                                    </div>
                                    <button class="remove-btn" onclick="removeFavorite('${car.globalKey}', this)">Bỏ yêu thích</button>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="no-favorite" id="noFavoriteMsg">
                            <p>Bạn chưa có xe nào trong danh sách yêu thích.</p>
                            <a href="${pageContext.request.contextPath}/" style="color:#007bff;text-decoration:underline;">Khám phá xe ngay!</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <script>
            function removeFavorite(globalKey, btn) {
                btn.disabled = true;
                fetch('${pageContext.request.contextPath}/updateFavorite', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: 'globalKey=' + encodeURIComponent(globalKey)
                })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                // Xóa thẻ card khỏi DOM
                                const card = btn.closest('.car-card');
                                card.remove();
                                // Nếu không còn xe nào, hiển thị thông báo
                                const list = document.getElementById('favoriteList');
                                if (!list || list.children.length === 0) {
                                    document.getElementById('favoriteContent').innerHTML =
                                            '<div class="no-favorite"><p>Bạn chưa có xe nào trong danh sách yêu thích.</p>' +
                                            '<a href="${pageContext.request.contextPath}/" style="color:#007bff;text-decoration:underline;">Khám phá xe ngay!</a></div>';
                                }
                            } else {
                                alert(data.message || 'Bỏ yêu thích thất bại!');
                                btn.disabled = false;
                            }
                        })
                        .catch(() => {
                            alert('Có lỗi xảy ra!');
                            btn.disabled = false;
                        });
            }
        </script>
    </body>
</html>
