package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Customer {

    private Integer maKH;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String soDienThoai;
    private String email;
    private String cccd;
    private String ngheNghiep;
    private Long mucThuNhap;
    private LocalDateTime ngayTao;
    private String trangThai;

    public Customer() {
    }

    public Customer(Integer maKH, String hoTen, LocalDate ngaySinh, String gioiTinh, String diaChi, String soDienThoai, String email, String cccd, String ngheNghiep, Long mucThuNhap, LocalDateTime ngayTao, String trangThai) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.cccd = cccd;
        this.ngheNghiep = ngheNghiep;
        this.mucThuNhap = mucThuNhap;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }

    // Constructor cho việc tạo mới (ID sẽ được DB tự sinh)
    public Customer(String hoTen, LocalDate ngaySinh, String gioiTinh, String diaChi, String soDienThoai, String email, String cccd, String ngheNghiep, Long mucThuNhap) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.cccd = cccd;
        this.ngheNghiep = ngheNghiep;
        this.mucThuNhap = mucThuNhap;
        this.ngayTao = LocalDateTime.now();
        this.trangThai = "Hoạt động";
    }

    // Getters and Setters (tạo đầy đủ cho tất cả các thuộc tính)
    public Integer getMaKH() {
        return maKH;
    }

    public void setMaKH(Integer maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getNgheNghiep() {
        return ngheNghiep;
    }

    public void setNgheNghiep(String ngheNghiep) {
        this.ngheNghiep = ngheNghiep;
    }

    public Long getMucThuNhap() {
        return mucThuNhap;
    }

    public void setMucThuNhap(Long mucThuNhap) {
        this.mucThuNhap = mucThuNhap;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
