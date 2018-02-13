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
@Table(name = "USER_VOLUNTEERING_INTEREST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserVolunteeringInterest.findAll", query = "SELECT u FROM UserVolunteeringInterest u")
    , @NamedQuery(name = "UserVolunteeringInterest.findByID", query = "SELECT u FROM UserVolunteeringInterest u WHERE u.ID = :ID")
    , @NamedQuery(name = "UserVolunteeringInterest.findByVolunteeringAreaID", query = "SELECT u FROM UserVolunteeringInterest u WHERE u.volunteeringAreaID = :volunteeringAreaID")
    , @NamedQuery(name = "UserVolunteeringInterest.findByUserID", query = "SELECT u FROM UserVolunteeringInterest u WHERE u.userID = :userID")})
public class UserVolunteeringInterest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer ID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOLUNTEERING_AREA_ID")
    private int volunteeringAreaID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USER_ID")
    private int userID;

    public UserVolunteeringInterest() {
    }

    public UserVolunteeringInterest(Integer ID) {
        this.ID = ID;
    }

    public UserVolunteeringInterest(int volunteeringAreaID, int userID) {
        this.volunteeringAreaID = volunteeringAreaID;
        this.userID = userID;
    }
    
    public UserVolunteeringInterest(Integer ID, int volunteeringAreaID, int userID) {
        this.ID = ID;
        this.volunteeringAreaID = volunteeringAreaID;
        this.userID = userID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public int getVolunteeringAreaID() {
        return volunteeringAreaID;
    }

    public void setVolunteeringAreaID(int volunteeringAreaID) {
        this.volunteeringAreaID = volunteeringAreaID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ID != null ? ID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserVolunteeringInterest)) {
            return false;
        }
        UserVolunteeringInterest other = (UserVolunteeringInterest) object;
        if ((this.ID == null && other.ID != null) || (this.ID != null && !this.ID.equals(other.ID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entityclasses.UserVolunteeringInterest[ id=" + ID + " ]";
    }
    
}
