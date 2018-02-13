/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andres
 */
@Entity
@Table(name = "VOLUNTEERING_HISTORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VolunteeringHistory.findAll", query = "SELECT v FROM VolunteeringHistory v")
    , @NamedQuery(name = "VolunteeringHistory.findByRecordID", query = "SELECT v FROM VolunteeringHistory v WHERE v.recordID = :recordID")
    , @NamedQuery(name = "VolunteeringHistory.findByUserID", query = "SELECT v FROM VolunteeringHistory v WHERE v.participant.userID = :userID")
    , @NamedQuery(name = "VolunteeringHistory.findByOpportunityID", query = "SELECT v FROM VolunteeringHistory v WHERE v.opportunityID = :opportunityID")
    , @NamedQuery(name = "VolunteeringHistory.findByParticipated", query = "SELECT v FROM VolunteeringHistory v WHERE v.participated = :participated")})
public class VolunteeringHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer recordID;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "USER_ID")
//    private int userID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOLUNTEERING_OPPORTUNITY_ID")
    private int opportunityID;
    @Column(name = "PARTICIPATED")
    private String participated;
    
    @NotNull
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users participant;

    public VolunteeringHistory() {
    }

    public VolunteeringHistory(Integer recordID) {
        this.recordID = recordID;
    }

    public VolunteeringHistory(Integer recordID, int opportunityID) {
        this.recordID = recordID;
//        this.userID = userID;
        this.opportunityID = opportunityID;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

    public Users getParticipant() {
        return participant;
    }

    public void setParticipant(Users participant) {
        this.participant = participant;
    }
    
//    public int getUserID() {
//        return userID;
//    }
//
//    public void setUserID(int userID) {
//        this.userID = userID;
//    }

    public int getOpportunityID() {
        return opportunityID;
    }

    public void setOpportunityID(int opportunityID) {
        this.opportunityID = opportunityID;
    }

    public String getParticipated() {
        return participated;
    }

    public void setParticipated(String participated) {
        this.participated = participated;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordID != null ? recordID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VolunteeringHistory)) {
            return false;
        }
        VolunteeringHistory other = (VolunteeringHistory) object;
        if ((this.recordID == null && other.recordID != null) || (this.recordID != null && !this.recordID.equals(other.recordID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entityclasses.VolunteeringHistory[ id=" + recordID + " ]";
    }
    
}
