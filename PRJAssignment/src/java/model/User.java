package model;

public class User {

    private int maUser;
    private String username;
    private String password;
    private String role;
    private Integer maKH; // nullable
    private Integer maNV; // nullable
    private String trangThai;

    public User() {
    }

    public User(int maUser, String username, String password, String role, Integer maKH, Integer maNV, String trangThai) {
        this.maUser = maUser;
        this.username = username;
        this.password = password;
        this.role = role;
        this.maKH = maKH;
        this.maNV = maNV;
        this.trangThai = trangThai;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "User{" +
                "maUser=" + maUser +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
