package model;
<<<<<<< HEAD
import jakarta.persistence.*;

@Entity
@Table(name = "HangXe")
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHang")
    private Integer carBrandId;

    @Column(name = "TenHang")
    private String carBrandName;

    @Column(name = "QuocGia")
    private String country;

    @Column(name = "Website")
    private String website;

    @Column(name = "MoTa")
    private String description;

    public CarBrand() {}

    public CarBrand(Integer carBrandId, String carBrandName, String country, String website, String description) {
        this.carBrandId = carBrandId;
        this.carBrandName = carBrandName;
        this.country = country;
        this.website = website;
        this.description = description;
    }

    public Integer getCarBrandId() {
        return carBrandId;
    }

    public void setCarBrandId(Integer carBrandId) {
        this.carBrandId = carBrandId;
    }

    public String getCarBrandName() {
        return carBrandName;
    }

    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
>>>>>>> origin/master
    }
}
