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
public class BranchPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "CITY")
    private String city;
    @Basic(optional = false)
    @Column(name = "LOCATION")
    private String location;

    public BranchPK() {
    }

    public BranchPK(String city, String location) {
        this.city = city;
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (city != null ? city.hashCode() : 0);
        hash += (location != null ? location.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BranchPK)) {
            return false;
        }
        BranchPK other = (BranchPK) object;
        if ((this.city == null && other.city != null) || (this.city != null && !this.city.equals(other.city))) {
            return false;
        }
        if ((this.location == null && other.location != null) || (this.location != null && !this.location.equals(other.location))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.BranchPK[ city=" + city + ", location=" + location + " ]";
    }
    
}
