package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.Photo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andres
 */
@Stateless
public class PhotoFacade extends AbstractFacade<Photo> {

    @PersistenceContext(unitName = "VolunteerCloud")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PhotoFacade() {
        super(Photo.class);
    }
    
    public Photo findPhotoByUserID(int userID) {

        if (em.createQuery("SELECT p FROM Photo p WHERE p.userID = :userID")
                .setParameter("userID", userID)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Photo) (em.createQuery("SELECT p FROM Photo p WHERE p.userID = :userID")
                .setParameter("userID", userID)
                .getSingleResult());
        }
    }
    
    public Photo findPhotoByOpportunityID(Integer opportunityID) {

        if (em.createQuery("SELECT p FROM Photo p WHERE p.opportunityID = :opportunityID")
                .setParameter("opportunityID", opportunityID)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Photo) (em.createQuery("SELECT p FROM Photo p WHERE p.opportunityID = :opportunityID")
                .setParameter("opportunityID", opportunityID)
                .getSingleResult());
        }
    }
}
