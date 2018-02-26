package com.mycompany.entityclasses;

import com.mycompany.entityclasses.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-25T19:58:23")
@StaticMetamodel(VolunteeringOpportunities.class)
public class VolunteeringOpportunities_ { 

    public static volatile SingularAttribute<VolunteeringOpportunities, String> zipCode;
    public static volatile SingularAttribute<VolunteeringOpportunities, Date> dateOccurrence;
    public static volatile SingularAttribute<VolunteeringOpportunities, Integer> opportunityID;
    public static volatile SingularAttribute<VolunteeringOpportunities, String> address;
    public static volatile SingularAttribute<VolunteeringOpportunities, String> city;
    public static volatile SingularAttribute<VolunteeringOpportunities, Integer> volunteerMatchID;
    public static volatile SingularAttribute<VolunteeringOpportunities, String> description;
    public static volatile SingularAttribute<VolunteeringOpportunities, Character> active;
    public static volatile SingularAttribute<VolunteeringOpportunities, Integer> state;
    public static volatile SingularAttribute<VolunteeringOpportunities, Users> ownerID;
    public static volatile SingularAttribute<VolunteeringOpportunities, String> title;
    public static volatile SingularAttribute<VolunteeringOpportunities, String> volunteeringAreaID;

}