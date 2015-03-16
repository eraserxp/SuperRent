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
@Table(name = "VEHICLETYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehicletype.findAll", query = "SELECT v FROM Vehicletype v"),
    @NamedQuery(name = "Vehicletype.findByTypename", query = "SELECT v FROM Vehicletype v WHERE v.typename = :typename"),
    @NamedQuery(name = "Vehicletype.findByWRate", query = "SELECT v FROM Vehicletype v WHERE v.wRate = :wRate"),
    @NamedQuery(name = "Vehicletype.findByDRate", query = "SELECT v FROM Vehicletype v WHERE v.dRate = :dRate"),
    @NamedQuery(name = "Vehicletype.findByHRate", query = "SELECT v FROM Vehicletype v WHERE v.hRate = :hRate"),
    @NamedQuery(name = "Vehicletype.findByPkRate", query = "SELECT v FROM Vehicletype v WHERE v.pkRate = :pkRate"),
    @NamedQuery(name = "Vehicletype.findByWInsurance", query = "SELECT v FROM Vehicletype v WHERE v.wInsurance = :wInsurance"),
    @NamedQuery(name = "Vehicletype.findByDInsurance", query = "SELECT v FROM Vehicletype v WHERE v.dInsurance = :dInsurance"),
    @NamedQuery(name = "Vehicletype.findByHInsurance", query = "SELECT v FROM Vehicletype v WHERE v.hInsurance = :hInsurance")})
public class Vehicletype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TYPENAME")
    private String typename;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "W_RATE")
    private Double wRate;
    @Column(name = "D_RATE")
    private Double dRate;
    @Column(name = "H_RATE")
    private Double hRate;
    @Column(name = "PK_RATE")
    private Double pkRate;
    @Column(name = "W_INSURANCE")
    private Double wInsurance;
    @Column(name = "D_INSURANCE")
    private Double dInsurance;
    @Column(name = "H_INSURANCE")
    private Double hInsurance;
    @OneToMany(mappedBy = "vehicletype")
    private Collection<Vehicleforsale> vehicleforsaleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicletype")
    private Collection<Vehicleforrent> vehicleforrentCollection;

    public Vehicletype() {
    }

    public Vehicletype(String typename) {
        this.typename = typename;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Double getWRate() {
        return wRate;
    }

    public void setWRate(Double wRate) {
        this.wRate = wRate;
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

    public Double getPkRate() {
        return pkRate;
    }

    public void setPkRate(Double pkRate) {
        this.pkRate = pkRate;
    }

    public Double getWInsurance() {
        return wInsurance;
    }

    public void setWInsurance(Double wInsurance) {
        this.wInsurance = wInsurance;
    }

    public Double getDInsurance() {
        return dInsurance;
    }

    public void setDInsurance(Double dInsurance) {
        this.dInsurance = dInsurance;
    }

    public Double getHInsurance() {
        return hInsurance;
    }

    public void setHInsurance(Double hInsurance) {
        this.hInsurance = hInsurance;
    }

    @XmlTransient
    public Collection<Vehicleforsale> getVehicleforsaleCollection() {
        return vehicleforsaleCollection;
    }

    public void setVehicleforsaleCollection(Collection<Vehicleforsale> vehicleforsaleCollection) {
        this.vehicleforsaleCollection = vehicleforsaleCollection;
    }

    @XmlTransient
    public Collection<Vehicleforrent> getVehicleforrentCollection() {
        return vehicleforrentCollection;
    }

    public void setVehicleforrentCollection(Collection<Vehicleforrent> vehicleforrentCollection) {
        this.vehicleforrentCollection = vehicleforrentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typename != null ? typename.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehicletype)) {
            return false;
        }
        Vehicletype other = (Vehicletype) object;
        if ((this.typename == null && other.typename != null) || (this.typename != null && !this.typename.equals(other.typename))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Vehicletype[ typename=" + typename + " ]";
    }
    
}
