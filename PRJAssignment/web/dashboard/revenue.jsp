<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <title>📊 Doanh thu cửa hàng</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            body {
                font-family: Arial;
                margin: 30px;
            }
            h2 {
                text-align: center;
            }
            .year-buttons {
                text-align: center;
                margin: 20px 0;
            }
            .year-buttons form {
                display: inline;
            }
            .year-buttons button {
                margin: 5px;
                padding: 10px 15px;
                font-size: 14px;
                cursor: pointer;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 30px;
            }
            th, td {
                border: 1px solid #ccc;
                padding: 8px;
                text-align: center;
            }
            th {
                background: #f2f2f2;
            }
            canvas {
                max-width: 1000px;
                height: 400px;
                margin: 40px auto;
                display: block;
            }
            .summary {
                text-align: center;
                font-size: 18px;
                margin-top: 30px;
                color: green;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/header.jsp" />
        <h2>📊 Thống kê doanh thu cửa hàng</h2>

        <!-- Chọn năm -->
        <div class="year-buttons">
            <c:forEach var="y" items="${allYears}">
                <form method="get" action="">
                    <input type="hidden" name="year" value="${y}"/>
                    <button type="submit" style="${y == selectedYear ? 'background:#007bff; color:white;' : ''}">
                        Năm ${y}
                    </button>
                </form>
            </c:forEach>
        </div>

        <!-- Biểu đồ -->
        <h3 style="text-align:center">
            📈 Doanh thu theo tháng
            <c:if test="${not empty selectedYear}">
                năm ${selectedYear}
            </c:if>
        </h3>
        <canvas id="revenueChart"></canvas>

        <!-- Doanh thu thực tế -->
        <p class="summary">
            💰 Doanh thu thực tế sau khi trừ giá gốc và hoa hồng:
            <fmt:formatNumber value="${totalRealRevenue}" type="currency" currencySymbol="₫"/>
        </p>

        <!-- Bảng hóa đơn -->
        <h3 style="text-align:center; margin-top: 50px;">🧾 Lịch sử giao dịch</h3>
        <p class="summary">
            📄 Tổng số giao dịch: ${fn:length(invoices)}
        </p>
        <table>
            <thead>
                <tr>
                    <th>Mã hóa đơn</th>
                    <th>Ngày</th>
                    <th>Khách hàng</th>
                    <th>Tổng tiền</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="i" items="${invoices}">
                    <tr>
                        <td>${i.invoiceId}</td>
                        <td><fmt:formatDate value="${i.invoiceDate}" pattern="dd/MM/yyyy"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty i.customer}">
                                    ${i.customer.fullName}
                                </c:when>
                                <c:otherwise>
                                    (không có tên)
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatNumber value="${i.totalAmount}" type="currency" currencySymbol="₫"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Chart.js Script -->
        <script>
            const revenueLabels = [
            <c:forEach var="entry" items="${revenueByMonth}" varStatus="loop">
            "${entry.key}"<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
            ];

            const revenueData = [
            <c:forEach var="entry" items="${revenueByMonth}" varStatus="loop">
                ${entry.value}<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
            ];

            new Chart(document.getElementById("revenueChart"), {
                type: 'line',
                data: {
                    labels: revenueLabels,
                    datasets: [{
                            label: 'Doanh thu theo tháng (VND)',
                            data: revenueData,
                            fill: false,
                            borderColor: '#007bff',
                            tension: 0.1,
                            pointBackgroundColor: '#007bff',
                            pointRadius: 5
                        }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        tooltip: {
                            callbacks: {
                                label: function (context) {
                                    return new Intl.NumberFormat('vi-VN', {
                                        style: 'currency',
                                        currency: 'VND'
                                    }).format(context.parsed.y);
                                }
                            }
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                callback: function (value) {
                                    return value.toLocaleString("vi-VN") + ' ₫';
                                }
                            }
                        }
                    }
                }
            });
        </script>
    </body>
</html>