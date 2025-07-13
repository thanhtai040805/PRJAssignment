package model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ViewedCars")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewedCars.findAll", query = "SELECT v FROM ViewedCars v"),
    @NamedQuery(name = "ViewedCars.findByUserID", query = "SELECT v FROM ViewedCars v WHERE v.viewedCarsPK.userID = :userID ORDER BY v.viewedAt DESC"),
    @NamedQuery(name = "ViewedCars.findByGlobalKey", query = "SELECT v FROM ViewedCars v WHERE v.viewedCarsPK.globalKey = :globalKey")
})
public class ViewedCars implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected ViewedCarsPK viewedCarsPK;

    @JoinColumn(name = "UserID", referencedColumnName = "UserID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    @Column(name = "ViewedAt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date viewedAt;

    public ViewedCars() {
        this.viewedAt = new Date();
    }

    public ViewedCars(ViewedCarsPK viewedCarsPK) {
        this.viewedCarsPK = viewedCarsPK;
        this.viewedAt = new Date();
    }

    public ViewedCars(int userID, String globalKey) {
        this.viewedCarsPK = new ViewedCarsPK(userID, globalKey);
        this.viewedAt = new Date();
    }

    public ViewedCarsPK getViewedCarsPK() {
        return viewedCarsPK;
    }

    public void setViewedCarsPK(ViewedCarsPK viewedCarsPK) {
        this.viewedCarsPK = viewedCarsPK;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(Date viewedAt) {
        this.viewedAt = viewedAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (viewedCarsPK != null ? viewedCarsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ViewedCars)) {
            return false;
        }
        ViewedCars other = (ViewedCars) object;
        if ((this.viewedCarsPK == null && other.viewedCarsPK != null) || (this.viewedCarsPK != null && !this.viewedCarsPK.equals(other.viewedCarsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ViewedCars[ viewedCarsPK=" + viewedCarsPK + " ]";
    }
}
