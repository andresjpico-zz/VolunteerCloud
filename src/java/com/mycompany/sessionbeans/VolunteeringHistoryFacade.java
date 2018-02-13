/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.Users;
import com.mycompany.entityclasses.VolunteeringHistory;
import com.mycompany.entityclasses.VolunteeringOpportunities;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andres
 */
@Stateless
public class VolunteeringHistoryFacade extends AbstractFacade<VolunteeringHistory> {

    @PersistenceContext(unitName = "VolunteerCloud")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VolunteeringHistoryFacade() {
        super(VolunteeringHistory.class);
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
    public VolunteeringHistory getVolunteeringHistoryFacade(int recordID) {
        
        // The find method is inherited from the parent AbstractFacade class
        return em.find(VolunteeringHistory.class, recordID);
    }

    /**
     * @param opportunityID is the id attribute (column) value of the roommate
     * @return object reference of the roommate entity whose id is id
     */
    public VolunteeringHistory findByRecordID(int recordID) {
        if (em.createQuery("SELECT c FROM VolunteeringHistory c WHERE c.recordID = :recordID")
                .setParameter("recordID", recordID)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (VolunteeringHistory) (em.createQuery("SELECT c FROM VolunteeringHistory c WHERE c.recordID = :recordID")
                    .setParameter("recordID", recordID)
                    .getSingleResult());
        }
    }
    
    /**
     * @param ownerID is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public List<VolunteeringHistory> findByUserID(int userID) {

        List<VolunteeringHistory> historyRecords = new ArrayList<VolunteeringHistory>(); 
        historyRecords = em.createQuery("SELECT c FROM VolunteeringHistory c WHERE c.participant.userID = :userID")
                    .setParameter("userID", userID)
                    .getResultList();
        
        return historyRecords;
    }
    
    /**
     * @param ownerID is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public List<VolunteeringHistory> findByOpportunityID(int opportunityID) {

        List<VolunteeringHistory> historyRecords = new ArrayList<VolunteeringHistory>(); 
        historyRecords = em.createQuery("SELECT c FROM VolunteeringHistory c WHERE c.opportunityID = :opportunityID")
                    .setParameter("opportunityID", opportunityID)
                    .getResultList();
        
        return historyRecords;
    }
    
    /**
     * @param ownerID is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public List<VolunteeringHistory> findByParticipation(char participation) {

        List<VolunteeringHistory> historyRecords = new ArrayList<VolunteeringHistory>(); 
        historyRecords = em.createQuery("SELECT c FROM VolunteeringHistory c WHERE c.participated = :participated")
                    .setParameter("participated", participation)
                    .getResultList();
        
        return historyRecords;
    }
    
    /**
     * Deletes the Roommate entity whose primary key is roommateID
     * @param opportunityID is the Primary Key of the Roommate entity in a table row in the PizzaHutDB database.
     */
    public void deleteRecord(int recordID) {
        
        // The find method is inherited from the parent AbstractFacade class
        VolunteeringHistory record = em.find(VolunteeringHistory.class, recordID);
        
        // The remove method is inherited from the parent AbstractFacade class
        em.remove(record); 
    }
    
    public void deleteRecord(int userID, int opportunityID) {
        
        VolunteeringHistory record = SearchHistoryRecord(opportunityID, userID);
        em.remove(record); 
    }
    
//    public void deleteRecord(Users user, int opportunityID) {
//        
//        VolunteeringHistory record = SearchHistoryRecord(opportunityID, user);
//        em.remove(record); 
//    }
    
    /**
     * @param opportunityID is the id attribute (column) value of the roommate
     * @return object reference of the roommate entity whose id is id
     */
    public boolean isVolunteerSubscribedToOpportunity(int userID, int opportunityID) {
        if (em.createQuery("SELECT c FROM VolunteeringHistory c WHERE c.participant.userID = :userID AND c.opportunityID = :opportunityID")
                .setParameter("userID", userID)
                .setParameter("opportunityID", opportunityID)
                .getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * @param opportunityID is the id attribute (column) value of the roommate
     * @return object reference of the roommate entity whose id is id
     */
    public boolean isVolunteerParticipationConfirmed(int userID, int opportunityID) {
        if (em.createQuery("SELECT c.participated FROM VolunteeringHistory c WHERE c.participant.userID = :userID AND c.opportunityID = :opportunityID")
                .setParameter("userID", userID)
                .setParameter("opportunityID", opportunityID)
                .getSingleResult() == null) {
            return false;
        } else {
            return true;
        }
    }
    
    public List<Integer> getOpportunityParticipantsIDs(int opportunityID) {
                
        List<Integer> participants = new ArrayList<Integer>(); 
        participants = em.createQuery("SELECT c.userID FROM VolunteeringHistory c WHERE c.opportunityID = :opportunityID")
                    .setParameter("opportunityID", opportunityID)
                    .getResultList();
    
        return participants;
    }
    
    public List<VolunteeringHistory> getOpportunityParticipantsRecords(int opportunityID) {
                
        List<VolunteeringHistory> participationRecords = new ArrayList<VolunteeringHistory>(); 
        participationRecords = em.createQuery("SELECT c FROM VolunteeringHistory c WHERE c.opportunityID = :opportunityID")
                    .setParameter("opportunityID", opportunityID)
                    .getResultList();
    
        return participationRecords;
    }
    
    public List<Integer> getOpportunityConfirmedParticipants(int opportunityID) {
                
        List<Integer> participants = new ArrayList<Integer>(); 
        participants = em.createQuery("SELECT c.userID FROM VolunteeringHistory c WHERE c.opportunityID IN :opportunityID AND c.participated = :participated")
                    .setParameter("opportunityID", opportunityID)
                    .setParameter("participated", "Y")
                    .getResultList();
    
        return participants;
    }
    
    public List<Integer> getOpportunityUnconfirmedParticipants(int opportunityID) {
                
        List<Integer> participants = new ArrayList<Integer>(); 
        participants = em.createQuery("SELECT c.userID FROM VolunteeringHistory c WHERE c.opportunityID IN :opportunityID AND c.participated = :participated")
                    .setParameter("opportunityID", opportunityID)
                    .setParameter("participated", "N")
                    .getResultList();
    
        return participants;
    }
    
    public VolunteeringHistory SearchHistoryRecord(int opportunityID, int userID) {
        if (em.createQuery("SELECT c FROM VolunteeringHistory c WHERE c.opportunityID = :opportunityID AND c.participant.userID = :userID")
                .setParameter("opportunityID", opportunityID)
                .setParameter("userID", userID)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (VolunteeringHistory) (em.createQuery("SELECT c FROM VolunteeringHistory c WHERE c.opportunityID = :opportunityID AND c.participant.userID = :userID")
                    .setParameter("opportunityID", opportunityID)
                    .setParameter("userID", userID)
                    .getSingleResult());
        }
    }
  
}
