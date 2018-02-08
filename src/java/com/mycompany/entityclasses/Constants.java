package com.mycompany.entityclasses;

/**
 *
 * @author andres
 */

// Class to store all constants used by the classes
public final class Constants {

    public static final String ROOT_DIRECTORY = "/Users/andres/NETBEANS_PROJECTS/VolunteerCloudStorageLocation/";

    public static final String STORAGE_DIRECTORY = "VolunteerCloudStorageLocation/";

    public static final String TEMP_FILE = "tmp_file";

    public static final Integer THUMBNAIL_SIZE = 200;

    public static final Integer MAX_CAPTION_SIZE = 140;

    public static final String[] SECURITY_QUESTIONS = {
        "In what city were you born?",
        "What elementary school did you attend?",
        "What is the last name of your favorite teacher?",
        "What is your father's middle name?",
        "What is your favorite pet's name?",
        "What is your favorite sports team?",
        "What is the make and model of your first car?",
        "Where is your favorite place to vacation?",
        "What is your favorite food?",
        "What city did you attend university?",
        "What is your mother’s middle name?",
        "What is the name of the street that you grew up on?",
        "What is the first name of your best friend?",
        "What was the name of your first pet?"
    };

    public static final String[] USER_ROLES = {
        "Volunteer",
        "Organization"
    };
    
    public static final String[] VOLUNTEERING_AREA = {
        "Animals",
        "Children",
        "Community",
        "Education",
        "Disabilities",
        "Disasters",
        "Health",
        "Homeless",
        "Hunger",
        "Seniors",
        "Veterans",
        "Misc."
    };
    
    public static final String[] STATES = {
        "Alabama",  
        "Alaska", 
        "Arizona", 
        "Arkansas", 
        "California", 
        "Colorado", 
        "Connecticut", 
        "Delaware", 
        "Dist. Columbia", 
        "Florida", 
        "Georgia", 
        "Hawaii", 
        "Idaho", 
        "Illinois", 
        "Indiana", 
        "Iowa", 
        "Kansas", 
        "Kentucky", 
        "Louisiana", 
        "Maine", 
        "Maryland", 
        "Massachusetts", 
        "Michigan", 
        "Minnesota", 
        "Mississippi", 
        "Missouri", 
        "Montana", 
        "Nebraska", 
        "Nevada", 
        "New Hampshire", 
        "New Jersey", 
        "New Mexico", 
        "New York", 
        "North Carolina", 
        "North Dakota", 
        "Ohio", 
        "Oklahoma", 
        "Oregon", 
        "Pennsylvania", 
        "Puerto Rico", 
        "Rhode Island", 
        "South Carolina", 
        "South Dakota", 
        "Tennessee", 
        "Texas", 
        "Utah", 
        "Vermont", 
        "Virginia", 
        "Washington", 
        "West Virginia", 
        "Wisconsin", 
        "Wyoming" 
    };


    public static final String[] STATES_ABBR = {
        "AL",
        "AK",
        "AZ",
        "AR",
        "CA",
        "CO",
        "CT",
        "DE",
        "DC",
        "FL",
        "GA",
        "HI",
        "ID",
        "IL",
        "IN",
        "IA",
        "KS",
        "KY",
        "LA",
        "ME",
        "MD",
        "MA",
        "MI",
        "MN",
        "MS",
        "MO",
        "MT",
        "NE",
        "NV",
        "NH",
        "NJ",
        "NM",
        "NY",
        "NC",
        "ND",
        "OH",
        "OK",
        "OR",
        "PA",
        "PR",
        "RI",
        "SC",
        "SD",
        "TN",
        "TX",
        "UT",
        "VT",
        "VA",
        "WA",
        "WV",
        "WI",
        "WY"
    };
    
        public static final String[] ZIPCODE_RADIUSES = {
        "5",
        "10",
        "25",
        "35",
        "50",
        "100"
    };
}