/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author dongshengshen
 */
@Embeddable
public class RentAddonPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "RENTID")
    private int rentid;
    @Basic(optional = false)
    @Column(name = "EQUIPNAME")
    private String equipname;

    public RentAddonPK() {
    }

    public RentAddonPK(int rentid, String equipname) {
        this.rentid = rentid;
        this.equipname = equipname;
    }

    public int getRentid() {
        return rentid;
    }

    public void setRentid(int rentid) {
        this.rentid = rentid;
    }

    public String getEquipname() {
        return equipname;
    }

    public void setEquipname(String equipname) {
        this.equipname = equipname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) rentid;
        hash += (equipname != null ? equipname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RentAddonPK)) {
            return false;
        }
        RentAddonPK other = (RentAddonPK) object;
        if (this.rentid != other.rentid) {
            return false;
        }
        if ((this.equipname == null && other.equipname != null) || (this.equipname != null && !this.equipname.equals(other.equipname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.RentAddonPK[ rentid=" + rentid + ", equipname=" + equipname + " ]";
    }
    
}
