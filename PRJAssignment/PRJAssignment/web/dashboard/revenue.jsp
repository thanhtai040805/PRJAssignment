<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>📊 Doanh thu cửa hàng</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body { font-family: Arial; margin: 30px; }
        h2 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-top: 30px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background: #f2f2f2; }
        canvas { max-width: 800px; margin: 40px auto; display: block; }
    </style>
</head>
<body>

<h2>📊 Thống kê doanh thu cửa hàng</h2>

<h3 style="text-align:center">🔢 Tổng hợp theo tháng</h3>
<canvas id="revenueChart"></canvas>

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
                <td>${i.customerName}</td>
                <td><fmt:formatNumber value="${i.totalAmount}" type="currency"/></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<script>
    const revenueByMonth = {
        labels: [
            <c:forEach var="entry" items="${revenueByMonth}">
                "${entry.key}",
            </c:forEach>
        ],
        data: [
            <c:forEach var="entry" items="${revenueByMonth}">
                ${entry.value},
            </c:forEach>
        ]
    };

    new Chart(document.getElementById("revenueChart"), {
        type: 'bar',
        data: {
            labels: revenueByMonth.labels,
            datasets: [{
                label: 'Doanh thu theo tháng (VND)',
                data: revenueByMonth.data,
                backgroundColor: '#007bff'
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
</script>

</body>
</html>
