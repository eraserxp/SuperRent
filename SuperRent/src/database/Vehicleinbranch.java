/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "VEHICLEINBRANCH")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehicleinbranch.findAll", query = "SELECT v FROM Vehicleinbranch v"),
    @NamedQuery(name = "Vehicleinbranch.findByVid", query = "SELECT v FROM Vehicleinbranch v WHERE v.vid = :vid")})
public class Vehicleinbranch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "VID")
    private Integer vid;
    @JoinColumns({
        @JoinColumn(name = "CITY", referencedColumnName = "CITY"),
        @JoinColumn(name = "LOCATION", referencedColumnName = "LOCATION")})
    @ManyToOne(optional = false)
    private Branch branch;

    public Vehicleinbranch() {
    }

    public Vehicleinbranch(Integer vid) {
        this.vid = vid;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
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
        if (!(object instanceof Vehicleinbranch)) {
            return false;
        }
        Vehicleinbranch other = (Vehicleinbranch) object;
        if ((this.vid == null && other.vid != null) || (this.vid != null && !this.vid.equals(other.vid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Vehicleinbranch[ vid=" + vid + " ]";
    }
    
}
