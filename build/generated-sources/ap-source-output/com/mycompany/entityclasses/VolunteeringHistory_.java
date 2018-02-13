package com.mycompany.entityclasses;

import com.mycompany.entityclasses.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-13T13:52:03")
@StaticMetamodel(VolunteeringHistory.class)
public class VolunteeringHistory_ { 

    public static volatile SingularAttribute<VolunteeringHistory, Integer> recordID;
    public static volatile SingularAttribute<VolunteeringHistory, Integer> opportunityID;
    public static volatile SingularAttribute<VolunteeringHistory, String> participated;
    public static volatile SingularAttribute<VolunteeringHistory, Users> participant;

}