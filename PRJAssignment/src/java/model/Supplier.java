package model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NhaCungCap")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNCC")
    private Integer supplierId;

    @Column(name = "TenNCC", nullable = false)
    private String supplierName;

    @Column(name = "DiaChi")
    private String address;

    @Column(name = "SoDienThoai")
    private String phoneNumber;

    @Column(name = "Email")
    private String email;

    @Column(name = "MaSoThue", unique = true)
    private String taxCode;

    @Column(name = "NguoiLienHe")
    private String contactPerson;

    @Column(name = "SoDienThoaiLienHe")
    private String contactPhone;

    @Column(name = "LoaiNCC")
    private String supplierType;

    @Column(name = "NgayHopTac")
    @Temporal(TemporalType.DATE)
    private Date cooperationDate;

    @Column(name = "TrangThai")
    private String status;

    // Constructors
    public Supplier() {}

    public Supplier(Integer supplierId, String supplierName, String address, String phoneNumber, String email,
                    String taxCode, String contactPerson, String contactPhone, String supplierType,
                    Date cooperationDate, String status) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.taxCode = taxCode;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
        this.supplierType = supplierType;
        this.cooperationDate = cooperationDate;
        this.status = status;
    }

    // Getters and Setters
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public Date getCooperationDate() {
        return cooperationDate;
    }

    public void setCooperationDate(Date cooperationDate) {
        this.cooperationDate = cooperationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
