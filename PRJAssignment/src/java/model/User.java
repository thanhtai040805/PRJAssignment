package model;

import java.util.Date; // Keep if ngaySinh still uses it, or consider migrating ngaySinh to LocalDate as well
import model.Customer; // Make sure this is correctly imported
import model.Employee; // Make sure this is correctly imported
import model.AdminProfile; // Make sure this is correctly imported
import java.time.LocalDateTime; // This is crucial

public class User {

    private int maUser;
    private String username;
    private String password;
    private String email;
    private String hoTen;
    private Date ngaySinh; // Sticking with java.util.Date for now based on your code
    private String gioiTinh;
    private String diaChi;
    private String soDienThoai;
    private String cccd;
    private String role;
    private LocalDateTime ngayTao; // THIS IS CORRECT - keep it as LocalDateTime
    private String trangThai;

    // Profile information
    private Customer customer;
    private Employee employee; // Renamed from employeeProfile for consistency with getter
    private AdminProfile adminProfile;

    // Constructors
    public User() {
    }

    public User(String username, String password, String email, String hoTen, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.hoTen = hoTen;
        this.role = role;
        this.ngayTao = LocalDateTime.now(); // FIX: Use LocalDateTime.now()
        this.trangThai = "Hoạt động";
    }

    // Full constructor (if needed, useful for loading from DB)
    // IMPORTANT: If you pass a java.util.Date for ngayTao here, it will be an issue
    // for LocalDateTime field. You should align all dates to either java.util.Date
    // or java.time.* for consistency.
    // For now, I'm adapting this constructor to accept LocalDateTime for ngayTao
    public User(int maUser, String username, String password, String email, String hoTen, Date ngaySinh,
            String gioiTinh, String diaChi, String soDienThoai, String cccd, String role,
            LocalDateTime ngayTao, String trangThai) { // FIX: Change Date to LocalDateTime for ngayTao parameter
        this.maUser = maUser;
        this.username = username;
        this.password = password;
        this.email = email;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.cccd = cccd;
        this.role = role;
        this.ngayTao = ngayTao; // This assignment is now correct
        this.trangThai = trangThai;
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

    public LocalDateTime getNgayTao() { // THIS IS CORRECT
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) { // FIX: Change parameter type from Date to LocalDateTime
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    // Getters and Setters cho Customer
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() { // Changed to getEmployeeProfile for consistency with DAO
        return employee;
    }

    public void setEmployee(Employee employeeProfile) { // Changed to setEmployeeProfile for consistency with DAO
        this.employee = employeeProfile;
    }

    public AdminProfile getAdminProfile() {
        return adminProfile;
    }

    public void setAdminProfile(AdminProfile adminProfile) {
        this.adminProfile = adminProfile;
    }

    // Utility methods
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role);
    }

    public boolean isEmployee() {
        return "EMPLOYEE".equalsIgnoreCase(this.role);
    }

    public boolean isCustomer() {
        return "CUSTOMER".equalsIgnoreCase(this.role);
    }

    public boolean isActive() {
        return "Hoạt động".equalsIgnoreCase(this.trangThai);
    }

    @Override
    public String toString() {
        return "User{"
                + "maUser=" + maUser
                + ", username='" + username + '\''
                + ", email='" + email + '\''
                + ", hoTen='" + hoTen + '\''
                + ", role='" + role + '\''
                + ", trangThai='" + trangThai + '\''
                + '}';
    }
}
