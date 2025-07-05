package model;

<<<<<<< HEAD
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "HoaDonBan")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHD")
    private Integer invoiceId;

    @Column(name = "MaKH")
    private Integer customerId;

    @Column(name = "MaNV")
    private Integer employeeId;

    @Column(name = "NgayLap")
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;

    @Column(name = "TongTien")
    private Long totalAmount;

    @Column(name = "TienGiam")
    private Long discountAmount;

    @Column(name = "ThanhTien")
    private Long finalAmount;

    @Column(name = "TrangThai")
    private String status;

    @Column(name = "GhiChu")
    private String note;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InvoiceDetail> chiTietHoaDons;

    public List<InvoiceDetail> getChiTietHoaDons() {
        return chiTietHoaDons;
    }

    public void setChiTietHoaDons(List<InvoiceDetail> chiTietHoaDons) {
        this.chiTietHoaDons = chiTietHoaDons;
    }

    public Invoice() {
    }

    public Invoice(Integer invoiceId, Integer customerId, Integer employeeId, Date invoiceDate, Long totalAmount,
            Long discountAmount, Long finalAmount, String status, String note) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.invoiceDate = invoiceDate;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
        this.status = status;
        this.note = note;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Long finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

>>>>>>> origin/master
}
