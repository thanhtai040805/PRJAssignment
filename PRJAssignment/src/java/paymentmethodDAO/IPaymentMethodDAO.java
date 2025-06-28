package paymentmethodDAO;

import model.PaymentMethod;
import java.sql.SQLException;
import java.util.List;

public interface IPaymentMethodDAO {

    PaymentMethod getPaymentMethodById(Integer paymentMethodId) throws SQLException;

    Integer addPaymentMethod(PaymentMethod paymentMethod) throws SQLException;

    boolean updatePaymentMethod(PaymentMethod paymentMethod) throws SQLException;

    boolean deletePaymentMethod(Integer paymentMethodId) throws SQLException;

    List<PaymentMethod> getAllPaymentMethods() throws SQLException;
}
