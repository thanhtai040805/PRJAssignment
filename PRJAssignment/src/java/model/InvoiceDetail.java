package model;

import java.math.BigDecimal;

public class InvoiceDetail {

    private Integer maCTHD;
    private Integer maHD; // Foreign key
    private Integer maXe; // Foreign key
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;

    // Các đối tượng liên quan để hiển thị đầy đủ thông tin chi tiết
    private Car xeOto; // Thông tin chi tiết của xe

    public InvoiceDetail() {
    }

    public InvoiceDetail(Integer maCTHD, Integer maHD, Integer maXe, Integer soLuong, BigDecimal donGia, BigDecimal thanhTien) {
        this.maCTHD = maCTHD;
        this.maHD = maHD;
        this.maXe = maXe;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    // Constructor cho việc tạo mới
    public InvoiceDetail(Integer maHD, Integer maXe, Integer soLuong, BigDecimal donGia, BigDecimal thanhTien) {
        this.maHD = maHD;
        this.maXe = maXe;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    // Getters and Setters
    public Integer getMaCTHD() {
        return maCTHD;
    }

    public void setMaCTHD(Integer maCTHD) {
        this.maCTHD = maCTHD;
    }

    public Integer getMaHD() {
        return maHD;
    }

    public void setMaHD(Integer maHD) {
        this.maHD = maHD;
    }

    public Integer getMaXe() {
        return maXe;
    }

    public void setMaXe(Integer maXe) {
        this.maXe = maXe;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

    // Getter/Setter cho đối tượng liên quan
    public Car getXeOto() {
        return xeOto;
    }

    public void setXeOto(Car xeOto) {
        this.xeOto = xeOto;
    }
}
