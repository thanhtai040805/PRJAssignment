package model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "XeOTo")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaXe")
    private Integer carId;

    // Quan hệ tới CarModel
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDong", referencedColumnName = "MaDong")
    private CarModel carModel;

    @Column(name = "MaNCC")
    private Integer supplierId;

    @Column(name = "TenXe")
    private String carName;

    @Column(name = "NamSanXuat")
    private Integer year;

    @Column(name = "MauSac")
    private String color;

    @Column(name = "SoKhung")
    private String chassisNumber;

    @Column(name = "SoMay")
    private String engineNumber;

    @Column(name = "DungTichDongCo")
    private Integer engineCapacity;

    @Column(name = "CongSuat")
    private Integer power;

    @Column(name = "HopSo")
    private String transmission;

    @Column(name = "KmDaDi")
    private Integer mileage;

    @Column(name = "TinhTrang")
    private String condition;

    @Column(name = "GiaNhap")
    private Long importPrice;

    @Column(name = "GiaBan")
    private Long salePrice;

    @Column(name = "SoLuongTon")
    private Integer stockQuantity;

    @Column(name = "NgayNhap")
    @Temporal(TemporalType.DATE)
    private Date importDate;

    @Column(name = "TrangThai")
    private String status;

    @Column(name = "MoTa")
    private String description;

    @Column(name = "LinkAnh")
    private String imageLink;

    @Column(name = "GlobalKey")
    private String globalKey;

    public Car() {}

    public Car(Integer carId, CarModel carModel, Integer supplierId, String carName, Integer year, String color,
               String chassisNumber, String engineNumber, Integer engineCapacity, Integer power, String transmission,
               Integer mileage, String condition, Long importPrice, Long salePrice, Integer stockQuantity,
               Date importDate, String status, String description, String imageLink, String globalKey) {
        this.carId = carId;
        this.carModel = carModel;
        this.supplierId = supplierId;
        this.carName = carName;
        this.year = year;
        this.color = color;
        this.chassisNumber = chassisNumber;
        this.engineNumber = engineNumber;
        this.engineCapacity = engineCapacity;
        this.power = power;
        this.transmission = transmission;
        this.mileage = mileage;
        this.condition = condition;
        this.importPrice = importPrice;
        this.salePrice = salePrice;
        this.stockQuantity = stockQuantity;
        this.importDate = importDate;
        this.status = status;
        this.description = description;
        this.imageLink = imageLink;
        this.globalKey = globalKey;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public Integer getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(Integer engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Long getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(Long importPrice) {
        this.importPrice = importPrice;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getGlobalKey() {
        return globalKey;
    }

    public void setGlobalKey(String globalKey) {
        this.globalKey = globalKey;
    }

    // Getter cho JSP dùng trực tiếp
    public String getCarBrandName() {
        return (carModel != null && carModel.getCarBrand() != null) ? carModel.getCarBrand().getCarBrandName() : "";
    }

    public String getCarModelName() {
        return carModel != null ? carModel.getCarModelName() : "";
    }

    public String getFuelType() {
        return carModel != null ? carModel.getFuelType() : "";
    }

    public Integer getSeatCount() {
        return carModel != null ? carModel.getSeatCount() : null;
    }

    public String getCarType() {
        return carModel != null ? carModel.getCarType() : "";
    }
}
