<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Xác nhận thông tin thanh toán</title>
        <meta charset="UTF-8">
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f1f3f5;
                padding: 30px;
                color: #343a40;
            }
            .confirmation-box {
                background: #fff;
                border-radius: 8px;
                padding: 30px;
                box-shadow: 0 8px 20px rgba(0,0,0,0.1);
                max-width: 800px;
                margin: 0 auto;
            }
            h2 {
                color: #007bff;
                margin-bottom: 20px;
            }
            .label {
                font-weight: bold;
                color: #6c757d;
            }
            p {
                margin: 8px 0;
            }
            .section-title {
                margin-top: 30px;
                color: #495057;
                font-size: 18px;
                border-bottom: 1px solid #dee2e6;
                padding-bottom: 5px;
            }
            .btn-group {
                margin-top: 30px;
            }
            .btn {
                display: inline-block;
                padding: 10px 20px;
                margin-right: 15px;
                background: #28a745;
                color: white;
                border-radius: 5px;
                text-decoration: none;
            }
            .btn.cancel {
                background: #6c757d;
            }
            .btn:hover {
                opacity: 0.9;
            }
            .bank-btn {
                background-color: #e9ecef;
                border: 1px solid #ced4da;
                border-radius: 5px;
                padding: 10px 15px;
                font-size: 14px;
                cursor: pointer;
                margin: 5px;
                transition: 0.2s ease;
            }
            .bank-btn:hover {
                background-color: #dee2e6;
            }
            input[type="text"], input[type="password"] {
                width: 300px;
                padding: 8px;
                margin: 5px 0 15px;
                border-radius: 4px;
                border: 1px solid #ccc;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/header.jsp" />
        <div class="confirmation-box">
            <h2>Xác nhận thông tin thanh toán</h2>
            <p>Vui lòng kiểm tra lại thông tin hóa đơn trước khi tiếp tục thanh toán.</p>

            <div class="section-title">Thông tin khách hàng</div>
            <p><span class="label">Họ tên:</span> ${customerName}</p>
            <p><span class="label">Email:</span> ${email}</p>
            <p><span class="label">Số điện thoại:</span> ${phone}</p>
            <p><span class="label">Địa chỉ:</span> ${address}</p>

            <div class="section-title">Thông tin xe & hóa đơn</div>
            <p><span class="label">Tên xe:</span> ${carName}</p>
            <p><span class="label">Giá bán:</span> 
                <fmt:formatNumber value="${price}" type="currency" currencyCode="VND" pattern="#,###"/> VND
            </p>
            <p><span class="label">Phương thức thanh toán:</span> ${paymentMethodName}</p>

            <div class="section-title">Chi tiết thanh toán</div>
            <c:choose>
                <c:when test="${paymentMethodName eq 'Tiền mặt'}">
                    <h4>Thanh toán bằng Tiền mặt</h4>
                    <p>Vui lòng đến showroom để thanh toán toàn bộ số tiền.</p>
                    <p><strong>Tổng số tiền cần thanh toán:</strong> 
                        <fmt:formatNumber value="${price}" type="currency" currencyCode="VND" pattern="#,###"/> VND
                    </p>
                </c:when>

                <c:when test="${paymentMethodName eq 'Chuyển khoản'}">
                    <h4>Thanh toán bằng Chuyển khoản</h4>
                    <p>Vui lòng chuyển khoản vào tài khoản dưới đây:</p>
                    <ul>
                        <li>Ngân hàng: Vietcombank</li>
                        <li>Tên tài khoản: Công ty TNHH ABC</li>
                        <li>Số tài khoản: 123 456 789</li>
                        <li>Nội dung chuyển khoản: Thanh toán xe #${carName} - ${customerName}</li>
                    </ul>
                    <p><strong>Tổng tiền:</strong> 
                        <fmt:formatNumber value="${price}" type="currency" currencyCode="VND" pattern="#,###"/> VND
                    </p>
                </c:when>

                <c:when test="${paymentMethodName eq 'Thẻ tín dụng'}">
                    <h4>Thanh toán bằng Thẻ tín dụng</h4>
                    <form>
                        <label for="cardNumber">Số thẻ:</label><br/>
                        <input type="text" id="cardNumber" name="cardNumber" maxlength="19" placeholder="•••• •••• •••• ••••" required><br/>

                        <label for="cardHolder">Tên chủ thẻ:</label><br/>
                        <input type="text" id="cardHolder" name="cardHolder" placeholder="Tên in trên thẻ" required><br/>

                        <label for="expDate">Ngày hết hạn:</label><br/>
                        <input type="text" id="expDate" name="expDate" maxlength="5" placeholder="MM/YY" required><br/>

                        <label for="cvv">CSC:</label><br/>
                        <input type="password" id="cvv" name="cvv" maxlength="3" placeholder="***" required><br/>
                    </form>
                </c:when>

                <c:when test="${paymentMethodName eq 'Trả góp ngân hàng'}">
                    <h4>Trả góp qua ngân hàng</h4>
                    <p>Chọn ngân hàng hỗ trợ trả góp:</p>
                    <div style="display: flex; flex-wrap: wrap;">
                        <c:forEach var="bank" items="${['Vietcombank','VietinBank','BIDV','ACB','TPBank','MB Bank','Techcombank','VP Bank','Sacombank','OCB']}">
                            <button type="button" class="bank-btn">${bank}</button>
                        </c:forEach>
                    </div>

                    <br/>
                    <label for="installmentCard">Nhập số thẻ ngân hàng của bạn:</label><br/>
                    <input type="text" id="installmentCard" name="installmentCard" placeholder="•••• •••• •••• ••••" required><br/>
                </c:when>

                <c:otherwise>
                    <p><em>Hình thức thanh toán không xác định. Vui lòng liên hệ để được hỗ trợ.</em></p>
                </c:otherwise>
            </c:choose>

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

                    <button type="submit" class="btn btn-success">Xác nhận & Thanh toán</button>
                </form>
            </div>
        </div>
    </body>
</html>
