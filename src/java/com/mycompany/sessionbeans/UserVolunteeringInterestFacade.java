/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.UserVolunteeringInterest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author andres
 */
@Stateless
public class UserVolunteeringInterestFacade extends AbstractFacade<UserVolunteeringInterest> {

    @PersistenceContext(unitName = "VolunteerCloud")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserVolunteeringInterestFacade() {
        super(UserVolunteeringInterest.class);
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
    public UserVolunteeringInterest getUserVolunteeringInterestFacade(int recordID) {
        
        // The find method is inherited from the parent AbstractFacade class
        return em.find(UserVolunteeringInterest.class, recordID);
    }

    /**
     * @param uniqueID is the id attribute (column) value of the roommate
     * @return object reference of the roommate entity whose id is id
     */
    public UserVolunteeringInterest findByUniqueID(int uniqueID) {
        if (em.createQuery("SELECT c FROM UserVolunteeringInterest c WHERE c.ID = :uniqueID")
                .setParameter("uniqueID", uniqueID)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (UserVolunteeringInterest) (em.createQuery("SELECT c FROM UserVolunteeringInterest c WHERE c.ID = :uniqueID")
                    .setParameter("uniqueID", uniqueID)
                    .getSingleResult());
        }
    }
    
    /**
     * @param userID is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public List<UserVolunteeringInterest> findByUserID(int userID) {

        List<UserVolunteeringInterest> userVolunteeringInterests = new ArrayList<UserVolunteeringInterest>(); 
        userVolunteeringInterests = em.createQuery("SELECT c FROM UserVolunteeringInterest c WHERE c.userID = :userID")
                    .setParameter("userID", userID)
                    .getResultList();
        
        return userVolunteeringInterests;
    }
    
    /**
     * @param ownerID is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public List<UserVolunteeringInterest> findByVolunteeringAreaID(int volunteeringAreaID) {

        List<UserVolunteeringInterest> userVolunteeringInterests = new ArrayList<UserVolunteeringInterest>(); 
        userVolunteeringInterests = em.createQuery("SELECT c FROM UserVolunteeringInterest c WHERE c.volunteeringAreaID = :volunteeringAreaID")
                    .setParameter("volunteeringAreaID", volunteeringAreaID)
                    .getResultList();
        
        return userVolunteeringInterests;
    }
    
    /**
     * Deletes the Roommate entity whose primary key is roommateID
     * @param uniqueID is the Primary Key of the Roommate entity in a table row in the PizzaHutDB database.
     */
    public void deleteRecord(int uniqueID) {
        
        // The find method is inherited from the parent AbstractFacade class
        UserVolunteeringInterest userVolunteeringInterest = em.find(UserVolunteeringInterest.class, uniqueID);
        
        // The remove method is inherited from the parent AbstractFacade class
        em.remove(userVolunteeringInterest); 
    }
    
    public void deleteUserInterests(int userID) {

        em.createQuery("DELETE FROM UserVolunteeringInterest WHERE userID = :userID")
                .setParameter("userID", userID)
                .executeUpdate();
        em.flush();
    }
    
    public void deleteUserInterest(int userID, int volunteeringAreaID) {
        
        UserVolunteeringInterest userVolunteeringInterest = SearchVolunteeringInterestRecord(volunteeringAreaID, userID);
        em.remove(userVolunteeringInterest); 
    }
    
    /**
     * @param opportunityID is the id attribute (column) value of the roommate
     * @return object reference of the roommate entity whose id is id
     */
    public boolean isUserInterestedInArea(int userID, int volunteeringAreaID) {
        if (em.createQuery("SELECT c FROM UserVolunteeringInterest c WHERE c.userID = :userID AND c.volunteeringAreaID = :volunteeringAreaID")
                .setParameter("userID", userID)
                .setParameter("volunteeringAreaID", volunteeringAreaID)
                .getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public List<Integer> getListUserIDsInterestedInArea(int volunteeringAreaID) {
                
        List<Integer> userIDs = new ArrayList<Integer>(); 
        userIDs = em.createQuery("SELECT c.userID FROM UserVolunteeringInterest c WHERE c.volunteeringAreaID = :volunteeringAreaID")
                    .setParameter("volunteeringAreaID", volunteeringAreaID)
                    .getResultList();
    
        return userIDs;
    }

    public List<Integer> getListInterestAreaIDsFromUser(int userID) {
                
        List<Integer> volunteeringAreaIDs = new ArrayList<Integer>(); 
        volunteeringAreaIDs = em.createQuery("SELECT c.volunteeringAreaID FROM UserVolunteeringInterest c WHERE c.userID = :userID")
                    .setParameter("userID", userID)
                    .getResultList();
    
        return volunteeringAreaIDs;
    }
    
    public List<String> getStringListInterestAreaIDsFromUser(int userID) {
                
        List<String> volunteeringAreaIDs = new ArrayList<String>(); 
        volunteeringAreaIDs = em.createQuery("SELECT c.volunteeringAreaID FROM UserVolunteeringInterest c WHERE c.userID = :userID")
                    .setParameter("userID", userID)
                    .getResultList();
    
        return volunteeringAreaIDs;
    }
    
    public UserVolunteeringInterest SearchVolunteeringInterestRecord(int volunteeringAreaID, int userID) {
        if (em.createQuery("SELECT c FROM UserVolunteeringInterest c WHERE c.volunteeringAreaID = :volunteeringAreaID AND c.userID = :userID")
                .setParameter("volunteeringAreaID", volunteeringAreaID)
                .setParameter("userID", userID)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (UserVolunteeringInterest) (em.createQuery("SELECT c FROM UserVolunteeringInterest c WHERE c.volunteeringAreaID = :volunteeringAreaID AND c.userID = :userID")
                    .setParameter("volunteeringAreaID", volunteeringAreaID)
                    .setParameter("userID", userID)
                    .getSingleResult());
        }
    }
    
    public void updateVolunteeringInterestAreas(int userID, List<String> userVolunteeringAreas) {
    
        deleteUserInterests(userID);
        for (int i = 0; i < userVolunteeringAreas.size(); i++) {
            UserVolunteeringInterest userVolunteeringInterest = new UserVolunteeringInterest(Integer.parseInt(userVolunteeringAreas.get(i)), userID);
            create(userVolunteeringInterest);
        }
        
        // Not recognizing INSERT statement so solution below can't be applied :(!
        // Ideal solution could involve hibernate batch processing but this solution's required time and efforts exceed the reward by far!
        // In conclusion, the solution above is fair enough for the business needs given the rare instance of its use.

//        StringBuilder queryBuilder = new StringBuilder().append("INSERT INTO UserVolunteeringInterest (c.volunteeringAreaID, userID) VALUES (:volunteeringArea_0,:userID)");
//        for(int i = 1; i < userVolunteeringAreas.size(); i++) {
//            queryBuilder.append(",(:volunteeringArea_").append(i).append(",:userID)");
//        }
//        
//        Query query = em.createQuery(queryBuilder.toString());
//        for(int i = 0; i < userVolunteeringAreas.size(); i++)
//            query.setParameter("volunteeringArea_" + i, userID);
//
//        query.executeUpdate();
//        em.flush();

    }
    
}
