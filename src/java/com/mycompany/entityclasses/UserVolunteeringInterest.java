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
    , @NamedQuery(name = "UserVolunteeringInterest.findById", query = "SELECT u FROM UserVolunteeringInterest u WHERE u.id = :id")
    , @NamedQuery(name = "UserVolunteeringInterest.findByVolunteeringAreaId", query = "SELECT u FROM UserVolunteeringInterest u WHERE u.volunteeringAreaId = :volunteeringAreaId")
    , @NamedQuery(name = "UserVolunteeringInterest.findByUserId", query = "SELECT u FROM UserVolunteeringInterest u WHERE u.userId = :userId")})
public class UserVolunteeringInterest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOLUNTEERING_AREA_ID")
    private int volunteeringAreaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USER_ID")
    private int userId;

    public UserVolunteeringInterest() {
    }

    public UserVolunteeringInterest(Integer id) {
        this.id = id;
    }

    public UserVolunteeringInterest(Integer id, int volunteeringAreaId, int userId) {
        this.id = id;
        this.volunteeringAreaId = volunteeringAreaId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVolunteeringAreaId() {
        return volunteeringAreaId;
    }

    public void setVolunteeringAreaId(int volunteeringAreaId) {
        this.volunteeringAreaId = volunteeringAreaId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserVolunteeringInterest)) {
            return false;
        }
        UserVolunteeringInterest other = (UserVolunteeringInterest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entityclasses.UserVolunteeringInterest[ id=" + id + " ]";
    }
    
}
