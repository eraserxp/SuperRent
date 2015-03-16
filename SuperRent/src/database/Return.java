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
import javax.persistence.JoinColumns;
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
@Table(name = "RETURN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Return.findAll", query = "SELECT r FROM Return r"),
    @NamedQuery(name = "Return.findByReturnid", query = "SELECT r FROM Return r WHERE r.returnid = :returnid"),
    @NamedQuery(name = "Return.findByReturnDate", query = "SELECT r FROM Return r WHERE r.returnDate = :returnDate"),
    @NamedQuery(name = "Return.findByReturnTime", query = "SELECT r FROM Return r WHERE r.returnTime = :returnTime"),
    @NamedQuery(name = "Return.findByCustomerUsername", query = "SELECT r FROM Return r WHERE r.customerUsername = :customerUsername"),
    @NamedQuery(name = "Return.findByTankFullIndicator", query = "SELECT r FROM Return r WHERE r.tankFullIndicator = :tankFullIndicator"),
    @NamedQuery(name = "Return.findByOdometerReading", query = "SELECT r FROM Return r WHERE r.odometerReading = :odometerReading"),
    @NamedQuery(name = "Return.findByRentalCharge", query = "SELECT r FROM Return r WHERE r.rentalCharge = :rentalCharge"),
    @NamedQuery(name = "Return.findByInsuranceCost", query = "SELECT r FROM Return r WHERE r.insuranceCost = :insuranceCost"),
    @NamedQuery(name = "Return.findByAdditionalCost", query = "SELECT r FROM Return r WHERE r.additionalCost = :additionalCost"),
    @NamedQuery(name = "Return.findByTotalCost", query = "SELECT r FROM Return r WHERE r.totalCost = :totalCost"),
    @NamedQuery(name = "Return.findByPaymentMethod", query = "SELECT r FROM Return r WHERE r.paymentMethod = :paymentMethod")})
public class Return implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RETURNID")
    private Integer returnid;
    @Column(name = "RETURN_DATE")
    @Temporal(TemporalType.TIME)
    private Date returnDate;
    @Column(name = "RETURN_TIME")
    @Temporal(TemporalType.TIME)
    private Date returnTime;
    @Column(name = "CUSTOMER_USERNAME")
    private String customerUsername;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TANK_FULL_INDICATOR")
    private Double tankFullIndicator;
    @Column(name = "ODOMETER_READING")
    private Double odometerReading;
    @Column(name = "RENTAL_CHARGE")
    private Double rentalCharge;
    @Column(name = "INSURANCE_COST")
    private Double insuranceCost;
    @Column(name = "ADDITIONAL_COST")
    private Double additionalCost;
    @Column(name = "TOTAL_COST")
    private Double totalCost;
    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;
    @JoinColumns({
        @JoinColumn(name = "BRANCH_CITY", referencedColumnName = "CITY"),
        @JoinColumn(name = "BRANCH_LOCATION", referencedColumnName = "LOCATION")})
    @ManyToOne
    private Branch branch;
    @JoinColumn(name = "RENT_ID", referencedColumnName = "RENT_ID")
    @ManyToOne
    private Rent rentId;
    @JoinColumn(name = "VID", referencedColumnName = "VID")
    @ManyToOne
    private Vehicle vid;

    public Return() {
    }

    public Return(Integer returnid) {
        this.returnid = returnid;
    }

    public Integer getReturnid() {
        return returnid;
    }

    public void setReturnid(Integer returnid) {
        this.returnid = returnid;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public Double getTankFullIndicator() {
        return tankFullIndicator;
    }

    public void setTankFullIndicator(Double tankFullIndicator) {
        this.tankFullIndicator = tankFullIndicator;
    }

    public Double getOdometerReading() {
        return odometerReading;
    }

    public void setOdometerReading(Double odometerReading) {
        this.odometerReading = odometerReading;
    }

    public Double getRentalCharge() {
        return rentalCharge;
    }

    public void setRentalCharge(Double rentalCharge) {
        this.rentalCharge = rentalCharge;
    }

    public Double getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(Double insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    public Double getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(Double additionalCost) {
        this.additionalCost = additionalCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Rent getRentId() {
        return rentId;
    }

    public void setRentId(Rent rentId) {
        this.rentId = rentId;
    }

    public Vehicle getVid() {
        return vid;
    }

    public void setVid(Vehicle vid) {
        this.vid = vid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (returnid != null ? returnid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Return)) {
            return false;
        }
        Return other = (Return) object;
        if ((this.returnid == null && other.returnid != null) || (this.returnid != null && !this.returnid.equals(other.returnid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Return[ returnid=" + returnid + " ]";
    }
    
}
