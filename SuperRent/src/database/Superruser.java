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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dongshengshen
 */
@Entity
@Table(name = "SUPERRUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Superruser.findAll", query = "SELECT s FROM Superruser s"),
    @NamedQuery(name = "Superruser.findByUsername", query = "SELECT s FROM Superruser s WHERE s.username = :username"),
    @NamedQuery(name = "Superruser.findByPassword", query = "SELECT s FROM Superruser s WHERE s.password = :password"),
    @NamedQuery(name = "Superruser.findByName", query = "SELECT s FROM Superruser s WHERE s.name = :name"),
    @NamedQuery(name = "Superruser.findByType", query = "SELECT s FROM Superruser s WHERE s.type = :type")})
public class Superruser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TYPE")
    private String type;
    @ManyToMany(mappedBy = "superruserCollection")
    private Collection<Branch> branchCollection;
    @JoinColumns({
        @JoinColumn(name = "CITY", referencedColumnName = "CITY"),
        @JoinColumn(name = "LOCATION", referencedColumnName = "LOCATION")})
    @ManyToOne
    private Branch branch;

    public Superruser() {
    }

    public Superruser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<Branch> getBranchCollection() {
        return branchCollection;
    }

    public void setBranchCollection(Collection<Branch> branchCollection) {
        this.branchCollection = branchCollection;
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
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Superruser)) {
            return false;
        }
        Superruser other = (Superruser) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Superruser[ username=" + username + " ]";
    }
    
}
