package model;

import java.time.LocalDateTime;

public class PaymentMethod {

    private Integer maPTTT;
    private String tenPTTT;
    private String moTa;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private String trangThai; // Ví dụ: "Hoạt động", "Không hoạt động"

    public PaymentMethod() {
    }

    // Constructor đầy đủ cho việc truy xuất từ DB
    public PaymentMethod(Integer maPTTT, String tenPTTT, String moTa, LocalDateTime ngayTao, LocalDateTime ngayCapNhat, String trangThai) {
        this.maPTTT = maPTTT;
        this.tenPTTT = tenPTTT;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
        this.trangThai = trangThai;
    }

    // Constructor cho việc tạo mới (MaPTTT sẽ do DB tự sinh)
    public PaymentMethod(String tenPTTT, String moTa) {
        this.tenPTTT = tenPTTT;
        this.moTa = moTa;
        this.ngayTao = LocalDateTime.now();
        this.trangThai = "Hoạt động"; // Default status
    }

    // Getters and Setters
    public Integer getMaPTTT() {
        return maPTTT;
    }

    public void setMaPTTT(Integer maPTTT) {
        this.maPTTT = maPTTT;
    }

    public String getTenPTTT() {
        return tenPTTT;
    }

    public void setTenPTTT(String tenPTTT) {
        this.tenPTTT = tenPTTT;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public LocalDateTime getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(LocalDateTime ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
