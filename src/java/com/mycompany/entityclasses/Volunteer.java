/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author andres
 */

@Entity
@Table(name = "USERS")
@DiscriminatorValue("0")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Volunteer.findAll", query = "SELECT u FROM Volunteer u")
    , @NamedQuery(name = "Volunteer.findByUserId", query = "SELECT u FROM Volunteer u WHERE u.userID = :userID")
    , @NamedQuery(name = "Volunteer.findByEmail", query = "SELECT u FROM Volunteer u WHERE u.email = :email")
    , @NamedQuery(name = "Volunteer.findByUsername", query = "SELECT u FROM Volunteer u WHERE u.username = :username")
    , @NamedQuery(name = "Volunteer.findByPassword", query = "SELECT u FROM Volunteer u WHERE u.password = :password")
    , @NamedQuery(name = "Volunteer.findBySecurityQuestion", query = "SELECT u FROM Volunteer u WHERE u.securityQuestion = :securityQuestion")
    , @NamedQuery(name = "Volunteer.findBySecurityAnswer", query = "SELECT u FROM Volunteer u WHERE u.securityAnswer = :securityAnswer")
    , @NamedQuery(name = "Volunteer.findByPhoneNumber", query = "SELECT u FROM Volunteer u WHERE u.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "Volunteer.findByFirstName", query = "SELECT u FROM Volunteer u WHERE u.firstName = :firstName")
    , @NamedQuery(name = "Volunteer.findByLastName", query = "SELECT u FROM Volunteer u WHERE u.lastName = :lastName")
    , @NamedQuery(name = "Volunteer.findByMission", query = "SELECT u FROM Volunteer u WHERE u.mission = :mission")
    , @NamedQuery(name = "Volunteer.findByWebsite", query = "SELECT u FROM Volunteer u WHERE u.website = :website")
    , @NamedQuery(name = "Volunteer.findByAddress", query = "SELECT u FROM Volunteer u WHERE u.address = :address")
    , @NamedQuery(name = "Volunteer.findByCity", query = "SELECT u FROM Volunteer u WHERE u.city = :city")
    , @NamedQuery(name = "Volunteer.findByState", query = "SELECT u FROM Volunteer u WHERE u.state = :state")
    , @NamedQuery(name = "Volunteer.findByZipCode", query = "SELECT u FROM Volunteer u WHERE u.zipCode = :zipCode")
    , @NamedQuery(name = "Volunteer.findByActive", query = "SELECT u FROM Volunteer u WHERE u.active = :active")})

public class Volunteer extends Users implements Serializable {
    
    public Volunteer() {
    }

    public Volunteer(Integer userID) {
        this.userID = userID;
    }

    public Volunteer(Integer userID, String email, String username, String password, int securityQuestion, String securityAnswer, int state, String zipCode, Character active) {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.state = state;
        this.zipCode = zipCode;
        this.active = active;
    }
    
    @Override
    public String getOrganizationName() {
        return null;
    }

    @Override
    public void setOrganizationName(String organizationName) {
    }

    @Override
    public Integer getVolunteerMatchID() {
        return null;
    }

    @Override
    public void setVolunteerMatchID(Integer volunteerMatchID) {
    }
    
}
