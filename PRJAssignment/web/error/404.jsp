<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Trang không tồn tại | DriveDreams</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            color: white;
        }
        
        .error-container {
            text-align: center;
            max-width: 600px;
            padding: 40px 20px;
        }
        
        .error-icon {
            font-size: 120px;
            margin-bottom: 20px;
            animation: bounce 2s infinite;
        }
        
        @keyframes bounce {
            0%, 20%, 50%, 80%, 100% {
                transform: translateY(0);
            }
            40% {
                transform: translateY(-10px);
            }
            60% {
                transform: translateY(-5px);
            }
        }
        
        .error-code {
            font-size: 72px;
            font-weight: bold;
            margin-bottom: 20px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
        }
        
        .error-title {
            font-size: 32px;
            margin-bottom: 20px;
            font-weight: 300;
        }
        
        .error-message {
            font-size: 18px;
            line-height: 1.6;
            margin-bottom: 40px;
            color: rgba(255,255,255,0.9);
        }
        
        .error-actions {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
            margin-bottom: 40px;
        }
        
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 25px;
            font-size: 16px;
            text-decoration: none;
            cursor: pointer;
            transition: all 0.3s ease;
            display: inline-block;
        }
        
        .btn-primary {
            background: white;
            color: #667eea;
            font-weight: bold;
        }
        
        .btn-primary:hover {
            background: #f8f9fa;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }
        
        .btn-secondary {
            background: rgba(255,255,255,0.2);
            color: white;
            border: 2px solid rgba(255,255,255,0.3);
        }
        
        .btn-secondary:hover {
            background: rgba(255,255,255,0.3);
            border-color: rgba(255,255,255,0.5);
            transform: translateY(-2px);
        }
        
        .search-box {
            margin-bottom: 30px;
        }
        
        .search-form {
            display: flex;
            max-width: 400px;
            margin: 0 auto;
            gap: 10px;
        }
        
        .search-input {
            flex: 1;
            padding: 12px 20px;
            border: none;
            border-radius: 25px;
            font-size: 16px;
            outline: none;
        }
        
        .search-btn {
            padding: 12px 20px;
            background: rgba(255,255,255,0.2);
            color: white;
            border: 2px solid rgba(255,255,255,0.3);
            border-radius: 25px;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        
        .search-btn:hover {
            background: rgba(255,255,255,0.3);
            border-color: rgba(255,255,255,0.5);
        }
        
        .helpful-links {
            margin-top: 30px;
        }
        
        .helpful-links h3 {
            margin-bottom: 15px;
            font-size: 20px;
        }
        
        .links-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            max-width: 500px;
            margin: 0 auto;
        }
        
        .link-item {
            background: rgba(255,255,255,0.1);
            padding: 15px;
            border-radius: 10px;
            text-decoration: none;
            color: white;
            transition: all 0.3s ease;
            display: block;
        }
        
        .link-item:hover {
            background: rgba(255,255,255,0.2);
            transform: translateY(-2px);
        }
        
        .link-icon {
            font-size: 24px;
            margin-bottom: 10px;
            display: block;
        }
        
        .link-title {
            font-weight: bold;
            margin-bottom: 5px;
        }
        
        .link-desc {
            font-size: 14px;
            color: rgba(255,255,255,0.8);
        }
        
        .back-to-top {
            position: fixed;
            bottom: 30px;
            right: 30px;
            background: rgba(255,255,255,0.2);
            color: white;
            border: none;
            border-radius: 50%;
            width: 50px;
            height: 50px;
            font-size: 20px;
            cursor: pointer;
            transition: all 0.3s ease;
            opacity: 0.7;
        }
        
        .back-to-top:hover {
            background: rgba(255,255,255,0.3);
            transform: translateY(-2px);
            opacity: 1;
        }
        
        /* Responsive */
        @media (max-width: 768px) {
            .error-code {
                font-size: 48px;
            }
            
            .error-title {
                font-size: 24px;
            }
            
            .error-message {
                font-size: 16px;
            }
            
            .error-actions {
                flex-direction: column;
                align-items: center;
            }
            
            .search-form {
                flex-direction: column;
                max-width: 300px;
            }
            
            .links-grid {
                grid-template-columns: 1fr;
            }
        }
        
        /* Animation for car icon */
        .car-animation {
            font-size: 80px;
            margin: 20px 0;
            animation: drive 3s linear infinite;
        }
        
        @keyframes drive {
            0% {
                transform: translateX(-100px);
            }
            50% {
                transform: translateX(100px);
            }
            100% {
                transform: translateX(-100px);
            }
        }
    </style>
</head>
<body>
    <div class="error-container">
        <!-- Error Icon -->
        <div class="error-icon">🚗💨</div>
        
        <!-- Error Code -->
        <div class="error-code">404</div>
        
        <!-- Error Title -->
        <h1 class="error-title">Oops! Trang không tồn tại</h1>
        
        <!-- Error Message -->
        <div class="error-message">
            <p>Có vẻ như chiếc xe bạn đang tìm kiếm đã lạc đường rồi!</p>
            <p>Trang web bạn đang tìm kiếm có thể đã bị di chuyển, xóa hoặc không bao giờ tồn tại.</p>
        </div>
        
        <!-- Action Buttons -->
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                🏠 Về Trang Chính
            </a>
            <a href="javascript:history.back()" class="btn btn-secondary">
                ⬅️ Trở Về
            </a>
        </div>
        
        <!-- Search Box -->
        <div class="search-box">
            <form class="search-form" action="${pageContext.request.contextPath}/search" method="get">
                <input type="text" class="search-input" name="keyword" placeholder="Tìm kiếm xe, hãng, dòng xe...">
                <button type="submit" class="search-btn">🔍 Tìm</button>
            </form>
        </div>
        
        <!-- Car Animation -->
        <div class="car-animation">🚙</div>
        
        <!-- Helpful Links -->
        <div class="helpful-links">
            <h3>Hoặc bạn có thể khám phá:</h3>
            <div class="links-grid">
                <a href="${pageContext.request.contextPath}/cars" class="link-item">
                    <span class="link-icon">🚗</span>
                    <div class="link-title">Danh sách xe</div>
                    <div class="link-desc">Xem tất cả xe có sẵn</div>
                </a>
                
                <a href="${pageContext.request.contextPath}/brands" class="link-item">
                    <span class="link-icon">🏭</span>
                    <div class="link-title">Hãng xe</div>
                    <div class="link-desc">Khám phá các thương hiệu</div>
                </a>
                
                <a href="${pageContext.request.contextPath}/services" class="link-item">
                    <span class="link-icon">🔧</span>
                    <div class="link-title">Dịch vụ</div>
                    <div class="link-desc">Bảo trì và sửa chữa</div>
                </a>
                
                <a href="${pageContext.request.contextPath}/contact" class="link-item">
                    <span class="link-icon">📞</span>
                    <div class="link-title">Liên hệ</div>
                    <div class="link-desc">Hỗ trợ khách hàng</div>
                </a>
            </div>
        </div>
    </div>
    
    <!-- Back to top button -->
    <button class="back-to-top" onclick="window.scrollTo(0,0)">↑</button>
    
    <script>
        // Auto-focus search input
        document.addEventListener('DOMContentLoaded', function() {
            const searchInput = document.querySelector('.search-input');
            if (searchInput) {
                setTimeout(() => {
                    searchInput.focus();
                }, 1000);
            }
        });
        
        // Add some interactivity
        document.querySelectorAll('.link-item').forEach(item => {
            item.addEventListener('mouseenter', function() {
                this.style.transform = 'translateY(-3px) scale(1.02)';
            });
            
            item.addEventListener('mouseleave', function() {
                this.style.transform = 'translateY(0) scale(1)';
            });
        });
        
        // Console easter egg
        console.log('🚗 Chào mừng đến với DriveDreams! Có vẻ như bạn đã lạc đường rồi. Đừng lo, chúng tôi sẽ giúp bạn tìm được chiếc xe mơ ước! 🚗');
    </script>
</body>
</html>
