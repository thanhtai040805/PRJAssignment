package employeeDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.Employee;
import util.GenericDAO;

public class EmployeeDAO extends GenericDAO<Employee, Integer> {

    private EntityManager em;

    @Override
    protected Class<Employee> getEntityClass() {
        return Employee.class;
    }
}
