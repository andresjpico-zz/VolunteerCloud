/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andres
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "VolunteerCloud")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
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
    public Users getUsersFacade(int userID) {
        
        // The find method is inherited from the parent AbstractFacade class
        return em.find(Users.class, userID);
    }

    /**
     * @param userID is the id attribute (column) value of the roommate
     * @return object reference of the roommate entity whose id is id
     */
    public Users findByID(int userID) {
        if (em.createQuery("SELECT c FROM Users c WHERE c.userID = :userID")
                .setParameter("userID", userID)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Users) (em.createQuery("SELECT c FROM Users c WHERE c.userID = :userID")
                    .setParameter("userID", userID)
                    .getSingleResult());
        }
    }
    
    /**
     * @param email is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public Users findByEmail(String email) {
        if (em.createQuery("SELECT c FROM Users c WHERE c.email = :email")
                .setParameter("email", email)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Users) (em.createQuery("SELECT c FROM Users c WHERE c.email = :email")
                    .setParameter("email", email)
                    .getSingleResult());
        }
    }
    
    /**
     * @param username is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public Users findByUsername(String username) {
        if (em.createQuery("SELECT c FROM Users c WHERE c.username = :username")
                .setParameter("username", username)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Users) (em.createQuery("SELECT c FROM Users c WHERE c.username = :username")
                    .setParameter("username", username)
                    .getSingleResult());
        }
    }

    /**
     * Deletes the Roommate entity whose primary key is roommateID
     * @param userID is the Primary Key of the Roommate entity in a table row in the PizzaHutDB database.
     */
    public void deleteUser(int userID) {
        
        // The find method is inherited from the parent AbstractFacade class
        Users user = em.find(Users.class, userID);
        
        // The remove method is inherited from the parent AbstractFacade class
        em.remove(user); 
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param userID is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public char deactivateUser(int userID) {
        
        Users user = em.find(Users.class, userID);
        user.setActive('N');
        edit(user);
        return user.getActive();
    }

    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param zipCode is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Users> findUsersByLocation(String zipCode) {
        
        Character active = 'Y';
        List<Users> users = new ArrayList<Users>(); 
        users = em.createQuery("SELECT c FROM Users c WHERE c.zipCode = :zipCode AND c.active = :active")
                    .setParameter("zipCode", zipCode)
                    .setParameter("active", active)
                    .getResultList();
        
        return users;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param firstName is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Users> findUsersByFirstName(String firstName) {
        
        List<Users> users = new ArrayList<Users>(); 
        users = em.createQuery("SELECT c FROM Users c WHERE c.firstName LIKE :firstName AND c.active = 'Y'")
                    .setParameter("firstName", firstName)
                    .getResultList();
        
        return users;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param lastName is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Users> findUsersByLastName(String lastName) {
        
        List<Users> users = new ArrayList<Users>(); 
        users = em.createQuery("SELECT c FROM Users c WHERE c.lastName LIKE :lastName AND c.active = 'Y'")
                    .setParameter("lastName", lastName)
                    .getResultList();
        
        return users;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param volunteerID is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Users> findVolunteersByVolunteeringArea(Integer apartmentID) {
        //Use Join
        List<Users> users = new ArrayList<Users>(); 
//        volunteers = em.createQuery("SELECT c FROM Roommate c WHERE c.apartmentID = :apartmentID")
//                    .setParameter("apartmentID", apartmentID)
//                    .getResultList();
        
        return users;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Users> findActiveUsers() {
        
        List<Users> users = new ArrayList<Users>(); 
        users = em.createQuery("SELECT c FROM Users c WHERE c.active = 'Y'")
                    .getResultList();
        
        return users;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Users> findInactiveUsers() {
        
        List<Users> users = new ArrayList<Users>(); 
        users = em.createQuery("SELECT c FROM Users c WHERE c.active = 'N'")
                    .getResultList();
        
        return users;
    }
    
}