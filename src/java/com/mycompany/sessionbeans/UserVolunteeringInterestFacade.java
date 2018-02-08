/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.UserVolunteeringInterest;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
