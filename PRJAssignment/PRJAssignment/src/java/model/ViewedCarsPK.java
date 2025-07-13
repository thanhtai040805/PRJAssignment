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
public class ViewedCarsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "UserID")
    private int userID;
    @Basic(optional = false)
    @Column(name = "GlobalKey")
    private String globalKey;

    public ViewedCarsPK() {
    }

    public ViewedCarsPK(int userID, String globalKey) {
        this.userID = userID;
        this.globalKey = globalKey;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getGlobalKey() {
        return globalKey;
    }

    public void setGlobalKey(String globalKey) {
        this.globalKey = globalKey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userID;
        hash += (globalKey != null ? globalKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ViewedCarsPK)) {
            return false;
        }
        ViewedCarsPK other = (ViewedCarsPK) object;
        if (this.userID != other.userID) {
            return false;
        }
        if ((this.globalKey == null && other.globalKey != null) || (this.globalKey != null && !this.globalKey.equals(other.globalKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ViewedCarsPK[ userID=" + userID + ", globalKey=" + globalKey + " ]";
    }
    
}
