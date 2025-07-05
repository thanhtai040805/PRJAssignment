package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List; // Sử dụng List thay vì Set cho các chi tiết

public class Invoice {

    private Integer maHD;
    private Integer maKH; // Foreign key
    private Integer maNV; // Foreign key
    private LocalDate ngayLap;
    private BigDecimal tongTien;
    private BigDecimal tienGiam;
    private BigDecimal thanhTien;
    private String trangThai;
    private String ghiChu;

    // Các đối tượng liên quan để hiển thị đầy đủ thông tin hóa đơn
    private Customer khachHang;
    private Employee nhanVien;
    private List<InvoiceDetail> chiTietHoaDons; // Danh sách các xe trong hóa đơn
    private Payment thanhToan; // Thông tin thanh toán

    public Invoice() {
    }

    public Invoice(Integer maHD, Integer maKH, Integer maNV, LocalDate ngayLap, BigDecimal tongTien, BigDecimal tienGiam, BigDecimal thanhTien, String trangThai, String ghiChu) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.tienGiam = tienGiam;
        this.thanhTien = thanhTien;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // Constructor cho việc tạo mới
    public Invoice(Integer maKH, Integer maNV, BigDecimal tongTien, BigDecimal tienGiam, BigDecimal thanhTien, String trangThai, String ghiChu) {
        this.maKH = maKH;
        this.maNV = maNV;
        this.ngayLap = LocalDate.now();
        this.tongTien = tongTien;
        this.tienGiam = tienGiam;
        this.thanhTien = thanhTien;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // Getters and Setters (tạo đầy đủ)
    public Integer getMaHD() {
        return maHD;
    }

    public void setMaHD(Integer maHD) {
        this.maHD = maHD;
    }

    public Integer getMaKH() {
        return maKH;
    }

    public void setMaKH(Integer maKH) {
        this.maKH = maKH;
    }

    public Integer getMaNV() {
        return maNV;
    }

    public void setMaNV(Integer maNV) {
        this.maNV = maNV;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public BigDecimal getTienGiam() {
        return tienGiam;
    }

    public void setTienGiam(BigDecimal tienGiam) {
        this.tienGiam = tienGiam;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    // Getters/Setters cho các đối tượng liên quan
    public Customer getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(Customer khachHang) {
        this.khachHang = khachHang;
    }

    public Employee getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(Employee nhanVien) {
        this.nhanVien = nhanVien;
    }

    public List<InvoiceDetail> getChiTietHoaDons() {
        return chiTietHoaDons;
    }

    public void setChiTietHoaDons(List<InvoiceDetail> chiTietHoaDons) {
        this.chiTietHoaDons = chiTietHoaDons;
    }

    public Payment getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(Payment thanhToan) {
        this.thanhToan = thanhToan;
    }
}
