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
import javax.persistence.Transient;

/**
 *
 * @author andres
 */

@Entity
@Table(name = "USERS")
@DiscriminatorValue("1")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Organization.findAll", query = "SELECT u FROM Organization u")
    , @NamedQuery(name = "Organization.findByUserId", query = "SELECT u FROM Organization u WHERE u.userID = :userID")
    , @NamedQuery(name = "Organization.findByEmail", query = "SELECT u FROM Organization u WHERE u.email = :email")
    , @NamedQuery(name = "Organization.findByUsername", query = "SELECT u FROM Organization u WHERE u.username = :username")
    , @NamedQuery(name = "Organization.findByPassword", query = "SELECT u FROM Organization u WHERE u.password = :password")
//    , @NamedQuery(name = "Organization.findByUserRoleId", query = "SELECT u FROM Organization u WHERE u.userRole = :userRole")
    , @NamedQuery(name = "Organization.findBySecurityQuestion", query = "SELECT u FROM Organization u WHERE u.securityQuestion = :securityQuestion")
    , @NamedQuery(name = "Organization.findBySecurityAnswer", query = "SELECT u FROM Organization u WHERE u.securityAnswer = :securityAnswer")
    , @NamedQuery(name = "Organization.findByPhoneNumber", query = "SELECT u FROM Organization u WHERE u.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "Organization.findByFirstName", query = "SELECT u FROM Organization u WHERE u.firstName = :firstName")
    , @NamedQuery(name = "Organization.findByLastName", query = "SELECT u FROM Organization u WHERE u.lastName = :lastName")
    , @NamedQuery(name = "Organization.findByOrganizationName", query = "SELECT u FROM Organization u WHERE u.organizationName = :organizationName")
    , @NamedQuery(name = "Organization.findByMission", query = "SELECT u FROM Organization u WHERE u.mission = :mission")
    , @NamedQuery(name = "Organization.findByWebsite", query = "SELECT u FROM Organization u WHERE u.website = :website")
    , @NamedQuery(name = "Organization.findByAddress", query = "SELECT u FROM Organization u WHERE u.address = :address")
    , @NamedQuery(name = "Organization.findByCity", query = "SELECT u FROM Organization u WHERE u.city = :city")
    , @NamedQuery(name = "Organization.findByState", query = "SELECT u FROM Organization u WHERE u.state = :state")
    , @NamedQuery(name = "Organization.findByZipCode", query = "SELECT u FROM Organization u WHERE u.zipCode = :zipCode")
    , @NamedQuery(name = "Organization.findByVolunteerMatchId", query = "SELECT u FROM Organization u WHERE u.volunteerMatchID = :volunteerMatchID")
    , @NamedQuery(name = "Organization.findByActive", query = "SELECT u FROM Organization u WHERE u.active = :active")})

public class Organization extends Users implements Serializable {
    
    @Transient
    private String imageURL;
    
    public Organization() {
    }

    public Organization(Integer userID) {
        this.userID = userID;
    }
    
    public Organization(Integer volunteerMatchID, String organizationVmName, String mission, String city, int state, String zipCode, String imageURL) {
        this.volunteerMatchID = volunteerMatchID;
        this.organizationVmName = organizationVmName;
        this.mission = mission;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.imageURL = imageURL;
    }

    public Organization(int organizationID, String email, String username, String password, int securityQuestion, String securityAnswer, int state, String zipCode, Character active) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.state = state;
        this.zipCode = zipCode;
        this.active = active;
    }
    
    public String getImageURL() {
        if (imageURL == null || imageURL.isEmpty()) 
            imageURL = Constants.STORAGE_DIRECTORY + "no_image_1.png";
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

