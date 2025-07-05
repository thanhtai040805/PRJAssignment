package paymentDAO;

import model.Payment;
import java.sql.Connection; // Để hỗ trợ transaction
import java.sql.SQLException;
import java.util.List;

public interface IPaymentDAO {

    Payment getPaymentById(Integer paymentId) throws SQLException;

    Payment getPaymentByInvoiceId(Integer invoiceId) throws SQLException;

    boolean addPayment(Connection conn, Payment payment) throws SQLException;

    boolean updatePayment(Payment payment) throws SQLException;

    boolean deletePayment(Integer paymentId) throws SQLException;

    List<Payment> getAllPayments() throws SQLException;

    boolean insertPaymentByCar(String carId, String customerName, String phone, String address, String email, String paymentMethod) throws SQLException;
}
