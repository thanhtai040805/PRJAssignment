package model;

<<<<<<< HEAD
import jakarta.persistence.*;

@Entity
@Table(name = "DongXe")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDong")
    private Integer carModelId;

    // Quan hệ tới CarBrand
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaHang", referencedColumnName = "MaHang")
    private CarBrand carBrand;

    @Column(name = "TenDong")
    private String carModelName;

    @Column(name = "LoaiXe")
    private String carType;

    @Column(name = "SoChoNgoi")
    private Integer seatCount;

    @Column(name = "NhienLieu")
    private String fuelType;

    @Column(name = "MoTa")
    private String description;

    public CarModel() {}

    public CarModel(Integer carModelId, CarBrand carBrand, String carModelName, String carType, Integer seatCount,
                    String fuelType, String description) {
        this.carModelId = carModelId;
        this.carBrand = carBrand;
        this.carModelName = carModelName;
        this.carType = carType;
        this.seatCount = seatCount;
        this.fuelType = fuelType;
        this.description = description;
    }

    public Integer getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(Integer carModelId) {
        this.carModelId = carModelId;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
>>>>>>> origin/master
