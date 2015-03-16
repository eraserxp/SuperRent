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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dongshengshen
 */
@Entity
@Table(name = "RESERVE_ADDON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReserveAddon.findAll", query = "SELECT r FROM ReserveAddon r"),
    @NamedQuery(name = "ReserveAddon.findByConfirmno", query = "SELECT r FROM ReserveAddon r WHERE r.confirmno = :confirmno")})
public class ReserveAddon implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CONFIRMNO")
    private Integer confirmno;
    @JoinColumn(name = "EQUIPNAME", referencedColumnName = "EQUIPNAME")
    @ManyToOne
    private Equipment equipname;
    @JoinColumn(name = "CONFIRMNO", referencedColumnName = "CONFIRMATION_NUMBER", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Reservation reservation;

    public ReserveAddon() {
    }

    public ReserveAddon(Integer confirmno) {
        this.confirmno = confirmno;
    }

    public Integer getConfirmno() {
        return confirmno;
    }

    public void setConfirmno(Integer confirmno) {
        this.confirmno = confirmno;
    }

    public Equipment getEquipname() {
        return equipname;
    }

    public void setEquipname(Equipment equipname) {
        this.equipname = equipname;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (confirmno != null ? confirmno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReserveAddon)) {
            return false;
        }
        ReserveAddon other = (ReserveAddon) object;
        if ((this.confirmno == null && other.confirmno != null) || (this.confirmno != null && !this.confirmno.equals(other.confirmno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.ReserveAddon[ confirmno=" + confirmno + " ]";
    }
    
}
