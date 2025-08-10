package invoiceDAO;
import jakarta.persistence.EntityManager;
import model.Invoice;
import java.util.List;

public class InvoiceDAO {

    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public Integer addInvoice(Invoice invoice) {
        try {
            em.persist(invoice);
            em.flush();
            return invoice.getInvoiceId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateInvoice(Invoice invoice) {
        try {
            em.merge(invoice);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteInvoice(Integer invoiceId) {
        try {
            Invoice invoice = em.find(Invoice.class, invoiceId);
            if (invoice != null) {
                em.remove(invoice);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Invoice> getAllInvoices() {
        String jpql = "SELECT hd FROM Invoice hd";
        return em.createQuery(jpql, Invoice.class).getResultList();
    }

    public List<Invoice> findByEmployeeAndStatus(int employeeId, String status) {
        String jpql = "SELECT i FROM Invoice i WHERE i.employeeId = :employeeId AND i.status = :status";
        return em.createQuery(jpql, Invoice.class)
                .setParameter("employeeId", employeeId)
                .setParameter("status", status)
                .getResultList();
    }

    public List<Invoice> findByStatus(String status) {
        String jpql = "SELECT i FROM Invoice i WHERE i.status = :status";
        return em.createQuery(jpql, Invoice.class)
                .setParameter("status", status)
                .getResultList();
    }
    
    public Invoice getById(Integer invoiceId) {
    try {
        return em.find(Invoice.class, invoiceId);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

}
