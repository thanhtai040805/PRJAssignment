package customerDAO;

import jakarta.persistence.EntityManager;
import model.Customer;
import util.GenericDAO;

public class CustomerDAO extends GenericDAO<Customer, Integer> {

    private EntityManager em;

    @Override
    protected Class<Customer> getEntityClass() {
        return Customer.class;
    }
}
