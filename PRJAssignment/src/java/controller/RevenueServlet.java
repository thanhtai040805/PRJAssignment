package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.persistence.*;

import model.Car;
import model.Invoice;
import model.InvoiceDetail;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/dashboard/revenue")
public class RevenueServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("PRJPU");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();

        try {
            // 🧾 Load tất cả hóa đơn và trigger car để tránh LazyInitializationException
            List<Invoice> invoices = em.createQuery("SELECT i FROM Invoice i", Invoice.class).getResultList();
            invoices.forEach(i -> i.getChiTietHoaDons().forEach(d -> d.getCar()));

            req.setAttribute("invoices", invoices);

            // 📅 Tìm tất cả năm có trong hóa đơn
            Set<String> allYears = invoices.stream()
                    .filter(i -> i.getInvoiceDate() != null)
                    .map(i -> new SimpleDateFormat("yyyy").format(i.getInvoiceDate()))
                    .collect(Collectors.toCollection(TreeSet::new)); // Sorted set
            req.setAttribute("allYears", allYears);

            // 📌 Lấy năm được chọn (nếu null thì lấy năm mới nhất)
            String selectedYear = req.getParameter("year");
            if ((selectedYear == null || selectedYear.isEmpty()) && !allYears.isEmpty()) {
                selectedYear = ((TreeSet<String>) allYears).last();
            }
            req.setAttribute("selectedYear", selectedYear);

            // 🗂 Lọc hóa đơn theo năm
            final String yearFilter = selectedYear;
            List<Invoice> filteredInvoices = invoices.stream()
                    .filter(i -> {
                        Date date = i.getInvoiceDate();
                        return date != null && yearFilter != null
                                && yearFilter.equals(new SimpleDateFormat("yyyy").format(date));
                    })
                    .collect(Collectors.toList());

            // 📊 Tính tổng doanh thu theo tháng
            Map<String, Long> revenueByMonth = filteredInvoices.stream()
                    .collect(Collectors.groupingBy(
                            i -> new SimpleDateFormat("MM").format(i.getInvoiceDate()),
                            TreeMap::new,
                            Collectors.summingLong(Invoice::getTotalAmount)
                    ));
            req.setAttribute("revenueByMonth", revenueByMonth);

            // 💰 Tính doanh thu thực nhận (tổng tiền bán - giá nhập - hoa hồng)
            long totalRevenue = filteredInvoices.stream()
                    .mapToLong(Invoice::getTotalAmount)
                    .sum();

            long totalImportCost = 0;
            long totalCommission = 0;

            for (Invoice invoice : filteredInvoices) {
                for (InvoiceDetail detail : invoice.getChiTietHoaDons()) {
                    Car car = detail.getCar();
                    if (car != null && car.getImportPrice() != null && detail.getQuantity() != null) {
                        totalImportCost += car.getImportPrice() * detail.getQuantity();
                        totalCommission += detail.getUnitPrice() * detail.getQuantity() * 5 / 100;
                    }
                }
            }

            long realProfit = totalRevenue - totalImportCost - totalCommission;
            req.setAttribute("totalRealRevenue", realProfit);

            // 👉 Chuyển sang JSP để hiển thị
            req.getRequestDispatcher("/dashboard/revenue.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi
            req.setAttribute("error", "Lỗi khi tải thống kê doanh thu.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        } finally {
            em.close();
        }
    }
}
