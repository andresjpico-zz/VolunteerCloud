/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entityclasses;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import com.mycompany.entityclasses.Organization;
import com.mycompany.sessionbeans.OrganizationFacade;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;

/**
 *
 * @author andres
 */

//SELECT VOLUNTEERING_OPPORTUNITIES.*, ORGANIZATION_NAME
//FROM VOLUNTEERING_OPPORTUNITIES
//LEFT JOIN USERS 
//ON VOLUNTEERING_OPPORTUNITIES.OWNER_ID = USERS.USER_ID;

@Entity
@Table(name = "VOLUNTEERING_OPPORTUNITIES")
//@SecondaryTable(name = "USERS", pkJoinColumns = { @PrimaryKeyJoinColumn(name="USER_ID", referencedColumnName="OWNER_ID")})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VolunteeringOpportunities.findAll", query = "SELECT v FROM VolunteeringOpportunities v")
    , @NamedQuery(name = "VolunteeringOpportunities.findById", query = "SELECT v FROM VolunteeringOpportunities v WHERE v.opportunityID = :opportunityID")
    , @NamedQuery(name = "VolunteeringOpportunities.findByOwnerId", query = "SELECT v FROM VolunteeringOpportunities v WHERE v.ownerID = :ownerID")
    , @NamedQuery(name = "VolunteeringOpportunities.findByVolunteeringAreaId", query = "SELECT v FROM VolunteeringOpportunities v WHERE v.volunteeringAreaID = :volunteeringAreaID")
    , @NamedQuery(name = "VolunteeringOpportunities.findByTitle", query = "SELECT v FROM VolunteeringOpportunities v WHERE v.title = :title")
    , @NamedQuery(name = "VolunteeringOpportunities.findByDescription", query = "SELECT v FROM VolunteeringOpportunities v WHERE v.description = :description")
    , @NamedQuery(name = "VolunteeringOpportunities.findByAddress", query = "SELECT v FROM VolunteeringOpportunities v WHERE v.address = :address")
    , @NamedQuery(name = "VolunteeringOpportunities.findByCity", query = "SELECT v FROM VolunteeringOpportunities v WHERE v.city = :city")
    , @NamedQuery(name = "VolunteeringOpportunities.findByState", query = "SELECT v FROM VolunteeringOpportunities v WHERE v.state = :state")
    , @NamedQuery(name = "VolunteeringOpportunities.findByZipCode", query = "SELECT v FROM VolunteeringOpportunities v WHERE v.zipCode = :zipCode")
    , @NamedQuery(name = "VolunteeringOpportunities.findByDateOccurrence", query = "SELECT v FROM VolunteeringOpportunities v WHERE v.dateOccurrence = :dateOccurrence")
    , @NamedQuery(name = "VolunteeringOpportunities.findByVolunteerMatchId", query = "SELECT v FROM VolunteeringOpportunities v WHERE v.volunteerMatchID = :volunteerMatchID")
    , @NamedQuery(name = "VolunteeringOpportunities.findByActive", query = "SELECT v FROM VolunteeringOpportunities v WHERE v.active = :active")})
public class VolunteeringOpportunities implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer opportunityID;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "OWNER_ID")
//    private int ownerID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOLUNTEERING_AREA_ID")
    private int volunteeringAreaID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TITLE")
    private String title;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 255)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 255)
    @Column(name = "CITY")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATE")
    private int state;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ZIP_CODE")
    private String zipCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_OCCURRENCE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOccurrence;
    @Column(name = "VOLUNTEER_MATCH_ID")
    private Integer volunteerMatchID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVE")
    private Character active;
//    @Basic(optional = false)
//    @Column(name = "ORGANIZATION_NAME", table = "USERS", insertable = false, updatable = false)
//    private String organizationName;

//    @ManyToOne
//    @JoinColumn(name = "USER_ID", referencedColumnName = "OWNER_ID")
//    private int userID;
//    @Basic(optional = false)

    @NotNull
    @JoinColumn(name = "OWNER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users ownerID;

    
    public VolunteeringOpportunities() {
    }

    public VolunteeringOpportunities(Integer opportunityID) {
        this.opportunityID = opportunityID;
    }

    public VolunteeringOpportunities(Integer opportunityID, int volunteeringAreaID, String title, String description, String address, String city, int state, String zipCode, Date dateOccurrence, Character active) {
        this.opportunityID = opportunityID;
//        this.ownerID = ownerID;
        this.volunteeringAreaID = volunteeringAreaID;
        this.title = title;
        this.description = description;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.dateOccurrence = dateOccurrence;
        this.active = active;
    }

    public Integer getOpportunityID() {
        return opportunityID;
    }

    public void setOpportunityID(Integer opportunityID) {
        this.opportunityID = opportunityID;
    }

    public Users getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Users ownerID) {
        this.ownerID = ownerID;
    }

    public int getVolunteeringAreaID() {
        return volunteeringAreaID;
    }

    public void setVolunteeringAreaID(int volunteeringAreaID) {
        this.volunteeringAreaID = volunteeringAreaID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Date getDateOccurrence() {
        return dateOccurrence;
    }

    public void setDateOccurrence(Date dateOccurrence) {
        this.dateOccurrence = dateOccurrence;
    }

    public Integer getVolunteerMatchID() {
        return volunteerMatchID;
    }

    public void setVolunteerMatchID(Integer volunteerMatchID) {
        this.volunteerMatchID = volunteerMatchID;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
    }
    
    public String getStateName() {
        return Constants.STATES[state];
    }
    
    public String getVolunteeringArea() {
        return Constants.VOLUNTEERING_AREA[volunteeringAreaID];
    }
    
//    public String getOrganizationName() {
//        return organizationName;
//    }
    
//    public int getUserID() {
//        return userID;
//    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (opportunityID != null ? opportunityID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VolunteeringOpportunities)) {
            return false;
        }
        VolunteeringOpportunities other = (VolunteeringOpportunities) object;
        if ((this.opportunityID == null && other.opportunityID!= null) || (this.opportunityID != null && !this.opportunityID.equals(other.opportunityID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entityclasses.VolunteeringOpportunities[ id=" + opportunityID + " ]";
    }
    
}
