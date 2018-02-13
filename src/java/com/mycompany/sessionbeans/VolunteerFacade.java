/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.Volunteer;
import com.mycompany.entityclasses.VolunteeringHistory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andres
 */
@Stateless
public class VolunteerFacade extends AbstractFacade<Volunteer> {

    @PersistenceContext(unitName = "VolunteerCloud")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VolunteerFacade() {
        super(Volunteer.class);
    }
    
    /*
    -----------------------------------------------------
    The following methods are added to the generated code
    -----------------------------------------------------
     */
    /**
     * @param userID is the Primary Key of the Roommate entity in a table row in the PizzaHutDB database.
     * @return object reference of the Roommate entity whose primary key is id
     */
    public Volunteer getVolunteerFacade(int userID) {
        
        // The find method is inherited from the parent AbstractFacade class
        return em.find(Volunteer.class, userID);
    }

    /**
     * @param userID is the id attribute (column) value of the roommate
     * @return object reference of the roommate entity whose id is id
     */
    public Volunteer findByID(int userID) {
        if (em.createQuery("SELECT c FROM Volunteer c WHERE c.userID = :userID AND c.userRole = :userRole")
                .setParameter("userID", userID)
                .setParameter("userRole", 0)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Volunteer) (em.createQuery("SELECT c FROM Volunteer c WHERE c.userID = :userID AND c.userRole = :userRole")
                    .setParameter("userID", userID)
                    .setParameter("userRole", 0)
                    .getSingleResult());
        }
    }
    
    /**
     * @param email is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public Volunteer findByEmail(String email) {
        if (em.createQuery("SELECT c FROM Volunteer c WHERE c.email = :email AND c.userRole = :userRole")
                .setParameter("email", email)
                .setParameter("userRole", 0)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Volunteer) (em.createQuery("SELECT c FROM Volunteer c WHERE c.email = :email AND c.userRole = :userRole")
                    .setParameter("email", email)
                    .setParameter("userRole", 0)
                    .getSingleResult());
        }
    }
    
    /**
     * @param username is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public Volunteer findByUsername(String username) {
        if (em.createQuery("SELECT c FROM Volunteer c WHERE c.username = :username AND c.userRole = :userRole")
                .setParameter("username", username)
                .setParameter("userRole", 0)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Volunteer) (em.createQuery("SELECT c FROM Volunteer c WHERE c.username = :username AND c.userRole = :userRole")
                    .setParameter("username", username)
                    .setParameter("userRole", 0)
                    .getSingleResult());
        }
    }

    /**
     * Deletes the Roommate entity whose primary key is roommateID
     * @param userID is the Primary Key of the Roommate entity in a table row in the PizzaHutDB database.
     */
    public void deleteVolunteer(int userID) {
        
        // The find method is inherited from the parent AbstractFacade class
        Volunteer volunteer = em.find(Volunteer.class, userID);
        
        // The remove method is inherited from the parent AbstractFacade class
        em.remove(volunteer); 
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param userID is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public char deactivateVolunteer(int userID) {
        
        Volunteer volunteer = em.find(Volunteer.class, userID);
        volunteer.setActive('N');
        edit(volunteer);
        return volunteer.getActive();
    }

    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param zipCode is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Volunteer> findVolunteersByLocation(String zipCode) {
        
        Character active = 'Y';
        List<Volunteer> volunteers = new ArrayList<Volunteer>(); 
        volunteers = em.createQuery("SELECT c FROM Volunteer c WHERE c.zipCode = :zipCode AND c.userRole = :userRole")
                    .setParameter("zipCode", zipCode)
                    .setParameter("userRole", 0)
                    .getResultList();
        
        return volunteers;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param firstName is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Volunteer> findVolunteersByFirstName(String firstName) {
        
        List<Volunteer> volunteers = new ArrayList<Volunteer>(); 
        volunteers = em.createQuery("SELECT c FROM Volunteer c WHERE c.firstName LIKE :firstName AND c.userRole = :userRole")
                    .setParameter("firstName", firstName)
                    .setParameter("userRole", 0)
                    .getResultList();
        
        return volunteers;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param lastName is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Volunteer> findVolunteersByLastName(String lastName) {
        
        List<Volunteer> volunteers = new ArrayList<Volunteer>(); 
        volunteers = em.createQuery("SELECT c FROM Volunteer c WHERE c.lastName LIKE :lastName AND c.userRole = :userRole")
                    .setParameter("lastName", lastName)
                    .setParameter("userRole", 0)
                    .getResultList();
        
        return volunteers;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Volunteer> findActiveVolunteers() {
        
        List<Volunteer> volunteers = new ArrayList<Volunteer>(); 
        volunteers = em.createQuery("SELECT c FROM Volunteer c WHERE c.active = :active AND c.userRole = :userRole")
                    .setParameter("active", 'Y')
                    .setParameter("userRole", 0)
                    .getResultList();
        
        return volunteers;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Volunteer> findInactiveVolunteers() {
        
        List<Volunteer> volunteers = new ArrayList<Volunteer>(); 
        volunteers = em.createQuery("SELECT c FROM Volunteer c WHERE c.active = :active AND c.userRole = :userRole")
                    .setParameter("active", 'N')
                    .setParameter("userRole", 0)
                    .getResultList();
        
        return volunteers;
    }
    
    public List<Volunteer> SearchVolunteersOld(String zipcode, String firstName, String lastName, Character active) {
                
        List<Volunteer> volunteers = new ArrayList<Volunteer>(); 
        StringBuilder query = new StringBuilder().append("SELECT c FROM Volunteer c WHERE c.userRole = :userRole");
        
        if(firstName != null)
            query.append(" AND c.firstName = :firstName");

        if(lastName != null)
            query.append(" AND c.lastName = :lastName");
        
        if(zipcode != null)
            query.append(" AND c.zipCode = :zipCode");

        if(active != null)
            query.append(" AND c.active = :active");

        volunteers = em.createQuery(query.toString())
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .setParameter("zipcode", zipcode)    
                    .setParameter("active", active)
                    .setParameter("userRole", 0)
                    .getResultList();
        
        return volunteers;
    }
    
    public List<Volunteer> SearchVolunteers(List<Integer> userIDs) {
                
        List<Volunteer> volunteers = new ArrayList<Volunteer>(); 
        volunteers = em.createQuery("SELECT c FROM Volunteer c WHERE c.userID IN :userID AND c.active = :active")
                    .setParameter("userID", userIDs)
                    .setParameter("active", 'Y')
                    .getResultList();
    
        return volunteers;
    }    

    // MySQL Query Below:
    // SELECT * FROM USERS WHERE FIRST_NAME LIKE '%ANDRES%' AND LAST_NAME LIKE '%PI%' AND ZIP_CODE IN (24060, 24061, 34638); 
    public List<Volunteer> SearchVolunteers(List<String> zipCodesList, String firstName, String lastName) {
                
        List<Volunteer> volunteers = new ArrayList<Volunteer>(); 
        if (!zipCodesList.isEmpty())
            volunteers = em.createQuery("SELECT c FROM Volunteer c WHERE c.firstName LIKE :firstName AND c.lastName LIKE :lastName "
                    + "AND c.zipCode IN :zipCode AND c.active = :active")
                        .setParameter("firstName", "%" + firstName + "%")
                        .setParameter("lastName", "%" + lastName + "%")
                        .setParameter("zipCode", zipCodesList)
                        .setParameter("active", 'Y')
                        .getResultList();
    
        return volunteers;
    }
    
    public List<Volunteer> SearchVolunteers(List<Integer> userIDsList, List<String> zipCodesList, String firstName, String lastName) {
                
        List<Volunteer> volunteers = new ArrayList<Volunteer>();
        if (!userIDsList.isEmpty() && !zipCodesList.isEmpty())
            volunteers = em.createQuery("SELECT c FROM Volunteer c WHERE c.firstName LIKE :firstName AND c.lastName LIKE :lastName "
                    + "AND c.userID IN :userID AND c.zipCode IN :zipCode AND c.active = :active")
                        .setParameter("firstName", "%" + firstName + "%")
                        .setParameter("lastName", "%" + lastName + "%")
                        .setParameter("userID", userIDsList)
                        .setParameter("zipCode", zipCodesList)
                        .setParameter("active", 'Y')
                        .getResultList();
    
        return volunteers;
    }
    
}
