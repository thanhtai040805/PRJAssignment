package paymentmethodDAO;

import jakarta.persistence.EntityManager;
import model.PaymentMethod;

public class PaymentMethodDAO extends util.GenericDAO<PaymentMethod, Integer> {

    private EntityManager em;

    @Override
    protected Class<PaymentMethod> getEntityClass() {
        return PaymentMethod.class;
    }

}
