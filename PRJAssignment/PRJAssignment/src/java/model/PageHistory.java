package model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PageHistory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PageHistory.findAll", query = "SELECT p FROM PageHistory p"),
    @NamedQuery(name = "PageHistory.findByUserID", query = "SELECT p FROM PageHistory p WHERE p.pageHistoryPK.userID = :userID ORDER BY p.createdAt DESC"),
    @NamedQuery(name = "PageHistory.findByPath", query = "SELECT p FROM PageHistory p WHERE p.pageHistoryPK.path = :path")
})
public class PageHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected PageHistoryPK pageHistoryPK;

    @JoinColumn(name = "UserID", referencedColumnName = "UserID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    @Column(name = "createdAt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public PageHistory() {
    }

    public PageHistory(PageHistoryPK pageHistoryPK) {
        this.pageHistoryPK = pageHistoryPK;
        this.createdAt = new Date(); // mặc định là thời điểm hiện tại
    }

    public PageHistory(int userID, String path) {
        this.pageHistoryPK = new PageHistoryPK(userID, path);
        this.createdAt = new Date();
    }

    public PageHistoryPK getPageHistoryPK() {
        return pageHistoryPK;
    }

    public void setPageHistoryPK(PageHistoryPK pageHistoryPK) {
        this.pageHistoryPK = pageHistoryPK;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pageHistoryPK != null ? pageHistoryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PageHistory)) {
            return false;
        }
        PageHistory other = (PageHistory) object;
        if ((this.pageHistoryPK == null && other.pageHistoryPK != null) || (this.pageHistoryPK != null && !this.pageHistoryPK.equals(other.pageHistoryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PageHistory[ pageHistoryPK=" + pageHistoryPK + " ]";
    }
}
