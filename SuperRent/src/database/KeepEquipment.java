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
@Table(name = "KEEP_EQUIPMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KeepEquipment.findAll", query = "SELECT k FROM KeepEquipment k"),
    @NamedQuery(name = "KeepEquipment.findByEquipname", query = "SELECT k FROM KeepEquipment k WHERE k.equipname = :equipname"),
    @NamedQuery(name = "KeepEquipment.findByQuantity", query = "SELECT k FROM KeepEquipment k WHERE k.quantity = :quantity")})
public class KeepEquipment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EQUIPNAME")
    private String equipname;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @JoinColumns({
        @JoinColumn(name = "CITY", referencedColumnName = "CITY"),
        @JoinColumn(name = "LOCATION", referencedColumnName = "LOCATION")})
    @ManyToOne(optional = false)
    private Branch branch;

    public KeepEquipment() {
    }

    public KeepEquipment(String equipname) {
        this.equipname = equipname;
    }

    public String getEquipname() {
        return equipname;
    }

    public void setEquipname(String equipname) {
        this.equipname = equipname;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
        hash += (equipname != null ? equipname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeepEquipment)) {
            return false;
        }
        KeepEquipment other = (KeepEquipment) object;
        if ((this.equipname == null && other.equipname != null) || (this.equipname != null && !this.equipname.equals(other.equipname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.KeepEquipment[ equipname=" + equipname + " ]";
    }
    
}
