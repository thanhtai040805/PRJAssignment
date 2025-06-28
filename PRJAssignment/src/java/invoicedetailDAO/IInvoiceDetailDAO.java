package invoicedetailDAO;

import model.InvoiceDetail;
import java.sql.Connection; // Để hỗ trợ transaction
import java.sql.SQLException;
import java.util.List;

public interface IInvoiceDetailDAO {

    InvoiceDetail getInvoiceDetailById(Integer invoiceDetailId) throws SQLException;

    List<InvoiceDetail> getInvoiceDetailsByInvoiceId(Integer invoiceId) throws SQLException;

    boolean addInvoiceDetail(Connection conn, InvoiceDetail invoiceDetail) throws SQLException; // Nhận Connection để transaction

    boolean updateInvoiceDetail(InvoiceDetail invoiceDetail) throws SQLException;

    boolean deleteInvoiceDetail(Integer invoiceDetailId) throws SQLException;

    List<InvoiceDetail> getAllInvoiceDetails() throws SQLException;
}
