package paymentmethodDAO;

import jakarta.persistence.EntityManager;
import java.util.List;
import model.PaymentMethod;

public class PaymentMethodDAO extends util.GenericDAO<PaymentMethod, Integer> {

    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    protected Class<PaymentMethod> getEntityClass() {
        return PaymentMethod.class;
    }

    public List<PaymentMethod> findAll() {
        return em.createQuery("SELECT p FROM PaymentMethod p", PaymentMethod.class)
                .getResultList();
    }

    public int getIdByName(String methodName) {
        return em.createQuery(
                "SELECT p.paymentMethodId FROM PaymentMethod p WHERE p.paymentMethodName = :name",
                Integer.class)
                .setParameter("name", methodName)
                .getSingleResult();
    }

    public PaymentMethod findById(int id) {
        return em.find(PaymentMethod.class, id);
    }

    public List<PaymentMethod> getAll() {
        return findAll();
    }
}
