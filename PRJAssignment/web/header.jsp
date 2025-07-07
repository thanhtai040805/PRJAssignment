<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
    .header {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        padding: 15px 0;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    }
    .header-content {
        max-width: 1200px;
        margin: 0 auto;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 20px;
    }
    .logo {
        display: flex;
        align-items: center;
        gap: 10px;
    }
    .logo a {
        text-decoration: none !important;
        color: inherit;
        display: flex;
        align-items: center;
    }
    .logo h1 {
        color: white;
        font-size: 28px;
        font-weight: 300;
        font-style: italic;
        margin: 0;
    }
    .logo-icon {
        width: 40px;
        height: 40px;
        background: rgba(255,255,255,0.2);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-size: 20px;
    }
    .auth-links {
        display: flex;
        gap: 15px;
        align-items: center;
    }
    .auth-links a {
        color: white;
        text-decoration: none;
        padding: 8px 16px;
        border-radius: 20px;
        transition: background-color 0.3s;
        font-weight: 500;
        display: flex;
        align-items: center;
        gap: 6px;
    }
    .auth-links a:hover {
        background-color: rgba(255,255,255,0.2);
        text-decoration: none;
        color: white;
    }
    .auth-links .favorite-link {
        background: #fff;
        color: #ff6b6b;
        border: 1px solid #ff6b6b;
        padding: 8px 18px;
        border-radius: 25px;
        font-weight: 600;
        transition: background 0.2s, color 0.2s;
        box-shadow: 0 2px 8px rgba(255,107,107,0.08);
    }
    .auth-links .favorite-link:hover {
        background: #ff6b6b;
        color: #fff;
    }
    .auth-links .history-link {
        background: #fff;
        color: #007bff;
        border: 1px solid #007bff;
        padding: 8px 18px;
        border-radius: 25px;
        font-weight: 600;
        transition: background 0.2s, color 0.2s;
        box-shadow: 0 2px 8px rgba(0,123,255,0.08);
        margin-left: 5px;
    }
    .auth-links .history-link:hover {
        background: #007bff;
        color: #fff;
    }
    .auth-links span {
        color: white;
        font-weight: 300;
        font-size: 14px;
    }
    @media (max-width: 768px) {
        .header-content {
            flex-direction: column;
            gap: 10px;
        }
        .logo h1 {
            font-size: 24px;
        }
        .auth-links {
            flex-direction: column;
            gap: 10px;
        }
        .auth-links .favorite-link,
        .auth-links .history-link {
            width: 100%;
            justify-content: center;
        }
    }
</style>

<header class="header">
    <div class="header-content">
        <div class="logo">
            <a href="${pageContext.request.contextPath}">
                <div class="logo-icon">🚗</div>
                <h1>DriveDreams</h1>
            </a>
        </div>
        <div class="auth-links">
            <a href="${pageContext.request.contextPath}/favoriteCar" class="favorite-link" title="Xe yêu thích">
                <span style="font-size: 18px;">❤️</span> Xe yêu thích
            </a>
            <a href="${pageContext.request.contextPath}/viewedCars" class="history-link" title="Lịch sử đã xem">
                <span style="font-size: 18px;">🕑</span> Lịch sử đã xem
            </a>
            <c:choose>
                <c:when test="${not empty sessionScope.currentUser}">
                    <span>Xin chào, ${sessionScope.currentUser.username}!</span>
                    <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                    <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
