/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author This PC
 */
@Entity
@Table(name = "FavoriteCars")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FavoriteCars.findAll", query = "SELECT f FROM FavoriteCars f"),
    @NamedQuery(name = "FavoriteCars.findByUserID", query = "SELECT f FROM FavoriteCars f WHERE f.favoriteCarsPK.userID = :userID"),
    @NamedQuery(name = "FavoriteCars.findByGlobalKey", query = "SELECT f FROM FavoriteCars f WHERE f.favoriteCarsPK.globalKey = :globalKey"),
    @NamedQuery(name = "FavoriteCars.findByCreatedAt", query = "SELECT f FROM FavoriteCars f WHERE f.createdAt = :createdAt")})
public class FavoriteCars implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FavoriteCarsPK favoriteCarsPK;
    @Column(name = "CreatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public FavoriteCars() {
    }

    public FavoriteCars(FavoriteCarsPK favoriteCarsPK) {
        this.favoriteCarsPK = favoriteCarsPK;
    }

    public FavoriteCars(int userID, String globalKey) {
        this.favoriteCarsPK = new FavoriteCarsPK(userID, globalKey);
    }

    public FavoriteCarsPK getFavoriteCarsPK() {
        return favoriteCarsPK;
    }

    public void setFavoriteCarsPK(FavoriteCarsPK favoriteCarsPK) {
        this.favoriteCarsPK = favoriteCarsPK;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (favoriteCarsPK != null ? favoriteCarsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FavoriteCars)) {
            return false;
        }
        FavoriteCars other = (FavoriteCars) object;
        if ((this.favoriteCarsPK == null && other.favoriteCarsPK != null) || (this.favoriteCarsPK != null && !this.favoriteCarsPK.equals(other.favoriteCarsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.FavoriteCars[ favoriteCarsPK=" + favoriteCarsPK + " ]";
    }
    
}
