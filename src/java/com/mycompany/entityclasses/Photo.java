/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entityclasses;

import com.mycompany.entityclasses.Constants;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andres
 */
@Entity
@Table(name = "PHOTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Photo.findByUserId", query = "SELECT p FROM Photo p WHERE p.userID = :userID")
    , @NamedQuery(name = "Photo.findAll", query = "SELECT p FROM Photo p")
    , @NamedQuery(name = "Photo.findByPhotoId", query = "SELECT p FROM Photo p WHERE p.photoID = :photoID")
    , @NamedQuery(name = "Photo.findByExtension", query = "SELECT p FROM Photo p WHERE p.extension = :extension")
    , @NamedQuery(name = "Photo.findByOpportunityId", query = "SELECT p FROM Photo p WHERE p.opportunityID = :opportunityID")})
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PHOTO_ID")
    private Integer photoID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "EXTENSION")
    private String extension;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USER_ID")
    private int userID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OPPORTUNITY_ID")
    private Integer opportunityID;

    public Photo() {
    }

    public Photo(Integer photoID) {
        this.photoID = photoID;
    }

    public Photo(Integer photoID, String extension, int userID, Integer opportunityID) {
        this.photoID = photoID;
        this.extension = extension;
        this.userID = userID;
        this.opportunityID = opportunityID;
    }
    
    // This method is added to the generated code
    public Photo(String extension, Users user) {
        this.extension = extension;
        this.userID = user.getUserID();
    }

    public Integer getphotoID() {
        return photoID;
    }

    public void setphotoID(Integer photoID) {
        this.photoID = photoID;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userId) {
        this.userID = userId;
    }
    
    public Integer getOpportunityID() {
        return opportunityID;
    }

    public void setOpportunityID(Integer opportunityID) {
        this.opportunityID = opportunityID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (photoID != null ? photoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Photo)) {
            return false;
        }
        Photo other = (Photo) object;
        if ((this.photoID == null && other.photoID != null) || (this.photoID != null && !this.photoID.equals(other.photoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entityclasses.Photo[ photoId=" + photoID + " ]";
    }
    
    
    /*
    =====================================================
    The following methods are added to the generated code
    =====================================================
     */
    public String getFilePath() {
        return Constants.ROOT_DIRECTORY + getFilename();
    }

    // Roommate's photo image file is named as Roommate's unique id.file extension name.
    // Example: 38.jpeg
    public String getFilename() {
        return getphotoID() + "." + getExtension();
    }

    public String getThumbnailFilePath() {
        return Constants.ROOT_DIRECTORY + getThumbnailName();
    }

    /*
    Roommate's photo thumbnail image file is named as 
    the primary key (id) of the Roommate's photo_thumbnail.file extension name.
    Example: 38_thumbnail.jpeg
     */
    public String getThumbnailName() {
        return getphotoID() + "_thumbnail." + getExtension();
    }
}
