package invoicedetailDAO;

<<<<<<< HEAD
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.InvoiceDetail;
import util.GenericDAO;

import java.util.List;

public class InvoiceDetailDAO extends GenericDAO<InvoiceDetail, Integer> {

    private EntityManager em;

    protected Class<InvoiceDetail> getEntityClass() {
        return InvoiceDetail.class;
    }


    public InvoiceDetail getInvoiceDetailById(Integer maCTHD) {
        String jpql = "SELECT id FROM InvoiceDetail id "
                + "LEFT JOIN FETCH id.xeOto xe "
                + "LEFT JOIN FETCH xe.dongXe dx "
                + "LEFT JOIN FETCH dx.hangXe hx "
                + "LEFT JOIN FETCH xe.nhaCungCap ncc "
                + "WHERE id.maCTHD = :maCTHD";
        TypedQuery<InvoiceDetail> query = em.createQuery(jpql, InvoiceDetail.class);
        query.setParameter("maCTHD", maCTHD);
        List<InvoiceDetail> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public List<InvoiceDetail> getInvoiceDetailsByInvoiceId(Integer maHD) {
        String jpql = "SELECT id FROM InvoiceDetail id "
                + "LEFT JOIN FETCH id.xeOto xe "
                + "LEFT JOIN FETCH xe.dongXe dx "
                + "LEFT JOIN FETCH dx.hangXe hx "
                + "LEFT JOIN FETCH xe.nhaCungCap ncc "
                + "WHERE id.maHD = :maHD";
        TypedQuery<InvoiceDetail> query = em.createQuery(jpql, InvoiceDetail.class);
        query.setParameter("maHD", maHD);
        return query.getResultList();
    }

    public boolean addInvoiceDetail(InvoiceDetail invoiceDetail) {
        try {
            em.persist(invoiceDetail);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateInvoiceDetail(InvoiceDetail invoiceDetail) {
        try {
            em.merge(invoiceDetail);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteInvoiceDetail(Integer maCTHD) {
        InvoiceDetail detail = em.find(InvoiceDetail.class, maCTHD);
        if (detail != null) {
            em.remove(detail);
            return true;
        }
        return false;
    }

    public List<InvoiceDetail> getAllInvoiceDetails() {
        String jpql = "SELECT id FROM InvoiceDetail id";
        return em.createQuery(jpql, InvoiceDetail.class).getResultList();
>>>>>>> origin/master
    }
}
