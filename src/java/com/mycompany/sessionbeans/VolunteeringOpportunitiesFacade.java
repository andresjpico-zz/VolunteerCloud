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
    public List<VolunteeringOpportunities> findByVolunteeringArea(int volunteeringAreaID) {

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
    public List<VolunteeringOpportunities> SearchOpportunities(List<String> zipCodesList, String title, String keyword) {
            
        List<VolunteeringOpportunities> opportunities = new ArrayList<VolunteeringOpportunities>(); 
        opportunities = em.createQuery("SELECT c FROM VolunteeringOpportunities c WHERE c.title LIKE :title AND c.description LIKE :description "
                + "AND c.zipCode IN :zipCode AND c.active = :active ORDER BY c.dateOccurrence DESC") 
                    .setParameter("title", "%" + title + "%")
                    .setParameter("description", "%" + keyword + "%")
                    .setParameter("zipCode", zipCodesList)
                    .setParameter("active", 'Y')
                    .getResultList();

        return opportunities;
    }
    
    public List<VolunteeringOpportunities> SearchOpportunitiesWithinDateRange(List<String> zipCodesList, String title, String keyword, Date dateStart, Date dateEnd) {
            
        List<VolunteeringOpportunities> opportunities = new ArrayList<VolunteeringOpportunities>(); 
        opportunities = em.createQuery("SELECT c FROM VolunteeringOpportunities c WHERE c.title LIKE :title AND c.description LIKE :description "
                + "AND c.dateOccurrence >= :dateStart AND c.dateOccurrence <= :dateEnd AND c.zipCode IN :zipCode AND c.active = :active")
                    .setParameter("title", "%" + title + "%")
                    .setParameter("description", "%" + keyword + "%")
                    .setParameter("dateStart", dateStart)
                    .setParameter("dateEnd", dateEnd)
                    .setParameter("zipCode", zipCodesList)
                    .setParameter("active", 'Y')
                    .getResultList();

        return opportunities;
    }

}
