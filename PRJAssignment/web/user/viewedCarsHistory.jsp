<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Lịch sử xe đã xem</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/viewedCar.css" />
    </head>
    <body>
        <jsp:include page="/header.jsp" />
        <div class="container">
            <h1>Lịch sử xe đã xem</h1>
            <div class="top-action">
                <a href="${pageContext.request.contextPath}/history" class="search-history-btn" title="Xem lịch sử tìm kiếm">
                    <span style="font-size: 17px; vertical-align: middle;">🔍</span> Lịch sử tìm kiếm
                </a>
            </div>
            <c:choose>
                <c:when test="${not empty viewedCarsList}">
                    <div class="viewed-list">
                        <c:forEach var="car" items="${viewedCarsList}">
                            <div class="car-card" id="card-${car.globalKey}">
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
                                <div class="card-actions">
                                    <button class="favorite-btn${favoriteGlobalKeys != null && favoriteGlobalKeys.contains(car.globalKey) ? ' favorited' : ''}"
                                            data-globalkey="${car.globalKey}"
                                            onclick="toggleFavorite('${car.globalKey}', this)">
                                        <span class="heart-icon"></span>
                                    </button>
                                    <a class="detail-btn" href="${pageContext.request.contextPath}/detail/${car.globalKey}">
                                        Xem chi tiết
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="no-history">
                        <p>Bạn chưa xem xe nào.</p>
                        <a href="${pageContext.request.contextPath}/" style="color:#007bff;text-decoration:underline;">Khám phá xe ngay!</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <script>
            var contextPath = '${pageContext.request.contextPath}';
        </script>
        <script src="${pageContext.request.contextPath}/js/include.js"></script>
        <script>
            function toggleFavorite(globalKey, btn) {
                fetch('${pageContext.request.contextPath}/updateFavorite', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: 'globalKey=' + encodeURIComponent(globalKey)
                })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                btn.classList.toggle('favorited');
                            } else {
                                alert(data.message || 'Có lỗi xảy ra!');
                            }
                        })
                        .catch(() => {
                            alert('Có lỗi xảy ra!');
                        });
            }
            document.addEventListener("DOMContentLoaded", function () {
            <c:if test="${empty sessionScope.currentUser}">
                syncFavoriteFromCookie();
            </c:if>
            });
        </script>
    </body>
</html>
