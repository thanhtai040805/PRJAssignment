<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thanh toán thành công</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f0f2f5;
                text-align: center;
                padding: 50px;
            }
            .success-box {
                background: white;
                border-radius: 8px;
                padding: 30px;
                display: inline-block;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            }
            h2 {
                color: #28a745;
            }
            .btn {
                margin-top: 20px;
                display: inline-block;
                padding: 10px 20px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 5px;
            }
            .btn:hover {
                opacity: 0.9;
            }
        </style>
    </head>
    <body>
        <div class="success-box">
            <h2>🎉 Thanh toán thành công!</h2>
            <p>Cảm ơn quý khách <strong>${customerName}</strong> đã mua xe <strong>${carName}</strong>.</p>
            <p>Chúng tôi đã gửi xác nhận đến email của bạn.</p>
            <a href="${pageContext.request.contextPath}/" class="btn">🏠 Về trang chủ</a>
        </div>
    </body>
</html>
