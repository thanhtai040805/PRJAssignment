package invoiceDAO;

import model.Invoice;
import java.sql.Connection; // Để hỗ trợ transaction
import java.sql.SQLException;
import java.util.List;

public interface IInvoiceDAO {

    Invoice getInvoiceById(Integer invoiceId) throws SQLException;

    Invoice getInvoiceFullInfo(Integer invoiceId) throws SQLException; // Lấy cả chi tiết và thông tin liên quan

    Integer addInvoice(Connection conn, Invoice invoice) throws SQLException; // Nhận Connection để transaction

    boolean updateInvoice(Invoice invoice) throws SQLException;

    boolean deleteInvoice(Integer invoiceId) throws SQLException;

    List<Invoice> getAllInvoices() throws SQLException;
}
