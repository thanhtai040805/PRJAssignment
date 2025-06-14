package model;

import java.util.Date;

public class User {
    private int maUser;
    private String username;
    private String password;
    private String email;
    private String hoTen;
    private Date ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String soDienThoai;
    private String cccd;
    private String role;
    private Date ngayTao;
    private String trangThai;
    
    // Profile information
    private CustomerProfile customerProfile;
    private EmployeeProfile employeeProfile;
    private AdminProfile adminProfile;

    // Constructors
    public User() {}

    public User(String username, String password, String email, String hoTen, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.hoTen = hoTen;
        this.role = role;
        this.ngayTao = new Date();
        this.trangThai = "Hoạt động";
    }

    // Getters and Setters
    public int getMaUser() {
        return maUser;
    }

    public void setMaUser(int maUser) {
        this.maUser = maUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public CustomerProfile getCustomerProfile() {
        return customerProfile;
    }

    public void setCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
    }

    public EmployeeProfile getEmployeeProfile() {
        return employeeProfile;
    }

    public void setEmployeeProfile(EmployeeProfile employeeProfile) {
        this.employeeProfile = employeeProfile;
    }

    public AdminProfile getAdminProfile() {
        return adminProfile;
    }

    public void setAdminProfile(AdminProfile adminProfile) {
        this.adminProfile = adminProfile;
    }

    // Utility methods
    public boolean isAdmin() {
        return "ADMIN".equals(this.role);
    }

    public boolean isEmployee() {
        return "EMPLOYEE".equals(this.role);
    }

    public boolean isCustomer() {
        return "CUSTOMER".equals(this.role);
    }

    public boolean isActive() {
        return "Hoạt động".equals(this.trangThai);
    }

    @Override
    public String toString() {
        return "User{" +
                "maUser=" + maUser +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", role='" + role + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
