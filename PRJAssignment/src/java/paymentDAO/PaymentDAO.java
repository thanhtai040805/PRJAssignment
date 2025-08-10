package paymentDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Payment;

import java.sql.SQLException;
import java.util.List;

public class PaymentDAO extends util.GenericDAO<Payment, Integer> {

    private EntityManager em;

    public PaymentDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    protected Class<Payment> getEntityClass() {
        return Payment.class;
    }

    public Payment getPaymentByInvoiceId(Integer invoiceId) {
        String jpql = "SELECT p FROM Payment p WHERE p.invoiceId = :invoiceId";
        TypedQuery<Payment> query = em.createQuery(jpql, Payment.class);
        query.setParameter("invoiceId", invoiceId);
        List<Payment> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public boolean addPayment(Payment payment) {
        try {
            em.persist(payment);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error adding payment", e);
        }
    }

    public boolean insertPaymentByCar(String carId, String customerName, String phone, String address, String email, String paymentMethod) throws SQLException {
        // Phương thức này có thể cần logic phức tạp, giữ nguyên hoặc chuyển sang JPQL nếu có entity tương ứng
        // Nếu không có entity tương ứng, có thể giữ nguyên JDBC hoặc chuyển sang native query
        throw new UnsupportedOperationException("Method not implemented with JPA, please implement as needed.");
    }
}
