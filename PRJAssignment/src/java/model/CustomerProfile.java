package model;

import java.math.BigDecimal;

public class CustomerProfile {
    private int maUser;
    private String ngheNghiep;
    private BigDecimal mucThuNhap;

    // Constructors
    public CustomerProfile() {}

    public CustomerProfile(int maUser, String ngheNghiep, BigDecimal mucThuNhap) {
        this.maUser = maUser;
        this.ngheNghiep = ngheNghiep;
        this.mucThuNhap = mucThuNhap;
    }

    // Getters and Setters
    public int getMaUser() {
        return maUser;
    }

    public void setMaUser(int maUser) {
        this.maUser = maUser;
    }

    public String getNgheNghiep() {
        return ngheNghiep;
    }

    public void setNgheNghiep(String ngheNghiep) {
        this.ngheNghiep = ngheNghiep;
    }

    public BigDecimal getMucThuNhap() {
        return mucThuNhap;
    }

    public void setMucThuNhap(BigDecimal mucThuNhap) {
        this.mucThuNhap = mucThuNhap;
    }
}
