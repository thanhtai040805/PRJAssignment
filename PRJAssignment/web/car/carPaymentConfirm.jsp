<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Xác nhận thông tin đặt hàng</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/car/confirm.css" />
    </head>
    <body>
        <jsp:include page="/header.jsp" />

        <div class="confirmation-box">
            <h2>Xác nhận thông tin đặt hàng</h2>
            <p style="text-align: center;">Vui lòng kiểm tra lại thông tin đặt xe.</p>

            <div class="info-box">
                <div class="section-title">Thông tin khách hàng</div>
                <p><span class="label">Họ tên:</span> ${customerName}</p>
                <p><span class="label">Email:</span> ${email}</p>
                <p><span class="label">Số điện thoại:</span> ${phone}</p>
                <p><span class="label">Địa chỉ:</span> ${address}</p>
            </div>

            <div class="info-box">
                <div class="section-title">Thông tin xe & hóa đơn</div>
                <p><span class="label">Tên xe:</span> ${carName}</p>
                <p><span class="label">Giá bán:</span> 
                    <fmt:formatNumber value="${price}" type="currency" currencyCode="VND" pattern="#,###"/> VND
                </p>
                <p><span class="label">Phương thức thanh toán:</span> ${paymentMethodName}</p>
            </div>

            <div class="info-box">
                <div class="section-title">Thông tin bổ sung</div>
                <p><span class="label">Nhân viên tư vấn:</span> ${employeeName}</p>
                <p><span class="label">Ngày hẹn tư vấn / mua xe:</span> ${appointmentDate}</p>
            </div>

            <div class="info-box">
                <div class="section-title">Chi tiết thanh toán</div>
                <c:choose>
                    <c:when test="${paymentMethodName eq 'Tiền mặt'}">
                        <p><strong>Thanh toán bằng Tiền mặt:</strong></p>
                        <p><strong>Tổng số tiền:</strong>
                            <fmt:formatNumber value="${price}" type="currency" currencyCode="VND" pattern="#,###"/> VND
                        </p>
                    </c:when>
                    <c:when test="${paymentMethodName eq 'Chuyển khoản'}">
                        <p><strong>Tài khoản và thông tin được dùng để thực hiện giao dịch:</strong></p>
                        <ul>
                            <li>Ngân hàng: Vietcombank</li>
                            <li>Tên tài khoản: Công ty TNHH ABC</li>
                            <li>Số tài khoản: 123 456 789</li>
                            <li>Nội dung: Thanh toán xe #${carName} - ${customerName}</li>
                        </ul>
                    </c:when>
                    <c:when test="${paymentMethodName eq 'Thẻ tín dụng'}">
                        <p><strong>Thanh toán bằng Thẻ tín dụng:</strong></p>
                        <form>
                            <label>Số thẻ:</label><br/>
                            <input type="text" placeholder="•••• •••• •••• ••••"><br/>
                            <label>Tên chủ thẻ:</label><br/>
                            <input type="text" placeholder="Tên in trên thẻ"><br/>
                            <label>Ngày hết hạn:</label><br/>
                            <input type="text" placeholder="MM/YY"><br/>
                            <label>CSC:</label><br/>
                            <input type="password" placeholder="***"><br/>
                        </form>
                    </c:when>
                    <c:when test="${paymentMethodName eq 'Trả góp vay ngân hàng'}">
                        <p><strong>Trả góp qua ngân hàng:</strong> Chọn ngân hàng hỗ trợ:</p>
                        <div style="display: flex; flex-wrap: wrap;">
                            <c:forEach var="bank" items="${['Vietcombank','VietinBank','BIDV','ACB','TPBank','MB Bank','Techcombank','VP Bank','Sacombank','OCB']}">
                                <button type="button" class="bank-btn">${bank}</button>
                            </c:forEach>
                        </div>
                        <label>Nhập số thẻ ngân hàng:</label><br/>
                        <input type="text" placeholder="•••• •••• •••• ••••"><br/>
                    </c:when>
                    <c:otherwise>
                        <p><em>Hình thức thanh toán không xác định.</em></p>
                    </c:otherwise>
                </c:choose>
            </div>

            <div style="border: 1px solid #ccc; border-left: 5px solid #4CAF50; padding: 16px; margin: 20px 0; background-color: #f9f9f9; border-radius: 8px;">
                <h3 style="margin-top: 0; color: #333;">Thông báo từ Showroom</h3>
                <p>
                    Vào ngày <strong>${appointmentDate}</strong>, nhân viên <strong>${employeeName}</strong> sẽ có mặt tại địa chỉ
                    <strong>${address}</strong> để trao đổi và hoàn tất các thủ tục liên quan đến hợp đồng.
                </p>
                <p>
                    Quý khách vui lòng chuẩn bị sẵn số tiền còn lại để việc thanh toán và ký kết diễn ra nhanh chóng và thuận tiện.
                </p>
                <p><em>Trân trọng cảm ơn!</em></p>
            </div>

            <div class="btn-group">
                <form action="${pageContext.request.contextPath}/invoice" method="post">
                    <input type="hidden" name="confirm" value="1"/>
                    <input type="hidden" name="carId" value="${carId}" />
                    <input type="hidden" name="carName" value="${carName}" />
                    <input type="hidden" name="price" value="${price}" />
                    <input type="hidden" name="customerName" value="${customerName}" />
                    <input type="hidden" name="email" value="${email}" />
                    <input type="hidden" name="phone" value="${phone}" />
                    <input type="hidden" name="address" value="${address}" />
                    <input type="hidden" name="paymentMethodId" value="${paymentMethodId}" />
                    <input type="hidden" name="employeeId" value="${employeeId}" />
                    <input type="hidden" name="employeeName" value="${employeeName}" />
                    <input type="hidden" name="appointmentDate" value="${appointmentDate}" />

                    <div style="text-align: center; margin-top: 30px;">
                        <button type="submit" class="btn-success">Xác nhận & Đặt hàng</button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            const bankButtons = document.querySelectorAll(".bank-btn");
            bankButtons.forEach(button => {
                button.addEventListener("click", () => {
                    bankButtons.forEach(btn => btn.classList.remove("selected"));
                    button.classList.add("selected");
                });
            });
        </script>

    </body>
</html>
