package controller;

import invoiceDAO.InvoiceDAO;
import paymentDAO.PaymentDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Invoice;
import model.Payment;
import java.io.IOException;

@WebServlet("/employee/confirmInvoice")
public class ConfirmInvoiceServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        if (emf == null) {
            throw new ServletException("EntityManagerFactory is not initialized in ServletContext.");
        }
        System.out.println("[DEBUG] ConfirmInvoiceServlet initialized. EntityManagerFactory loaded.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String invoiceIdParam = request.getParameter("invoiceId");
        System.out.println("[DEBUG] doGet - invoiceIdParam: " + invoiceIdParam);

        if (invoiceIdParam == null || invoiceIdParam.isEmpty()) {
            System.out.println("[DEBUG] doGet - invoiceIdParam is null or empty. Redirecting to invoiceList.");
            response.sendRedirect(request.getContextPath() + "/employee/invoiceList");
            return;
        }
        int invoiceId;
        try {
            invoiceId = Integer.parseInt(invoiceIdParam);
            System.out.println("[DEBUG] doGet - Parsed invoiceId: " + invoiceId);
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] doGet - NumberFormatException for invoiceIdParam: " + invoiceIdParam);
            response.sendRedirect(request.getContextPath() + "/employee/invoiceList");
            return;
        }
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            invoiceDAO.setEntityManager(em);

            Invoice invoice = invoiceDAO.getById(invoiceId);
            System.out.println("[DEBUG] doGet - Retrieved invoice: " + invoice);

            if (invoice == null) {
                System.out.println("[DEBUG] doGet - Invoice is null, forwarding to error.jsp");
                request.setAttribute("error", "Không tìm thấy hóa đơn.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } else {
                request.setAttribute("invoice", invoice);
                request.getRequestDispatcher("/employee/confirmInvoice.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] doGet - Exception occurred: ");
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            System.out.println("[DEBUG] doGet - EntityManager closed");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String invoiceIdParam = request.getParameter("invoiceId");

        System.out.println("[DEBUG] doPost - action: " + action + ", invoiceIdParam: " + invoiceIdParam);

        if (invoiceIdParam == null || invoiceIdParam.isEmpty()) {
            System.out.println("[DEBUG] doPost - invoiceIdParam is null or empty. Redirecting to invoiceList.");
            response.sendRedirect(request.getContextPath() + "/employee/invoiceList");
            return;
        }

        if (action == null) {
            System.out.println("[ERROR] doPost - action parameter is null.");
            request.setAttribute("error", "Action không hợp lệ.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int invoiceId;

        try {
            invoiceId = Integer.parseInt(invoiceIdParam);
            System.out.println("[DEBUG] doPost - Parsed invoiceId: " + invoiceId);
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] doPost - NumberFormatException for invoiceIdParam: " + invoiceIdParam);
            response.sendRedirect(request.getContextPath() + "/employee/invoiceList");
            return;
        }

        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            InvoiceDAO invoiceDAO = new InvoiceDAO();
            invoiceDAO.setEntityManager(em);

            Invoice invoice = invoiceDAO.getById(invoiceId);
            
            PaymentDAO paymentDAO = new PaymentDAO(em);
            Payment payment = paymentDAO.getPaymentByInvoiceId(invoiceId);

            if (invoice == null) {
                System.out.println("[DEBUG] doPost - Invoice is null, forwarding to error.jsp");
                request.setAttribute("error", "Không tìm thấy hóa đơn.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            switch (action.toLowerCase()) {
                case "confirm":
                    invoice.setStatus("Đã xác nhận");
                    System.out.println("[DEBUG] doPost - Set status to 'Đã xác nhận'");
                    break;
                case "delivered":
                    invoice.setStatus("Đã giao");
                    payment.setStatus("Thành công");
                    System.out.println("[DEBUG] doPost - Set status to 'Đã giao'");
                    break;
                case "cancelled":
                    invoice.setStatus("Đã hủy");
                    payment.setStatus("Thất bại");
                    System.out.println("[DEBUG] doPost - Set status to 'Đã hủy'");
                    break;
                default:
                    System.out.println("[ERROR] doPost - Unknown action: " + action);
                    request.setAttribute("error", "Thao tác không hợp lệ.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
            }

            invoiceDAO.updateInvoice(invoice);
            paymentDAO.update(payment);
            em.getTransaction().commit();

            System.out.println("[DEBUG] doPost - Invoice updated and transaction committed.");

            response.sendRedirect(request.getContextPath() + "/employee/confirmInvoice?invoiceId=" + invoiceId);
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("[ERROR] doPost - Exception occurred: ");
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            System.out.println("[DEBUG] doPost - EntityManager closed");
        }
    }
}


