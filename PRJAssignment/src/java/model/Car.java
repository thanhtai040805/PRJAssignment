package model;

import java.math.BigDecimal;
import java.util.Date;

public class Car {
    private int maXe;
    private int maDong;
    private int maNCC;
    private String tenXe;
    private int namSanXuat;
    private String mauSac;
    private String soKhung;
    private String soMay;
    private int dungTichDongCo;
    private int congSuat;
    private String hopSo;
    private int kmDaDi;
    private String tinhTrang;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;
    private int soLuongTon;
    private Date ngayNhap;
    private String trangThai;
    private String moTa;
    private String linkAnh; // Đổi từ hinhAnh thành linkAnh theo database
    private String globalKey; // Thêm thuộc tính globalKey
    
    // Loại bỏ albumAnh vì không có trong database
    
    // Thông tin từ bảng liên kết
    private String tenHang;
    private String tenDong;
    private String loaiXe;
    private String nhienLieu;
    private int soChoNgoi;
    private String tenNCC;

    // Constructors
    public Car() {}

    public Car(int maXe, String tenXe, BigDecimal giaBan, String tinhTrang, String linkAnh) {
        this.maXe = maXe;
        this.tenXe = tenXe;
        this.giaBan = giaBan;
        this.tinhTrang = tinhTrang;
        this.linkAnh = linkAnh;
    }

    // Getters and Setters
    public int getMaXe() {
        return maXe;
    }

    public void setMaXe(int maXe) {
        this.maXe = maXe;
    }

    public int getMaDong() {
        return maDong;
    }

    public void setMaDong(int maDong) {
        this.maDong = maDong;
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenXe() {
        return tenXe;
    }

    public void setTenXe(String tenXe) {
        this.tenXe = tenXe;
    }

    public int getNamSanXuat() {
        return namSanXuat;
    }

    public void setNamSanXuat(int namSanXuat) {
        this.namSanXuat = namSanXuat;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getSoKhung() {
        return soKhung;
    }

    public void setSoKhung(String soKhung) {
        this.soKhung = soKhung;
    }

    public String getSoMay() {
        return soMay;
    }

    public void setSoMay(String soMay) {
        this.soMay = soMay;
    }

    public int getDungTichDongCo() {
        return dungTichDongCo;
    }

    public void setDungTichDongCo(int dungTichDongCo) {
        this.dungTichDongCo = dungTichDongCo;
    }

    public int getCongSuat() {
        return congSuat;
    }

    public void setCongSuat(int congSuat) {
        this.congSuat = congSuat;
    }

    public String getHopSo() {
        return hopSo;
    }

    public void setHopSo(String hopSo) {
        this.hopSo = hopSo;
    }

    public int getKmDaDi() {
        return kmDaDi;
    }

    public void setKmDaDi(int kmDaDi) {
        this.kmDaDi = kmDaDi;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    // Getter/Setter cho GlobalKey
    public String getGlobalKey() {
        return globalKey;
    }

    public void setGlobalKey(String globalKey) {
        this.globalKey = globalKey;
    }

    // Getter/Setter cho thông tin từ bảng liên kết
    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getTenDong() {
        return tenDong;
    }

    public void setTenDong(String tenDong) {
        this.tenDong = tenDong;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }

    public String getNhienLieu() {
        return nhienLieu;
    }

    public void setNhienLieu(String nhienLieu) {
        this.nhienLieu = nhienLieu;
    }

    public int getSoChoNgoi() {
        return soChoNgoi;
    }

    public void setSoChoNgoi(int soChoNgoi) {
        this.soChoNgoi = soChoNgoi;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    // Utility methods
    public String getFormattedPrice() {
        if (giaBan != null) {
            return String.format("%,d VNĐ", giaBan.longValue());
        }
        return "Liên hệ";
    }

    public String getEngineInfo() {
        return dungTichDongCo + "cc - " + congSuat + "HP";
    }

    // Thêm method để tương thích với code cũ sử dụng hinhAnh
    public String getHinhAnh() {
        return this.linkAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.linkAnh = hinhAnh;
    }

    // Method kiểm tra xe có ảnh không
    public boolean hasImage() {
        return linkAnh != null && !linkAnh.trim().isEmpty();
    }

    // Method lấy ảnh mặc định nếu không có ảnh
    public String getImageOrDefault() {
        if (hasImage()) {
            return linkAnh;
        }
        return "/images/cars/default-car.jpg";
    }

    // Method kiểm tra xe có sẵn không
    public boolean isAvailable() {
        return "Có sẵn".equals(trangThai) && soLuongTon > 0;
    }

    // Method kiểm tra xe mới
    public boolean isNew() {
        return "Mới".equals(tinhTrang);
    }

    // Method kiểm tra xe cũ
    public boolean isUsed() {
        return "Cũ".equals(tinhTrang);
    }

    @Override
    public String toString() {
        return "Car{" +
                "maXe=" + maXe +
                ", tenXe='" + tenXe + '\'' +
                ", tenHang='" + tenHang + '\'' +
                ", tenDong='" + tenDong + '\'' +
                ", namSanXuat=" + namSanXuat +
                ", mauSac='" + mauSac + '\'' +
                ", giaBan=" + giaBan +
                ", tinhTrang='" + tinhTrang + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", globalKey='" + globalKey + '\'' +
                '}';
    }
}
