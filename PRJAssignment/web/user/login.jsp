<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập - CarShop</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f2f2f2;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }

            .login-container {
                background-color: white;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                width: 350px;
            }

            .login-form h2 {
                text-align: center;
                color: #333;
                margin-bottom: 30px;
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                color: #555;
                font-weight: bold;
            }

            input[type="text"], input[type="password"] {
                width: 100%;
                padding: 12px;
                border: 1px solid #ddd;
                border-radius: 5px;
                box-sizing: border-box;
                font-size: 16px;
            }

            .login-btn {
                width: 100%;
                padding: 12px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
            }

            .login-btn:hover {
                background-color: #0056b3;
            }

            .error-message {
                color: red;
                text-align: center;
                margin-top: 10px;
            }
            .success-message {
                color: green;
                text-align: center;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <form class="login-form" action="<c:url value='/login'/>" method="post">
                <h2>Đăng nhập hệ thống</h2>

                <div class="form-group">
                    <label for="username">Tên đăng nhập:</label>
                    <input type="text" id="username" name="username" value="<c:out value="${username}"/>" required>
                </div>

                <div class="form-group">
                    <label for="password">Mật khẩu:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" class="login-btn">Đăng nhập</button>

                <c:if test="${not empty errorMessage}">
                    <div class="error-message">
                        <c:out value="${errorMessage}"/>
                    </div>
                </c:if>

                <c:if test="${param.registered eq 'true'}">
                    <div class="success-message">
                        Đăng ký tài khoản thành công! Vui lòng đăng nhập.
                    </div>
                </c:if>

                <p style="text-align: center; margin-top: 20px;">
                    Chưa có tài khoản? <a href="<c:url value='/register'/>">Đăng ký ngay</a>
                </p>
            </form>
        </div>
    </body>
</html>