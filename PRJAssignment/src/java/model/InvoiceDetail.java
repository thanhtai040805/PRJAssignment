package model;

import jakarta.persistence.*;

@Entity
@Table(name = "ChiTietHoaDon")
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCTHD")
    private Integer invoiceDetailId;

    @Column(name = "MaXe")
    private Integer carId;

    @Column(name = "SoLuong")
    private Integer quantity;

    @Column(name = "DonGia")
    private Long unitPrice;

    @Column(name = "ThanhTien")
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "MaHD")
    private Invoice invoice;

    // 🔗 Thêm liên kết đến Car
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaXe", insertable = false, updatable = false)
    private Car car;

    public InvoiceDetail() {
    }

    public InvoiceDetail(Integer invoiceDetailId, Invoice invoice, Integer carId, Integer quantity, Long unitPrice,
            Long totalPrice) {
        this.invoiceDetailId = invoiceDetailId;
        this.invoice = invoice;
        this.carId = carId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public Integer getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(Integer invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Car getCar() {
        return car;
    }
}
