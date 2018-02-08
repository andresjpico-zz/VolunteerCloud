package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.Organization;
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
public class OrganizationFacade extends AbstractFacade<Organization> {

    @PersistenceContext(unitName = "VolunteerCloud")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrganizationFacade() {
        super(Organization.class);
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
    public Organization getOrganizationFacade(int userID) {
        
        // The find method is inherited from the parent AbstractFacade class
        return em.find(Organization.class, userID);
    }

    /**
     * @param userID is the id attribute (column) value of the roommate
     * @return object reference of the roommate entity whose id is id
     */
    public Organization findByID(int userID) {
        if (em.createQuery("SELECT c FROM Organization c WHERE c.userID = :userID AND c.userRole = :userRole")
                .setParameter("userID", userID)
                .setParameter("userRole", 1)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Organization) (em.createQuery("SELECT c FROM Organization c WHERE c.userID = :userID AND c.userRole = :userRole")
                    .setParameter("userID", userID)
                    .setParameter("userRole", 1)
                    .getSingleResult());
        }
    }
    
    /**
     * @param email is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public Organization findByEmail(String email) {
        if (em.createQuery("SELECT c FROM Organization c WHERE c.email = :email AND c.userRole = :userRole")
                .setParameter("email", email)
                .setParameter("userRole", 1)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Organization) (em.createQuery("SELECT c FROM Organization c WHERE c.email = :email AND c.userRole = :userRole")
                    .setParameter("email", email)
                    .setParameter("userRole", 1)
                    .getSingleResult());
        }
    }
    
    /**
     * @param username is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public Organization findByUsername(String username) {
        if (em.createQuery("SELECT c FROM Organization c WHERE c.username = :username AND c.userRole = :userRole")
                .setParameter("username", username)
                .setParameter("userRole", 1)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Organization) (em.createQuery("SELECT c FROM Organization c WHERE c.username = :username AND c.userRole = :userRole")
                    .setParameter("username", username)
                    .setParameter("userRole", 1)
                    .getSingleResult());
        }
    }

    /**
     * Deletes the Roommate entity whose primary key is roommateID
     * @param userID is the Primary Key of the Roommate entity in a table row in the PizzaHutDB database.
     */
    public void deleteOrganization(int userID) {
        
        // The find method is inherited from the parent AbstractFacade class
        Organization organization = em.find(Organization.class, userID);
        
        // The remove method is inherited from the parent AbstractFacade class
        em.remove(organization); 
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param userID is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public char deactivateOrganization(int userID) {
        
        Organization organization = em.find(Organization.class, userID);
        organization.setActive('N');
        edit(organization);
        return organization.getActive();
    }

    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param zipCode is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Organization> findVolunteersByLocation(String zipCode) {
        
        Character active = 'Y';
        List<Organization> organizations = new ArrayList<Organization>(); 
        organizations = em.createQuery("SELECT c FROM Organization c WHERE c.zipCode = :zipCode AND c.userRole = :userRole")
                    .setParameter("zipCode", zipCode)
                    .setParameter("userRole", 1)
                    .getResultList();
        
        return organizations;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param organizationName is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Organization> findOrganizationsByName(String organizationName) {
        
        List<Organization> organizations = new ArrayList<Organization>(); 
        organizations = em.createQuery("SELECT c FROM Organization c WHERE c.organizationName LIKE :organizationName AND c.userRole = :userRole")
                    .setParameter("organizationName", organizationName)
                    .setParameter("userRole", 1)
                    .getResultList();
        
        return organizations;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param organizationName is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Organization> findOrganizationsByKeyword(String keyword) {
        
        List<Organization> organizations = new ArrayList<Organization>(); 
        organizations = em.createQuery("SELECT c FROM Organization c WHERE c.organizationName LIKE :organizationName OR c.mission LIKE :mission")
                    .setParameter("organizationName", "%" + keyword + "%")
                    .setParameter("mission", "%" + keyword + "%")
                    .getResultList();
        
        return organizations;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param organizationID is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Organization> findOrganizationsByVolunteeringArea(Integer apartmentID) {
        //Use Join
        List<Organization> organizations = new ArrayList<Organization>(); 
//        volunteers = em.createQuery("SELECT c FROM Roommate c WHERE c.apartmentID = :apartmentID")
//                    .setParameter("apartmentID", apartmentID)
//                    .getResultList();
        
        return organizations;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Organization> findActiveOrganizations() {
        
        List<Organization> organizations = new ArrayList<Organization>(); 
        organizations = em.createQuery("SELECT c FROM Organization c WHERE c.active = :active")
                    .setParameter("active", 'Y')
                    .getResultList();
        
        return organizations;
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public List<Organization> findInactiveVolunteers() {
        
        List<Organization> organizations = new ArrayList<Organization>(); 
        organizations = em.createQuery("SELECT c FROM Organization c WHERE c.active = :active")
                    .setParameter("active", 'N')
                    .getResultList();
        
        return organizations;
    }
    
    public String getOrganizationName(int userID) {
        return (String) (em.createQuery("SELECT c.organizationName FROM Organization c WHERE c.userID = :userID")
                    .setParameter("userID", userID)
                    .getSingleResult());
    }
    
//    public List<String> getListOrganizationNames(List<Integer> userIDs) {
//        return (String) (em.createQuery("SELECT c.organizationName FROM Organization c WHERE c.userID = :userID")
//                    .setParameter("userID", userID)
//                    .getSingleResult());
//    }
    
    public List<Organization> SearchOrganizationsOld(String zipcode, String organizationName, Character active) {
                
        List<Organization> organizations = new ArrayList<Organization>(); 
        StringBuilder query = new StringBuilder().append("SELECT c FROM Organization c WHERE c.userRole = :userRole");
        
        if(organizationName != null)
            query.append(" AND c.organizationName = :organizationName");
        
        if(zipcode != null)
            query.append(" AND c.zipCode = :zipCode");
        
        if(active != null)
            query.append(" AND c.active = :active");

        organizations = em.createQuery(query.toString())
                    .setParameter("organizationName", organizationName)
                    .setParameter("zipcode", zipcode)    
                    .setParameter("active", active)
                    .setParameter("userRole", 1)
                    .getResultList();
        
        return organizations;
    }
    
    // MySQL Query Below:
    // SELECT * FROM USERS WHERE FIRST_NAME LIKE '%ANDRES%' AND LAST_NAME LIKE '%PI%' AND ZIP_CODE IN (24060, 24061, 34638); 
    public List<Organization> SearchOrganizations(List<String> zipCodesList, String organizationName, String keyword) {
                
        List<Organization> organizations = new ArrayList<Organization>(); 
        organizations = em.createQuery("SELECT c FROM Organization c WHERE c.organizationName LIKE :organizationName AND c.mission LIKE :keyword "
                + "AND c.zipCode IN :zipCode AND c.active = :active AND c.userRole = :userRole")
                    .setParameter("organizationName", "%" + organizationName + "%")
                    .setParameter("keyword", "%" + keyword + "%")
                    .setParameter("zipCode", zipCodesList)
                    .setParameter("active", 'Y')
                    .setParameter("userRole", 1)
                    .getResultList();
    
        return organizations;
    }
    
}
