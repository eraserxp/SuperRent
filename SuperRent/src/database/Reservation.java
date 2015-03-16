/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dongshengshen
 */
@Entity
@Table(name = "RESERVATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r"),
    @NamedQuery(name = "Reservation.findByConfirmationNumber", query = "SELECT r FROM Reservation r WHERE r.confirmationNumber = :confirmationNumber"),
    @NamedQuery(name = "Reservation.findByPickupDate", query = "SELECT r FROM Reservation r WHERE r.pickupDate = :pickupDate"),
    @NamedQuery(name = "Reservation.findByReturnDate", query = "SELECT r FROM Reservation r WHERE r.returnDate = :returnDate"),
    @NamedQuery(name = "Reservation.findByPickupTime", query = "SELECT r FROM Reservation r WHERE r.pickupTime = :pickupTime"),
    @NamedQuery(name = "Reservation.findByReturnTime", query = "SELECT r FROM Reservation r WHERE r.returnTime = :returnTime"),
    @NamedQuery(name = "Reservation.findByEstimationCost", query = "SELECT r FROM Reservation r WHERE r.estimationCost = :estimationCost"),
    @NamedQuery(name = "Reservation.findByAdditionalEquipmentIndicator", query = "SELECT r FROM Reservation r WHERE r.additionalEquipmentIndicator = :additionalEquipmentIndicator"),
    @NamedQuery(name = "Reservation.findByNoOfAddtionalEquipment", query = "SELECT r FROM Reservation r WHERE r.noOfAddtionalEquipment = :noOfAddtionalEquipment")})
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CONFIRMATION_NUMBER")
    private Integer confirmationNumber;
    @Column(name = "PICKUP_DATE")
    @Temporal(TemporalType.DATE)
    private Date pickupDate;
    @Column(name = "RETURN_DATE")
    @Temporal(TemporalType.DATE)
    private Date returnDate;
    @Column(name = "PICKUP_TIME")
    @Temporal(TemporalType.TIME)
    private Date pickupTime;
    @Column(name = "RETURN_TIME")
    @Temporal(TemporalType.TIME)
    private Date returnTime;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ESTIMATION_COST")
    private Double estimationCost;
    @Column(name = "ADDITIONAL_EQUIPMENT_INDICATOR")
    private String additionalEquipmentIndicator;
    @Column(name = "NO_OF_ADDTIONAL_EQUIPMENT")
    private Integer noOfAddtionalEquipment;
    @JoinColumns({
        @JoinColumn(name = "BRANCH_CITY", referencedColumnName = "CITY"),
        @JoinColumn(name = "BRANCH_LOCATION", referencedColumnName = "LOCATION")})
    @ManyToOne
    private Branch branch;
    @JoinColumn(name = "CUSTOMER_USERNAME", referencedColumnName = "USERNAME")
    @ManyToOne
    private Customer customerUsername;
    @JoinColumn(name = "EQUIP_NAME", referencedColumnName = "EQUIPNAME")
    @ManyToOne
    private Equipment equipName;
    @JoinColumn(name = "VID", referencedColumnName = "VID")
    @ManyToOne
    private Vehicle vid;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reservation")
    private ReserveAddon reserveAddon;

    public Reservation() {
    }

    public Reservation(Integer confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public Integer getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(Integer confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Double getEstimationCost() {
        return estimationCost;
    }

    public void setEstimationCost(Double estimationCost) {
        this.estimationCost = estimationCost;
    }

    public String getAdditionalEquipmentIndicator() {
        return additionalEquipmentIndicator;
    }

    public void setAdditionalEquipmentIndicator(String additionalEquipmentIndicator) {
        this.additionalEquipmentIndicator = additionalEquipmentIndicator;
    }

    public Integer getNoOfAddtionalEquipment() {
        return noOfAddtionalEquipment;
    }

    public void setNoOfAddtionalEquipment(Integer noOfAddtionalEquipment) {
        this.noOfAddtionalEquipment = noOfAddtionalEquipment;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Customer getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(Customer customerUsername) {
        this.customerUsername = customerUsername;
    }

    public Equipment getEquipName() {
        return equipName;
    }

    public void setEquipName(Equipment equipName) {
        this.equipName = equipName;
    }

    public Vehicle getVid() {
        return vid;
    }

    public void setVid(Vehicle vid) {
        this.vid = vid;
    }

    public ReserveAddon getReserveAddon() {
        return reserveAddon;
    }

    public void setReserveAddon(ReserveAddon reserveAddon) {
        this.reserveAddon = reserveAddon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (confirmationNumber != null ? confirmationNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.confirmationNumber == null && other.confirmationNumber != null) || (this.confirmationNumber != null && !this.confirmationNumber.equals(other.confirmationNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Reservation[ confirmationNumber=" + confirmationNumber + " ]";
    }
    
}
