<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Lỗi máy chủ | DriveDreams</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #ffa726 0%, #fb8c00 100%);
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
            animation: spin 2s linear infinite;
        }
        
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
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
            color: #ffa726;
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
        }
    </style>
</head>
<body>
    <div class="error-container">
        <!-- Error Icon -->
        <div class="error-icon">⚙️</div>
        
        <!-- Error Code -->
        <div class="error-code">500</div>
        
        <!-- Error Title -->
        <h1 class="error-title">Lỗi máy chủ nội bộ</h1>
        
        <!-- Error Message -->
        <div class="error-message">
            <p>Có lỗi xảy ra với máy chủ của chúng tôi!</p>
            <p>Chúng tôi đang khắc phục sự cố. Vui lòng thử lại sau.</p>
        </div>
        
        <!-- Action Buttons -->
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                🏠 Về Trang Chính
            </a>
            <a href="javascript:location.reload()" class="btn btn-secondary">
                🔄 Thử Lại
            </a>
        </div>
    </div>
</body>
</body>
</html>
