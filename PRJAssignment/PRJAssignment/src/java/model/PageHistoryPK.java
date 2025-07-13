/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 * @author This PC
 */
@Embeddable
public class PageHistoryPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "UserID")
    private int userID;
    @Basic(optional = false)
    @Column(name = "Path")
    private String path;

    public PageHistoryPK() {
    }

    public PageHistoryPK(int userID, String path) {
        this.userID = userID;
        this.path = path;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userID;
        hash += (path != null ? path.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PageHistoryPK)) {
            return false;
        }
        PageHistoryPK other = (PageHistoryPK) object;
        if (this.userID != other.userID) {
            return false;
        }
        if ((this.path == null && other.path != null) || (this.path != null && !this.path.equals(other.path))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PageHistoryPK[ userID=" + userID + ", path=" + path + " ]";
    }
    
}
