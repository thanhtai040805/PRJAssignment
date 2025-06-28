<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Car" %>
<%
    Car car = (Car) request.getAttribute("carToBuy");
    if (car == null) {
        response.sendRedirect(request.getContextPath() + "/error.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán - <%= car.getTenXe() %></title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary-color: #007bff;
            --accent-color: #28a745;
            --background-light: #f8f9fa;
            --card-background: #ffffff;
            --text-dark: #343a40;
            --text-muted: #6c757d;
            --border-color: #dee2e6;
            --shadow-light: rgba(0,0,0,0.1);
            --shadow-medium: rgba(0,0,0,0.15);
        }

        body {
            font-family: 'Poppins', sans-serif;
            background-color: var(--background-light);
            margin: 0;
            padding: 20px;
            color: var(--text-dark);
            line-height: 1.6;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            box-sizing: border-box; /* Ensures padding doesn't cause overflow */
        }

        .payment-main-container {
            max-width: 1000px; /* Increased max-width for two columns */
            width: 95%;
            background: var(--card-background);
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 10px 30px var(--shadow-medium);
            display: flex; /* Use flexbox for two columns */
            gap: 30px; /* Space between columns */
            flex-wrap: wrap; /* Allow wrapping on small screens */
        }

        .car-summary-section, .payment-form-section {
            flex: 1; /* Both sections take equal space */
            min-width: 300px; /* Minimum width before wrapping */
            padding: 20px;
            border-radius: 10px;
        }

        .car-summary-section {
            background-color: var(--background-light); /* Slightly different background for visual separation */
            border: 1px solid var(--border-color);
            box-shadow: 0 4px 15px var(--shadow-light);
            text-align: center;
        }

        .car-summary-section h2 {
            font-size: 1.8em;
            color: var(--primary-color);
            margin-bottom: 20px;
            font-weight: 700;
        }

        .car-summary-section img {
            max-width: 100%;
            height: 200px; /* Fixed height for consistency */
            object-fit: cover; /* Cover ensures the image fills the space */
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }

        .car-summary-section p {
            margin: 10px 0;
            font-size: 1.05em;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 5px;
            border-bottom: 1px dashed var(--border-color);
        }

        .car-summary-section p:last-child {
            border-bottom: none; /* No border for the last item */
            padding-bottom: 0;
        }

        .car-summary-section p strong {
            color: var(--text-muted);
            font-weight: 500;
        }

        .car-summary-section p span {
            color: var(--text-dark);
            font-weight: 600;
        }

        .car-summary-section .price {
            font-size: 1.5em;
            color: var(--accent-color);
            font-weight: 700;
            margin-top: 20px;
            justify-content: center; /* Center align price */
        }


        .payment-form-section h2 {
            font-size: 2em;
            margin-bottom: 30px;
            color: var(--primary-color);
            font-weight: 700;
            text-align: center;
        }

        .payment-form-section form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .payment-form-section label {
            font-weight: 600;
            margin-bottom: 5px;
            color: var(--text-dark);
            text-align: left;
            display: block;
        }

        .payment-form-section input[type="text"],
        .payment-form-section input[type="email"],
        .payment-form-section select {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid var(--border-color);
            border-radius: 8px;
            font-size: 1em;
            color: var(--text-dark);
            background-color: #fcfcfc;
            box-sizing: border-box;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        .payment-form-section input[type="text"]:focus,
        .payment-form-section input[type="email"]:focus,
        .payment-form-section select:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.25);
            outline: none;
        }

        .payment-form-section input[type="submit"] {
            padding: 15px 25px;
            border: none;
            border-radius: 8px;
            background: linear-gradient(to right, #4facfe, #00f2fe);
            color: white;
            font-weight: 600;
            font-size: 1.1em;
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease, background 0.3s ease;
            margin-top: 20px;
            width: 100%;
        }

        .payment-form-section input[type="submit"]:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 20px var(--shadow-medium);
            background: linear-gradient(to right, #00f2fe, #4facfe);
        }

        /* Responsive Adjustments */
        @media (max-width: 768px) {
            .payment-main-container {
                flex-direction: column; /* Stack columns on smaller screens */
                padding: 20px;
                gap: 20px;
            }
            .car-summary-section, .payment-form-section {
                padding: 15px;
                min-width: unset; /* Remove min-width to allow full stacking */
            }
            .car-summary-section h2, .payment-form-section h2 {
                font-size: 1.6em;
            }
            .car-summary-section img {
                height: 180px;
            }
        }

        @media (max-width: 480px) {
            body {
                padding: 10px;
            }
            .payment-main-container {
                padding: 15px;
            }
            .car-summary-section h2, .payment-form-section h2 {
                font-size: 1.4em;
            }
            .car-summary-section img {
                height: 150px;
            }
            .payment-form-section input[type="submit"] {
                font-size: 1em;
                padding: 12px 15px;
            }
        }
    </style>
</head>
<body>

    <div class="payment-main-container">
        <div class="car-summary-section">
            <h2>Thông tin xe</h2>
            <img src="${pageContext.request.contextPath}${carToBuy.getImageOrDefault()}" alt="Ảnh xe <%= car.getTenXe()%>">
            <p><strong>Tên xe:</strong> <span><%= car.getTenXe()%></span></p>
            <p><strong>Năm sản xuất:</strong> <span><%= car.getNamSanXuat()%></span></p>
            <p><strong>Màu sắc:</strong> <span><%= car.getMauSac()%></span></p>
            <p><strong>Số chỗ ngồi:</strong> <span><%= car.getSoChoNgoi()%></span></p>
            <p class="price"><strong>Giá bán:</strong> <span><%= car.getFormattedPrice()%></span></p>
        </div>

        <div class="payment-form-section">
            <h2>Thông tin thanh toán</h2>
            <form action="<%= request.getContextPath() %>/confirm-payment" method="post">
                <input type="hidden" name="carId" value="<%= car.getMaXe() %>"/>
                <input type="hidden" name="price" value="<%= car.getGiaBan() %>"/>
                <input type="hidden" name="carName" value="<%= car.getTenXe() %>"/>

                <label for="customerName">Họ tên:</label>
                <input type="text" id="customerName" name="customerName" required>

                <label for="phone">Số điện thoại:</label>
                <input type="text" id="phone" name="phone" required>

                <label for="address">Địa chỉ:</label>
                <input type="text" id="address" name="address" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>

                <label for="paymentMethod">Phương thức thanh toán:</label>
                <select id="paymentMethod" name="paymentMethod" required>
                    <option value="Tiền mặt">Tiền mặt</option>
                    <option value="Chuyển khoản">Chuyển khoản</option>
                    <option value="Qua ngân hàng">Qua ngân hàng</option>
                </select>

                <input type="submit" value="Xác nhận Thanh toán">
            </form>
        </div>
    </div>

</body>
</html>