package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {

    private Integer maTT;              // Mã thanh toán
    private Integer maHD;              // Mã hóa đơn
    private Integer maPTTT;            // Mã phương thức thanh toán
    private BigDecimal soTien;         // Số tiền thanh toán
    private LocalDate ngayThanhToan;   // Ngày thanh toán
    private String soTaiKhoan;         // Số tài khoản ngân hàng (nếu có)
    private String tenNganHang;        // Tên ngân hàng
    private String maGiaoDich;         // Mã giao dịch
    private String trangThai;          // Trạng thái thanh toán
    private String ghiChu;             // Ghi chú

    // Đối tượng liên quan (tùy chọn)
    private PaymentMethod phuongThucThanhToan;

    // Constructors
    public Payment() {
    }

    public Payment(Integer maTT, Integer maHD, Integer maPTTT, BigDecimal soTien,
                   LocalDate ngayThanhToan, String soTaiKhoan, String tenNganHang,
                   String maGiaoDich, String trangThai, String ghiChu) {
        this.maTT = maTT;
        this.maHD = maHD;
        this.maPTTT = maPTTT;
        this.soTien = soTien;
        this.ngayThanhToan = ngayThanhToan;
        this.soTaiKhoan = soTaiKhoan;
        this.tenNganHang = tenNganHang;
        this.maGiaoDich = maGiaoDich;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public Payment(Integer maHD, Integer maPTTT, BigDecimal soTien,
                   LocalDate ngayThanhToan, String soTaiKhoan, String tenNganHang,
                   String maGiaoDich, String trangThai, String ghiChu) {
        this.maHD = maHD;
        this.maPTTT = maPTTT;
        this.soTien = soTien;
        this.ngayThanhToan = ngayThanhToan;
        this.soTaiKhoan = soTaiKhoan;
        this.tenNganHang = tenNganHang;
        this.maGiaoDich = maGiaoDich;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // Getters and Setters
    public Integer getMaTT() {
        return maTT;
    }

    public void setMaTT(Integer maTT) {
        this.maTT = maTT;
    }

    public Integer getMaHD() {
        return maHD;
    }

    public void setMaHD(Integer maHD) {
        this.maHD = maHD;
    }

    public Integer getMaPTTT() {
        return maPTTT;
    }

    public void setMaPTTT(Integer maPTTT) {
        this.maPTTT = maPTTT;
    }

    public BigDecimal getSoTien() {
        return soTien;
    }

    public void setSoTien(BigDecimal soTien) {
        this.soTien = soTien;
    }

    public LocalDate getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(LocalDate ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public String getTenNganHang() {
        return tenNganHang;
    }

    public void setTenNganHang(String tenNganHang) {
        this.tenNganHang = tenNganHang;
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
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

    public PaymentMethod getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(PaymentMethod phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }
}
