package model;

import java.math.BigDecimal;
import java.util.Date;

public class EmployeeProfile {
    private int maUser;
    private String chucVu;
    private String phongBan;
    private BigDecimal luongCoBan;
    private BigDecimal heSoLuong;
    private Date ngayVaoLam;

    // Constructors
    public EmployeeProfile() {}

    public EmployeeProfile(int maUser, String chucVu, String phongBan, 
                          BigDecimal luongCoBan, BigDecimal heSoLuong, Date ngayVaoLam) {
        this.maUser = maUser;
        this.chucVu = chucVu;
        this.phongBan = phongBan;
        this.luongCoBan = luongCoBan;
        this.heSoLuong = heSoLuong;
        this.ngayVaoLam = ngayVaoLam;
    }

    // Getters and Setters
    public int getMaUser() {
        return maUser;
    }

    public void setMaUser(int maUser) {
        this.maUser = maUser;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public BigDecimal getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(BigDecimal luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public BigDecimal getHeSoLuong() {
        return heSoLuong;
    }

    public void setHeSoLuong(BigDecimal heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    public Date getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(Date ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }
}
