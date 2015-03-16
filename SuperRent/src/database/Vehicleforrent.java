/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dongshengshen
 */
@Entity
@Table(name = "VEHICLEFORRENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehicleforrent.findAll", query = "SELECT v FROM Vehicleforrent v"),
    @NamedQuery(name = "Vehicleforrent.findByVid", query = "SELECT v FROM Vehicleforrent v WHERE v.vid = :vid"),
    @NamedQuery(name = "Vehicleforrent.findByIsavailable", query = "SELECT v FROM Vehicleforrent v WHERE v.isavailable = :isavailable"),
    @NamedQuery(name = "Vehicleforrent.findByStartingYear", query = "SELECT v FROM Vehicleforrent v WHERE v.startingYear = :startingYear"),
    @NamedQuery(name = "Vehicleforrent.findByCategory", query = "SELECT v FROM Vehicleforrent v WHERE v.category = :category")})
public class Vehicleforrent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "VID")
    private Integer vid;
    @Column(name = "ISAVAILABLE")
    private Boolean isavailable;
    @Column(name = "STARTING_YEAR")
    @Temporal(TemporalType.DATE)
    private Date startingYear;
    @Column(name = "CATEGORY")
    private String category;
    @JoinColumn(name = "VEHICLETYPE", referencedColumnName = "TYPENAME")
    @ManyToOne(optional = false)
    private Vehicletype vehicletype;

    public Vehicleforrent() {
    }

    public Vehicleforrent(Integer vid) {
        this.vid = vid;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Boolean getIsavailable() {
        return isavailable;
    }

    public void setIsavailable(Boolean isavailable) {
        this.isavailable = isavailable;
    }

    public Date getStartingYear() {
        return startingYear;
    }

    public void setStartingYear(Date startingYear) {
        this.startingYear = startingYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Vehicletype getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(Vehicletype vehicletype) {
        this.vehicletype = vehicletype;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vid != null ? vid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehicleforrent)) {
            return false;
        }
        Vehicleforrent other = (Vehicleforrent) object;
        if ((this.vid == null && other.vid != null) || (this.vid != null && !this.vid.equals(other.vid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Vehicleforrent[ vid=" + vid + " ]";
    }
    
}
