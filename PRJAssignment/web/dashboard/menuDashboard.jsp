<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Menu Dashboard</title>
        <style>
            body {
                margin: 0;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: linear-gradient(135deg, #dfe9f3 0%, #ffffff 100%);
                min-height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .dashboard-wrapper {
                background-color: #ffffff;
                padding: 40px 30px;
                border-radius: 16px;
                box-shadow: 0 8px 24px rgba(0,0,0,0.1);
                max-width: 400px;
                width: 100%;
            }

            h2 {
                text-align: center;
                color: #333;
                margin-bottom: 30px;
            }

            .menu-item {
                background: #f9f9f9;
                border: 2px solid #e0e0e0;
                border-radius: 12px;
                padding: 15px 20px;
                margin-bottom: 20px;
                text-align: center;
                transition: all 0.3s ease;
            }

            .menu-item:hover {
                background: #f0f8ff;
                border-color: #007bff;
                transform: translateY(-2px);
            }

            .menu-item a {
                text-decoration: none;
                color: #333;
                font-size: 18px;
                font-weight: 600;
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 10px;
            }

            .menu-item a span.icon {
                font-size: 22px;
            }
        </style>
    </head>
    <body>
        <div class="dashboard-wrapper">
            <h2>📋 Menu Dashboard</h2>

            <div class="menu-item">
                <a href="${pageContext.request.contextPath}/dashboard/car">
                    <span class="icon">🚗</span> Quản lý xe
                </a>
            </div>

            <div class="menu-item">
                <a href="${pageContext.request.contextPath}/dashboard/customer">
                    <span class="icon">👥</span> Quản lý khách hàng
                </a>
            </div>

            <div class="menu-item">
                <a href="${pageContext.request.contextPath}/dashboard/employee">
                    <span class="icon">🧑‍💼</span> Quản lý nhân viên
                </a>
            </div>

            <div class="menu-item">
                <a href="${pageContext.request.contextPath}/dashboard/revenue">
                    <span class="icon">📈</span> Doanh thu
                </a>
            </div>

        </div>
    </body>
</html>