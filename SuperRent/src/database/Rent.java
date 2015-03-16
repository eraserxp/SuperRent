/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dongshengshen
 */
@Entity
@Table(name = "RENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rent.findAll", query = "SELECT r FROM Rent r"),
    @NamedQuery(name = "Rent.findByRentId", query = "SELECT r FROM Rent r WHERE r.rentId = :rentId"),
    @NamedQuery(name = "Rent.findByIsReserve", query = "SELECT r FROM Rent r WHERE r.isReserve = :isReserve"),
    @NamedQuery(name = "Rent.findByDriverLicense", query = "SELECT r FROM Rent r WHERE r.driverLicense = :driverLicense"),
    @NamedQuery(name = "Rent.findByRentDate", query = "SELECT r FROM Rent r WHERE r.rentDate = :rentDate"),
    @NamedQuery(name = "Rent.findByAdditionalEquipmentIndicator", query = "SELECT r FROM Rent r WHERE r.additionalEquipmentIndicator = :additionalEquipmentIndicator"),
    @NamedQuery(name = "Rent.findByNoOfAdditionalEquipment", query = "SELECT r FROM Rent r WHERE r.noOfAdditionalEquipment = :noOfAdditionalEquipment"),
    @NamedQuery(name = "Rent.findByCardNo", query = "SELECT r FROM Rent r WHERE r.cardNo = :cardNo"),
    @NamedQuery(name = "Rent.findByExpiryDate", query = "SELECT r FROM Rent r WHERE r.expiryDate = :expiryDate"),
    @NamedQuery(name = "Rent.findByFromDate", query = "SELECT r FROM Rent r WHERE r.fromDate = :fromDate"),
    @NamedQuery(name = "Rent.findByToDate", query = "SELECT r FROM Rent r WHERE r.toDate = :toDate"),
    @NamedQuery(name = "Rent.findByFromHour", query = "SELECT r FROM Rent r WHERE r.fromHour = :fromHour"),
    @NamedQuery(name = "Rent.findByToHour", query = "SELECT r FROM Rent r WHERE r.toHour = :toHour")})
public class Rent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RENT_ID")
    private Integer rentId;
    @Column(name = "IS_RESERVE")
    private Boolean isReserve;
    @Column(name = "DRIVER_LICENSE")
    private String driverLicense;
    @Column(name = "RENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date rentDate;
    @Column(name = "ADDITIONAL_EQUIPMENT_INDICATOR")
    private String additionalEquipmentIndicator;
    @Column(name = "NO_OF_ADDITIONAL_EQUIPMENT")
    private Integer noOfAdditionalEquipment;
    @Column(name = "CARD_NO")
    private Integer cardNo;
    @Column(name = "EXPIRY_DATE")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;
    @Column(name = "FROM_DATE")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "TO_DATE")
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @Column(name = "FROM_HOUR")
    @Temporal(TemporalType.TIME)
    private Date fromHour;
    @Column(name = "TO_HOUR")
    @Temporal(TemporalType.TIME)
    private Date toHour;
    @OneToMany(mappedBy = "rentId")
    private Collection<Return> returnCollection;
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

    public Rent() {
    }

    public Rent(Integer rentId) {
        this.rentId = rentId;
    }

    public Integer getRentId() {
        return rentId;
    }

    public void setRentId(Integer rentId) {
        this.rentId = rentId;
    }

    public Boolean getIsReserve() {
        return isReserve;
    }

    public void setIsReserve(Boolean isReserve) {
        this.isReserve = isReserve;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public String getAdditionalEquipmentIndicator() {
        return additionalEquipmentIndicator;
    }

    public void setAdditionalEquipmentIndicator(String additionalEquipmentIndicator) {
        this.additionalEquipmentIndicator = additionalEquipmentIndicator;
    }

    public Integer getNoOfAdditionalEquipment() {
        return noOfAdditionalEquipment;
    }

    public void setNoOfAdditionalEquipment(Integer noOfAdditionalEquipment) {
        this.noOfAdditionalEquipment = noOfAdditionalEquipment;
    }

    public Integer getCardNo() {
        return cardNo;
    }

    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromHour() {
        return fromHour;
    }

    public void setFromHour(Date fromHour) {
        this.fromHour = fromHour;
    }

    public Date getToHour() {
        return toHour;
    }

    public void setToHour(Date toHour) {
        this.toHour = toHour;
    }

    @XmlTransient
    public Collection<Return> getReturnCollection() {
        return returnCollection;
    }

    public void setReturnCollection(Collection<Return> returnCollection) {
        this.returnCollection = returnCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rentId != null ? rentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rent)) {
            return false;
        }
        Rent other = (Rent) object;
        if ((this.rentId == null && other.rentId != null) || (this.rentId != null && !this.rentId.equals(other.rentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Rent[ rentId=" + rentId + " ]";
    }
    
}
