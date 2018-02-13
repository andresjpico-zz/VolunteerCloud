/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.VolunteeringOpportunities;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author andres
 */
@Stateless
public class VolunteeringOpportunitiesFacade extends AbstractFacade<VolunteeringOpportunities> {

    @PersistenceContext(unitName = "VolunteerCloud")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VolunteeringOpportunitiesFacade() {
        super(VolunteeringOpportunities.class);
    }
    
    /*
    -----------------------------------------------------
    The following methods are added to the generated code
    -----------------------------------------------------
     */
    /**
     * Stores the newly CREATED Apartment (entity) object into the database
     *
     * @param opportunity contains object reference of the Apartment
     */
    public void ownEdit(VolunteeringOpportunities opportunity) {

        em.createQuery("UPDATE VolunteeringOpportunities c SET c.title = :title, c.description = :description, "
                + "c.volunteeringAreaID = :volunteeringAreaID, c.address = :address, c.city = :city, "
                + "c.state = :state, c.zipCode = :zipCode WHERE c.opportunityID = :opportunityID")
                .setParameter("opportunityID", opportunity.getOpportunityID())
                .setParameter("title", opportunity.getTitle())
                .setParameter("description", opportunity.getDescription())
                .setParameter("volunteeringAreaID", opportunity.getVolunteeringAreaID())
                .setParameter("address", opportunity.getAddress())
                .setParameter("city", opportunity.getCity())
                .setParameter("state", opportunity.getState())
                .setParameter("zipCode", opportunity.getZipCode())
                .executeUpdate();
        em.flush();
    }
    
    /**
     * @param opportunity contains object reference of the Apartment
     */
    public void cancelOpportunity(VolunteeringOpportunities opportunity) {

        em.createQuery("UPDATE VolunteeringOpportunities c SET c.active = :active WHERE c.opportunityID = :opportunityID")
                .setParameter("opportunityID", opportunity.getOpportunityID())
                .setParameter("active", opportunity.getActive())
                .executeUpdate();
        em.flush();
    }
    
    /**
     * @param opportunity contains object reference of the Apartment
     */
    public void ownRemove(VolunteeringOpportunities opportunity) {
        em.createQuery("DELETE VolunteeringOpportunities c WHERE c.opportunityID = :opportunityID")
                .setParameter("opportunityID", opportunity.getOpportunityID())
                .executeUpdate();
        em.flush();
    }
    
    /**
     * @param opportunityID is the Primary Key of the Roommate entity in a table row in the PizzaHutDB database.
     * @return object reference of the Roommate entity whose primary key is id
     */
    public VolunteeringOpportunities getVolunteeringOpportunitiesFacade(int opportunityID) {
        
        // The find method is inherited from the parent AbstractFacade class
        return em.find(VolunteeringOpportunities.class, opportunityID);
    }

    /**
     * @param opportunityID is the id attribute (column) value of the roommate
     * @return object reference of the roommate entity whose id is id
     */
    public VolunteeringOpportunities findByOpportunityID(int opportunityID) {
        if (em.createQuery("SELECT c FROM VolunteeringOpportunities c WHERE c.opportunityID = :opportunityID")
                .setParameter("opportunityID", opportunityID)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (VolunteeringOpportunities) (em.createQuery("SELECT c FROM VolunteeringOpportunities c WHERE c.opportunityID = :opportunityID")
                    .setParameter("opportunityID", opportunityID)
                    .getSingleResult());
        }
    }
    
