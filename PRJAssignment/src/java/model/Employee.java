package model;
<<<<<<< HEAD
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NhanVien")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNV")
    private Integer employeeId;

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

    @Column(name = "ChucVu")
    private String position;

    @Column(name = "PhongBan")
    private String department;

    @Column(name = "LuongCoBan")
    private Long baseSalary;

    @Column(name = "HeSoLuong")
    private Double salaryCoefficient;

    @Column(name = "NgayVaoLam")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "TrangThai")
    private String status;

    public Employee() {}

    public Employee(Integer employeeId, String fullName, Date birthDate, String gender, String address, String phone,
                    String email, String identityCard, String position, String department, Long baseSalary,
                    Double salaryCoefficient, Date startDate, String status) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.identityCard = identityCard;
        this.position = position;
        this.department = department;
        this.baseSalary = baseSalary;
        this.salaryCoefficient = salaryCoefficient;
        this.startDate = startDate;
        this.status = status;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Long baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Double getSalaryCoefficient() {
        return salaryCoefficient;
    }

    public void setSalaryCoefficient(Double salaryCoefficient) {
        this.salaryCoefficient = salaryCoefficient;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

>>>>>>> origin/master
}
