<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết xe - ${carDetail.carName}</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/car/carDetail.css" />
    </head>
    <body>
        <jsp:include page="/header.jsp" />
        <div class="container">
            <div class="car-image">
                <c:choose>
                    <c:when test="${not empty carDetail.imageLink}">
                        <img src="${pageContext.request.contextPath}${carDetail.imageLink}" alt="Ảnh xe">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/images/no-image.png" alt="Ảnh xe">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="car-info">
                <h1>${carDetail.carName}</h1>
                <p><strong>Giá bán:</strong>
                    <span>
                        <fmt:formatNumber value="${carDetail.salePrice}" type="currency" currencyCode="VND" pattern="#,###"/>
                    </span>
                </p>
                <p><strong>Tình trạng:</strong> <span>${carDetail.condition}</span></p>
                <p><strong>Động cơ:</strong>
                    <span>
                        <c:out value="${carDetail.engineCapacity}"/>cc -
                        <c:out value="${carDetail.power}"/>HP
                    </span>
                </p>
                <p><strong>Nhiên liệu:</strong> <span>${carDetail.fuelType}</span></p>
                <p><strong>Năm sản xuất:</strong> <span>${carDetail.year}</span></p>
                <p><strong>Màu sắc:</strong> <span>${carDetail.color}</span></p>
                <p><strong>Số chỗ ngồi:</strong> <span>${carDetail.seatCount}</span></p>
                <p><strong>Mô tả:</strong> <span>${carDetail.description}</span></p>
                <div class="buttons">
                    <c:if test="${not empty sessionScope.currentUser}">
                        <button onclick="checkStockBeforeBuy('${carDetail.globalKey}', '${carDetail.carName}')">Đặt xe</button>
                    </c:if>


                    <div class="favorite-action">
                        <button class="favorite-btn${favoriteGlobalKeys != null && favoriteGlobalKeys.contains(carDetail.globalKey) ? ' favorited' : ''}"
                                data-globalkey="${carDetail.globalKey}"
                                onclick="toggleFavorite('${carDetail.globalKey}', this)">
                            <span class="heart-icon"></span>
                            <span class="favorite-text">
                                Yêu thích
                            </span>
                        </button>
                    </div>
                </div>

            </div>
        </div>

        <!-- Phần hiển thị đánh giá trung bình -->
        <div class="rating-summary">
            <c:choose>
                <c:when test="${avgRating > 0}">
                    <div class="stars-display">
                        <c:forEach begin="1" end="5" var="i">
                            <i class="${i <= avgRating ? 'star full' : 'star'}"></i>
                        </c:forEach>
                    </div>
                    <div class="rating-info">
                        <span class="avg-rating"><fmt:formatNumber value="${avgRating}" maxFractionDigits="1"/> / 5</span>
                        <span class="total-rating">(${totalRatings} đánh giá)</span>
                    </div>
                </c:when>
                <c:otherwise>
                    <span class="no-rating">Chưa có đánh giá</span>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Danh sách review -->
        <h3>Đánh giá từ khách hàng</h3>
        <c:choose>
            <c:when test="${not empty reviews}">
                <c:forEach var="r" items="${reviews}">
                    <div class="review-item">
                        <div class="review-header">
                            <div class="review-meta">
                                <strong class="review-title">${r.title}</strong>
                                <span class="review-date"><fmt:formatDate value="${r.createdAt}" pattern="dd/MM/yyyy HH:mm"/></span>
                            </div>
                        </div>
                        <div class="review-content">
                            <p>${r.content}</p>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p class="no-review">Chưa có review nào cho xe này.</p>
            </c:otherwise>
        </c:choose>

        <!-- Form gửi review & rating -->
        <c:choose>
            <c:when test="${not empty sessionScope.currentUser}">
                <div class="review-form">
                    <h4>Viết đánh giá của bạn</h4>
                    <form action="${pageContext.request.contextPath}/ReviewAndRatingServlet" method="post">
                        <input type="hidden" name="globalKey" value="${carDetail.globalKey}" />

                        <!-- Rating -->
                        <div class="form-group">
                            <label for="rating">Chọn số sao:</label>
                            <div class="stars-input">
                                <input type="radio" name="rating" value="5" id="star5" required><label for="star5">★</label>
                                <input type="radio" name="rating" value="4" id="star4"><label for="star4">★</label>
                                <input type="radio" name="rating" value="3" id="star3"><label for="star3">★</label>
                                <input type="radio" name="rating" value="2" id="star2"><label for="star2">★</label>
                                <input type="radio" name="rating" value="1" id="star1"><label for="star1">★</label>
                            </div>
                        </div>

                        <!-- Review -->
                        <div class="form-group">
                            <label>Tiêu đề:</label>
                            <input type="text" name="title" required/>
                        </div>

                        <div class="form-group">
                            <label>Nội dung:</label>
                            <textarea name="content" required rows="4"></textarea>
                        </div>

                        <button type="submit" class="btn-submit-review">Gửi đánh giá</button>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login" class="login-to-review">Đăng nhập để gửi đánh giá</a>
            </c:otherwise>
        </c:choose>

        <c:if test="${not empty suggestionCars}">
            <section class="suggestions">
                <h2 class="section-title">Xe gợi ý dựa trên sở thích của bạn</h2>
                <div class="suggestion-grid">
                    <c:forEach var="c" items="${suggestionCars}">
                        <div class="car-suggestion">
                            <a href="${pageContext.request.contextPath}/detail/${c.globalKey}">
                                <c:choose>
                                    <c:when test="${not empty c.imageLink}">
                                        <img src="${pageContext.request.contextPath}${c.imageLink}" alt="Xe gợi ý">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/images/no-image.png" alt="Xe gợi ý">
                                    </c:otherwise>
                                </c:choose>
                                <p>${c.carName}</p>
                                <p>
                                    <fmt:formatNumber value="${c.salePrice}" type="currency" currencyCode="VND" pattern="#,###"/>
                                </p>
                            </a>
                            <div class="buttons">
                                <c:if test="${not empty sessionScope.currentUser}">
                                    <button onclick="checkStockBeforeBuy('${c.globalKey}', '${c.carName}')">Đặt xe</button>
                                </c:if>

                                <div class="favorite-action">
                                    <button class="favorite-btn${favoriteGlobalKeys != null && favoriteGlobalKeys.contains(c.globalKey) ? ' favorited' : ''}"
                                            data-globalkey="${c.globalKey}"
                                            onclick="toggleFavorite('${c.globalKey}', this)">
                                        <span class="heart-icon"></span>
                                        <span class="favorite-text">
                                            Yêu thích
                                        </span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </section>
        </c:if>

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

            var currentGlobalKey = '${carDetail.globalKey}';
            document.addEventListener("DOMContentLoaded", function () {
            <c:if test="${empty sessionScope.currentUser}">
                saveViewedCar(currentGlobalKey);
            </c:if>
            <c:if test="${not empty sessionScope.currentUser}">
                saveViewedCarToServer(currentGlobalKey);
            </c:if>
            });
        </script>
        <!-- Modal hết hàng -->
        <div id="outOfStockModal" style="display: none;
             position: fixed; top: 30%; left: 50%; transform: translate(-50%, -50%);
             background: white; padding: 25px 30px; border-radius: 12px;
             box-shadow: 0 5px 20px rgba(0,0,0,0.2); z-index: 9999;
             max-width: 400px; text-align: center;">
            <p id="outOfStockMessage" style="font-size: 1.1em; margin-bottom: 20px;"></p>
            <button onclick="closeOutOfStockModal()" style="
                    padding: 10px 20px; background: #007bff; color: white;
                    border: none; border-radius: 6px; font-weight: 600;
                    cursor: pointer;">OK</button>
        </div>

        <script>
            function checkStockBeforeBuy(globalKey, carName) {
                fetch('${pageContext.request.contextPath}/detail/' + globalKey, {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: 'globalKey=' + encodeURIComponent(globalKey)
                })
                        .then(res => res.json())
                        .then(data => {
                            if (data.available) {
                                window.location.href = '${pageContext.request.contextPath}/payment/' + globalKey;
                            } else {
                                document.getElementById('outOfStockMessage').innerHTML =
                                        'Sản phẩm <strong>' + carName + '</strong> hiện tại đang <strong>hết hàng</strong>.<br>Xin quý khách vui lòng thông cảm. Chúng tôi sẽ cố gắng bổ sung sớm nhất!';
                                document.getElementById('outOfStockModal').style.display = 'block';
                            }
                        })
                        .catch(err => {
                            alert("Không thể kiểm tra tồn kho. Vui lòng thử lại sau.");
                        });
            }

            function closeOutOfStockModal() {
                document.getElementById('outOfStockModal').style.display = 'none';
            }
        </script>
    </body>
</html>
