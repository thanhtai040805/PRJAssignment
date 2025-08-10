package controller;

import invoiceDAO.InvoiceDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Invoice;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/employee/loadInvoices")
public class LoadPendingInvoicesServlet extends HttpServlet {

    private InvoiceDAO invoiceDAO;
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        invoiceDAO = new InvoiceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("[DEBUG] LoadPendingInvoicesServlet is running...");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("loggedInUser");
        System.out.println("[DEBUG] Logged-in user: " + user.getUsername() + " | Role: " + user.getRole());

        EntityManager em = emf.createEntityManager();
        invoiceDAO.setEntityManager(em);

        List<Invoice> invoices = null;

        try {
            String role = user.getRole().toLowerCase();
            System.out.println("userId khi tìm hóa đơn: " + user.getUserId());

            if (role.equals("employee") || role.equals("nhanvien")) {
                invoices = invoiceDAO.findByEmployeeAndStatus(user.getEmployeeId(), "Chờ xử lý");
                System.out.println("[DEBUG] Tổng số hóa đơn tìm thấy: " + (invoices != null ? invoices.size() : 0));
                for (Invoice invoice : invoices) {
                    System.out.println("[DEBUG] InvoiceID: " + invoice.getInvoiceId()
                            + ", EmployeeID: " + invoice.getEmployeeId()
                            + ", Status: " + invoice.getStatus());
                }
                System.out.println("[DEBUG] Found " + (invoices != null ? invoices.size() : 0) + " invoices for employee.");
            } else if ("admin".equalsIgnoreCase(user.getRole())) {
                invoices = invoiceDAO.findByStatus("Chờ xử lý");
                System.out.println("[DEBUG] Found " + (invoices != null ? invoices.size() : 0) + " invoices for admin.");
            }

            request.setAttribute("pendingInvoices", invoices);
            request.getRequestDispatcher("/employee/pendingInvoices.jsp").forward(request, response);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}