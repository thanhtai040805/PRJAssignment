package invoiceDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import invoicedetailDAO.InvoiceDetailDAO;
import model.Invoice;
import model.InvoiceDetail;

import java.util.List;

public class InvoiceDAO {

    private EntityManager em;

    public Invoice getInvoiceById(Integer invoiceId) {
        return em.find(Invoice.class, invoiceId);
    }

    public Invoice getInvoiceFullInfo(Integer invoiceId) {
        String jpql = "SELECT hd FROM Invoice hd "
                + "LEFT JOIN FETCH hd.khachHang kh "
                + "LEFT JOIN FETCH hd.nhanVien nv "
                + "LEFT JOIN FETCH hd.thanhToan tt "
                + "LEFT JOIN FETCH tt.phuongThucThanhToan pttt "
                + "WHERE hd.maHD = :invoiceId";
        TypedQuery<Invoice> query = em.createQuery(jpql, Invoice.class);
        query.setParameter("invoiceId", invoiceId);
        List<Invoice> results = query.getResultList();

        if (results.isEmpty()) {
            return null;
        }

        Invoice invoice = results.get(0);
        InvoiceDetailDAO chiTietDao = new InvoiceDetailDAO();
        chiTietDao.setEntityManager(em);
        List<InvoiceDetail> chiTietHoaDons = chiTietDao.getInvoiceDetailsByInvoiceId(invoiceId);
        invoice.setChiTietHoaDons(chiTietHoaDons);

        return invoice;
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

}
