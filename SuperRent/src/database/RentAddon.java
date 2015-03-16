/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dongshengshen
 */
@Entity
@Table(name = "RENT_ADDON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RentAddon.findAll", query = "SELECT r FROM RentAddon r"),
    @NamedQuery(name = "RentAddon.findByRentid", query = "SELECT r FROM RentAddon r WHERE r.rentAddonPK.rentid = :rentid"),
    @NamedQuery(name = "RentAddon.findByEquipname", query = "SELECT r FROM RentAddon r WHERE r.rentAddonPK.equipname = :equipname")})
public class RentAddon implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RentAddonPK rentAddonPK;
    @JoinColumn(name = "EQUIPNAME", referencedColumnName = "EQUIPNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Equipment equipment;

    public RentAddon() {
    }

    public RentAddon(RentAddonPK rentAddonPK) {
        this.rentAddonPK = rentAddonPK;
    }

    public RentAddon(int rentid, String equipname) {
        this.rentAddonPK = new RentAddonPK(rentid, equipname);
    }

    public RentAddonPK getRentAddonPK() {
        return rentAddonPK;
    }

    public void setRentAddonPK(RentAddonPK rentAddonPK) {
        this.rentAddonPK = rentAddonPK;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rentAddonPK != null ? rentAddonPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RentAddon)) {
            return false;
        }
        RentAddon other = (RentAddon) object;
        if ((this.rentAddonPK == null && other.rentAddonPK != null) || (this.rentAddonPK != null && !this.rentAddonPK.equals(other.rentAddonPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.RentAddon[ rentAddonPK=" + rentAddonPK + " ]";
    }
    
}
