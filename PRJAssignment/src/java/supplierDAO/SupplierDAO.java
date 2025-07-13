package supplierDAO;

import model.Supplier;
import util.GenericDAO;

public class SupplierDAO extends GenericDAO<Supplier, Integer> {

    @Override
    protected Class<Supplier> getEntityClass() {
        return Supplier.class;
    }
}
