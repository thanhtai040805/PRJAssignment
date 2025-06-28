package customerDAO;

import model.Customer;
import java.sql.SQLException;
import java.util.List;

public interface ICustomerDAO {

    Customer getCustomerById(Integer customerId) throws SQLException;

    Integer addCustomer(Customer customer) throws SQLException; // Trả về ID tự sinh

    boolean updateCustomer(Customer customer) throws SQLException;

    boolean deleteCustomer(Integer customerId) throws SQLException;

    List<Customer> getAllCustomers() throws SQLException;
}
