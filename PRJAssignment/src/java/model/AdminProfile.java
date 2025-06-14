package model;

import java.util.Date;

public class AdminProfile {
    private int maUser;
    private int capDoQuyen;
    private Date ngayCapQuyen;

    // Constructors
    public AdminProfile() {}

    public AdminProfile(int maUser, int capDoQuyen, Date ngayCapQuyen) {
        this.maUser = maUser;
        this.capDoQuyen = capDoQuyen;
        this.ngayCapQuyen = ngayCapQuyen;
    }

    // Getters and Setters
    public int getMaUser() {
        return maUser;
    }

    public void setMaUser(int maUser) {
        this.maUser = maUser;
    }

    public int getCapDoQuyen() {
        return capDoQuyen;
    }

    public void setCapDoQuyen(int capDoQuyen) {
        this.capDoQuyen = capDoQuyen;
    }

    public Date getNgayCapQuyen() {
        return ngayCapQuyen;
    }

    public void setNgayCapQuyen(Date ngayCapQuyen) {
        this.ngayCapQuyen = ngayCapQuyen;
    }

    // Utility methods
    public boolean isSuperAdmin() {
        return capDoQuyen == 3;
    }

    public boolean isAdvancedAdmin() {
        return capDoQuyen == 2;
    }

    public boolean isBasicAdmin() {
        return capDoQuyen == 1;
    }
}
