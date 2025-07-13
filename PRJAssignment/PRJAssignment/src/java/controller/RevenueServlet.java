package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.persistence.*;

import model.Invoice;

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
            List<Invoice> invoices = em.createQuery("SELECT i FROM Invoice i", Invoice.class).getResultList();
            req.setAttribute("invoices", invoices);

            // Tính tổng doanh thu theo tháng
            Map<String, Long> revenueByMonth = invoices.stream().collect(Collectors.groupingBy(
                i -> new SimpleDateFormat("yyyy-MM").format(i.getInvoiceDate()),
                TreeMap::new,
                Collectors.summingLong(Invoice::getTotalAmount)
            ));

            req.setAttribute("revenueByMonth", revenueByMonth);
            req.getRequestDispatcher("/dashboard/revenue.jsp").forward(req, resp);

        } finally {
            em.close();
        }
    }
}
