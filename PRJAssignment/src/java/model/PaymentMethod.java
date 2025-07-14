package model;
import jakarta.persistence.*;

@Entity
@Table(name = "PhuongThucThanhToan")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPTTT")
    private Integer paymentMethodId;

    @Column(name = "TenPTTT")
    private String paymentMethodName;

    @Column(name = "MoTa")
    private String description;

    public PaymentMethod() {}

    public PaymentMethod(Integer paymentMethodId, String paymentMethodName, String description) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
        this.description = description;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
