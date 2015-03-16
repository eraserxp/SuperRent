/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dongshengshen
 */
@Entity
@Table(name = "EQUIPMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipment.findAll", query = "SELECT e FROM Equipment e"),
    @NamedQuery(name = "Equipment.findByEquipname", query = "SELECT e FROM Equipment e WHERE e.equipname = :equipname"),
    @NamedQuery(name = "Equipment.findByType", query = "SELECT e FROM Equipment e WHERE e.type = :type"),
    @NamedQuery(name = "Equipment.findByDRate", query = "SELECT e FROM Equipment e WHERE e.dRate = :dRate"),
    @NamedQuery(name = "Equipment.findByHRate", query = "SELECT e FROM Equipment e WHERE e.hRate = :hRate")})
public class Equipment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EQUIPNAME")
    private String equipname;
    @Column(name = "TYPE")
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "D_RATE")
    private Double dRate;
    @Column(name = "H_RATE")
    private Double hRate;
    @OneToMany(mappedBy = "equipName")
    private Collection<Reservation> reservationCollection;
    @OneToMany(mappedBy = "equipname")
    private Collection<ReserveAddon> reserveAddonCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipment")
    private Collection<RentAddon> rentAddonCollection;
    @OneToMany(mappedBy = "equipName")
    private Collection<Rent> rentCollection;

    public Equipment() {
    }

    public Equipment(String equipname) {
        this.equipname = equipname;
    }

    public String getEquipname() {
        return equipname;
    }

    public void setEquipname(String equipname) {
        this.equipname = equipname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getDRate() {
        return dRate;
    }

    public void setDRate(Double dRate) {
        this.dRate = dRate;
    }

    public Double getHRate() {
        return hRate;
    }

    public void setHRate(Double hRate) {
        this.hRate = hRate;
    }

    @XmlTransient
    public Collection<Reservation> getReservationCollection() {
        return reservationCollection;
    }

    public void setReservationCollection(Collection<Reservation> reservationCollection) {
        this.reservationCollection = reservationCollection;
    }

    @XmlTransient
    public Collection<ReserveAddon> getReserveAddonCollection() {
        return reserveAddonCollection;
    }

    public void setReserveAddonCollection(Collection<ReserveAddon> reserveAddonCollection) {
        this.reserveAddonCollection = reserveAddonCollection;
    }

    @XmlTransient
    public Collection<RentAddon> getRentAddonCollection() {
        return rentAddonCollection;
    }

    public void setRentAddonCollection(Collection<RentAddon> rentAddonCollection) {
        this.rentAddonCollection = rentAddonCollection;
    }

    @XmlTransient
    public Collection<Rent> getRentCollection() {
        return rentCollection;
    }

    public void setRentCollection(Collection<Rent> rentCollection) {
        this.rentCollection = rentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (equipname != null ? equipname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipment)) {
            return false;
        }
        Equipment other = (Equipment) object;
        if ((this.equipname == null && other.equipname != null) || (this.equipname != null && !this.equipname.equals(other.equipname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Equipment[ equipname=" + equipname + " ]";
    }
    
}