    /**
     * @param ownerID is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public List<VolunteeringOpportunities> findByOrganization(int ownerID) {

        List<VolunteeringOpportunities> opportunities = new ArrayList<VolunteeringOpportunities>(); 
        opportunities = em.createQuery("SELECT c FROM VolunteeringOpportunities c WHERE c.ownerID = :ownerID")
                    .setParameter("ownerID", ownerID)
                    .getResultList();
        
        return opportunities;
    }
    
    /**
     * @param volunteeringAreaID is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public List<VolunteeringOpportunities> findByVolunteeringArea(String volunteeringAreaID) {

        List<VolunteeringOpportunities> opportunities = new ArrayList<VolunteeringOpportunities>(); 
        opportunities = em.createQuery("SELECT c FROM VolunteeringOpportunities c WHERE c.volunteeringAreaID = :volunteeringAreaID")
                    .setParameter("volunteeringAreaID", volunteeringAreaID)
                    .getResultList();
        
        return opportunities;
    }
    
    /**
     * @param keyword is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public List<VolunteeringOpportunities> findByKeyword(String keyword) {

        List<VolunteeringOpportunities> opportunities = new ArrayList<VolunteeringOpportunities>(); 
        opportunities = em.createQuery("SELECT c FROM VolunteeringOpportunities c WHERE c.description LIKE :keyword")
                    .setParameter("keyword", "%" + keyword + "%")
                    .getResultList();
        
        return opportunities;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param zipCode is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<VolunteeringOpportunities> findVolunteeringOpportunitiessByLocation(String zipCode) {
        
        Character active = 'Y';
        List<VolunteeringOpportunities> opportunities = new ArrayList<VolunteeringOpportunities>(); 
        opportunities = em.createQuery("SELECT c FROM VolunteeringOpportunities c WHERE c.zipCode = :zipCode")
                    .setParameter("zipCode", zipCode)
                    .getResultList();
        
        return opportunities;
    }

    /**
     * Deletes the Roommate entity whose primary key is roommateID
     * @param opportunityID is the Primary Key of the Roommate entity in a table row in the PizzaHutDB database.
     */
    public void deleteVolunteeringOpportunities(int opportunityID) {
        
        // The find method is inherited from the parent AbstractFacade class
        VolunteeringOpportunities opportunity = em.find(VolunteeringOpportunities.class, opportunityID);
        
        // The remove method is inherited from the parent AbstractFacade class
        em.remove(opportunity); 
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param opportunityID is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public char deactivateVolunteeringOpportunities(int opportunityID) {
        
        VolunteeringOpportunities opportunity = em.find(VolunteeringOpportunities.class, opportunityID);
        opportunity.setActive('N');
        edit(opportunity);
        return opportunity.getActive();
    }
    
    // + "AND ((c.dateOccurrence = :dateOccurrence AND NOT(:dateOccurrence is NULL)) OR :dateOccurrence is NULL) AND c.zipCode IN :zipCode AND c.active = :active")
    public List<VolunteeringOpportunities> SearchOpportunities(List<String> zipCodesList, String title, String keyword, String organizationName, String volunteeringAreaID) {
            
        volunteeringAreaID = (volunteeringAreaID == null) ? "%%" : volunteeringAreaID;
        
        List<VolunteeringOpportunities> opportunities = new ArrayList<VolunteeringOpportunities>(); 
        if (!zipCodesList.isEmpty())
            opportunities = em.createQuery("SELECT c FROM VolunteeringOpportunities c WHERE c.title LIKE :title AND c.description LIKE :description AND c.ownerID.organizationName LIKE :organizationName "
                    + "AND c.volunteeringAreaID LIKE :volunteeringAreaID AND c.zipCode IN :zipCode AND c.active = :active ORDER BY c.dateOccurrence DESC") 
                        .setParameter("title", "%" + title + "%")
                        .setParameter("description", "%" + keyword + "%")
                        .setParameter("organizationName", "%" + organizationName + "%")
                        .setParameter("volunteeringAreaID", volunteeringAreaID)
                        .setParameter("zipCode", zipCodesList)
                        .setParameter("active", 'Y')
                        .getResultList();

        return opportunities;
    }
    
    public List<VolunteeringOpportunities> SearchOpportunitiesWithinDateRange(List<String> zipCodesList, String title, String keyword, String organizationName, String volunteeringAreaID, Date dateStart, Date dateEnd) {
            
        volunteeringAreaID = (volunteeringAreaID == null) ? "%%" : volunteeringAreaID;
        
        List<VolunteeringOpportunities> opportunities = new ArrayList<VolunteeringOpportunities>(); 
        if (!zipCodesList.isEmpty())
            opportunities = em.createQuery("SELECT c FROM VolunteeringOpportunities c WHERE c.title LIKE :title AND c.description LIKE :description AND c.ownerID.organizationName LIKE :organizationName "
                    + "AND c.volunteeringAreaID LIKE :volunteeringAreaID AND c.dateOccurrence >= :dateStart AND c.dateOccurrence <= :dateEnd AND c.zipCode IN :zipCode AND c.active = :active")
                        .setParameter("title", "%" + title + "%")
                        .setParameter("description", "%" + keyword + "%")
                        .setParameter("organizationName", "%" + organizationName + "%")
                        .setParameter("volunteeringAreaID", volunteeringAreaID)
                        .setParameter("dateStart", dateStart)
                        .setParameter("dateEnd", dateEnd)
                        .setParameter("zipCode", zipCodesList)
                        .setParameter("active", 'Y')
                        .getResultList();

        return opportunities;
    }

}
