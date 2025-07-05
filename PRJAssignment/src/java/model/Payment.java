package model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ThanhToan")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTT")
    private Integer paymentId;

    @Column(name = "MaHD")
    private Integer invoiceId;

    @Column(name = "MaPTTT")
    private Integer paymentMethodId;

    @Column(name = "SoTien")
    private Long amount;

    @Column(name = "NgayThanhToan")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column(name = "SoTaiKhoan")
    private String accountNumber;

    @Column(name = "TenNganHang")
    private String bankName;

    @Column(name = "MaGiaoDich")
    private String transactionCode;

    @Column(name = "TrangThai")
    private String status;

    @Column(name = "GhiChu")
    private String note;

    public Payment() {}

    public Payment(Integer paymentId, Integer invoiceId, Integer paymentMethodId, Long amount, Date paymentDate,
                   String accountNumber, String bankName, String transactionCode, String status, String note) {
        this.paymentId = paymentId;
        this.invoiceId = invoiceId;
        this.paymentMethodId = paymentMethodId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.transactionCode = transactionCode;
        this.status = status;
        this.note = note;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
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


}
