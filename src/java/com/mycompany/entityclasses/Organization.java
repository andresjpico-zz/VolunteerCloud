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
    
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "USER_ID")
//    private Integer userID;
//    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 255)
//    @Column(name = "EMAIL")
//    private String email;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 255)
//    @Column(name = "USERNAME")
//    private String username;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 255)
//    @Column(name = "PASSWORD")
//    private String password;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "USER_ROLE_ID")
//    private int userRole;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "SECURITY_QUESTION")
//    private int securityQuestion;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 255)
//    @Column(name = "SECURITY_ANSWER")
//    private String securityAnswer;
//    @Size(max = 255)
//    @Column(name = "PHONE_NUMBER")
//    private String phoneNumber;
//    @Size(max = 255)
//    @Column(name = "FIRST_NAME")
//    private String firstName;
//    @Size(max = 255)
//    @Column(name = "LAST_NAME")
//    private String lastName;
//    @Size(max = 255)
//    @Column(name = "ORGANIZATION_NAME")
//    private String organizationName;
//    @Size(max = 255)
//    @Column(name = "MISSION")
//    private String mission;
//    @Size(max = 255)
//    @Column(name = "WEBSITE")
//    private String website;
//    @Size(max = 255)
//    @Column(name = "ADDRESS")
//    private String address;
//    @Size(max = 255)
//    @Column(name = "CITY")
//    private String city;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "STATE")
//    private int state;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "ZIP_CODE")
//    private int zipCode;
    @Column(name = "VOLUNTEER_MATCH_ID")
    private Integer volunteerMatchID;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "ACTIVE")
//    private Character active;

    public Organization() {
    }

    public Organization(Integer organizationID) {
        this.userID = userID;
    }

    public Organization(Integer organizationID, String email, String username, String password, int securityQuestion, String securityAnswer, int state, String zipCode, Character active) {
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

}

