/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "BRANCH")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Branch.findAll", query = "SELECT b FROM Branch b"),
    @NamedQuery(name = "Branch.findByCity", query = "SELECT b FROM Branch b WHERE b.branchPK.city = :city"),
    @NamedQuery(name = "Branch.findByLocation", query = "SELECT b FROM Branch b WHERE b.branchPK.location = :location")})
public class Branch implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BranchPK branchPK;
    @JoinTable(name = "WORKS_IN", joinColumns = {
        @JoinColumn(name = "CITY", referencedColumnName = "CITY"),
        @JoinColumn(name = "LOCATION", referencedColumnName = "LOCATION")}, inverseJoinColumns = {
        @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")})
    @ManyToMany
    private Collection<Superruser> superruserCollection;
    @OneToMany(mappedBy = "branch")
    private Collection<Reservation> reservationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private Collection<Vehicleinbranch> vehicleinbranchCollection;
    @OneToMany(mappedBy = "branch")
    private Collection<Superruser> superruserCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private Collection<KeepEquipment> keepEquipmentCollection;
    @OneToMany(mappedBy = "branch")
    private Collection<Return> returnCollection;
    @OneToMany(mappedBy = "branch")
    private Collection<Rent> rentCollection;

    public Branch() {
    }

    public Branch(BranchPK branchPK) {
        this.branchPK = branchPK;
    }

    public Branch(String city, String location) {
        this.branchPK = new BranchPK(city, location);
    }

    public BranchPK getBranchPK() {
        return branchPK;
    }

    public void setBranchPK(BranchPK branchPK) {
        this.branchPK = branchPK;
    }

    @XmlTransient
    public Collection<Superruser> getSuperruserCollection() {
        return superruserCollection;
    }

    public void setSuperruserCollection(Collection<Superruser> superruserCollection) {
        this.superruserCollection = superruserCollection;
    }

    @XmlTransient
    public Collection<Reservation> getReservationCollection() {
        return reservationCollection;
    }

    public void setReservationCollection(Collection<Reservation> reservationCollection) {
        this.reservationCollection = reservationCollection;
    }

    @XmlTransient
    public Collection<Vehicleinbranch> getVehicleinbranchCollection() {
        return vehicleinbranchCollection;
    }

    public void setVehicleinbranchCollection(Collection<Vehicleinbranch> vehicleinbranchCollection) {
        this.vehicleinbranchCollection = vehicleinbranchCollection;
    }

    @XmlTransient
    public Collection<Superruser> getSuperruserCollection1() {
        return superruserCollection1;
    }

    public void setSuperruserCollection1(Collection<Superruser> superruserCollection1) {
        this.superruserCollection1 = superruserCollection1;
    }

    @XmlTransient
    public Collection<KeepEquipment> getKeepEquipmentCollection() {
        return keepEquipmentCollection;
    }

    public void setKeepEquipmentCollection(Collection<KeepEquipment> keepEquipmentCollection) {
        this.keepEquipmentCollection = keepEquipmentCollection;
    }

    @XmlTransient
    public Collection<Return> getReturnCollection() {
        return returnCollection;
    }

    public void setReturnCollection(Collection<Return> returnCollection) {
        this.returnCollection = returnCollection;
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
        hash += (branchPK != null ? branchPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Branch)) {
            return false;
        }
        Branch other = (Branch) object;
        if ((this.branchPK == null && other.branchPK != null) || (this.branchPK != null && !this.branchPK.equals(other.branchPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Branch[ branchPK=" + branchPK + " ]";
    }
    
}
