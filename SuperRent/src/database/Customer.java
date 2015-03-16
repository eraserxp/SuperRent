/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "CUSTOMER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByUsername", query = "SELECT c FROM Customer c WHERE c.username = :username"),
    @NamedQuery(name = "Customer.findByPasswd", query = "SELECT c FROM Customer c WHERE c.passwd = :passwd"),
    @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name"),
    @NamedQuery(name = "Customer.findByIsroadstar", query = "SELECT c FROM Customer c WHERE c.isroadstar = :isroadstar"),
    @NamedQuery(name = "Customer.findByIsclubmember", query = "SELECT c FROM Customer c WHERE c.isclubmember = :isclubmember"),
    @NamedQuery(name = "Customer.findByPoint", query = "SELECT c FROM Customer c WHERE c.point = :point"),
    @NamedQuery(name = "Customer.findByPhone", query = "SELECT c FROM Customer c WHERE c.phone = :phone")})
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWD")
    private String passwd;
    @Column(name = "NAME")
    private String name;
    @Lob
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "ISROADSTAR")
    private Boolean isroadstar;
    @Column(name = "ISCLUBMEMBER")
    private Boolean isclubmember;
    @Column(name = "POINT")
    private Integer point;
    @Column(name = "PHONE")
    private Integer phone;
    @OneToMany(mappedBy = "customerUsername")
    private Collection<Reservation> reservationCollection;
    @OneToMany(mappedBy = "customerUsername")
    private Collection<Rent> rentCollection;

    public Customer() {
    }

    public Customer(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsroadstar() {
        return isroadstar;
    }

    public void setIsroadstar(Boolean isroadstar) {
        this.isroadstar = isroadstar;
    }

    public Boolean getIsclubmember() {
        return isclubmember;
    }

    public void setIsclubmember(Boolean isclubmember) {
        this.isclubmember = isclubmember;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    @XmlTransient
    public Collection<Reservation> getReservationCollection() {
        return reservationCollection;
    }

    public void setReservationCollection(Collection<Reservation> reservationCollection) {
        this.reservationCollection = reservationCollection;
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
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Customer[ username=" + username + " ]";
    }
    
}
