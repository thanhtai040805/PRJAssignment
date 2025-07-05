package model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "KhachHang")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKH")
    private Integer customerId;

    @Column(name = "HoTen")
    private String fullName;

    @Column(name = "NgaySinh")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "GioiTinh")
    private String gender;

    @Column(name = "DiaChi")
    private String address;

    @Column(name = "SoDienThoai")
    private String phone;

    @Column(name = "Email")
    private String email;

    @Column(name = "CCCD")
    private String identityCard;

    @Column(name = "NgheNghiep")
    private String occupation;

    @Column(name = "MucThuNhap")
    private Long income;

    @Column(name = "NgayTao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "TrangThai")
    private String status;

    public Customer() {
    }

    public Customer(Integer customerId, String fullName, Date birthDate, String gender, String address, String phone,
            String email, String identityCard, String occupation, Long income, Date createdDate, String status) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.identityCard = identityCard;
        this.occupation = occupation;
        this.income = income;
        this.createdDate = createdDate;
        this.status = status;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
