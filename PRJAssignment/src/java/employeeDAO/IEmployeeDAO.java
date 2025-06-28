package employeeDAO;

import model.Employee;
import java.sql.SQLException;
import java.util.List;

public interface IEmployeeDAO {

    Employee getEmployeeById(Integer employeeId) throws SQLException;

    Integer addEmployee(Employee employee) throws SQLException;

    boolean updateEmployee(Employee employee) throws SQLException;

    boolean deleteEmployee(Integer employeeId) throws SQLException;

    List<Employee> getAllEmployees() throws SQLException;
}
