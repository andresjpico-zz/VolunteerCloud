package com.mycompany.controllers;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.mycompany.entityclasses.VolunteeringOpportunities;
import com.mycompany.controllers.util.JsfUtil;
import com.mycompany.controllers.util.JsfUtil.PersistAction;
import com.mycompany.entityclasses.Constants;
import com.mycompany.entityclasses.Photo;
import com.mycompany.entityclasses.Users;
import com.mycompany.entityclasses.Volunteer;
import com.mycompany.entityclasses.VolunteeringHistory;
import com.mycompany.sessionbeans.VolunteeringOpportunitiesFacade;
import com.mycompany.sessionbeans.VolunteerFacade;
import com.mycompany.sessionbeans.OrganizationFacade;
import com.mycompany.sessionbeans.PhotoFacade;
import com.mycompany.sessionbeans.VolunteeringHistoryFacade;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ComponentSystemEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.primefaces.component.export.PDFExporter;

@Named("OpportunityController")
@SessionScoped
public class VolunteeringOpportunitiesController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private final String ExampleZipCodeAPI = "https://www.zipcodeapi.com/rest/s9oXbWODlgevBgpUCBlFQKi0ytTGunSc8sycGwSHvJyvc5Ruo8x7jcRIFOvZ7CiK/radius.<result_format>/<zip_code>/<radius>/<distance_unit>?minimal";
    private final String ZipCodeAPI1 = "https://www.zipcodeapi.com/rest/s9oXbWODlgevBgpUCBlFQKi0ytTGunSc8sycGwSHvJyvc5Ruo8x7jcRIFOvZ7CiK/radius.json/";
    private final String ZipCodeAPI2 = "/miles?minimal";
    
    private int ownerID;
    private String volunteeringAreaID;
    private String ownerName;
    private String title;
    private String description;
    private String address;
    private String city;
    private int stateID;
    private String state;
    private String zipCode;
    private Date dateOccurrence;
    private Integer volunteerMatchID;
    private Character active;
    private Date today = new Date();
    private SimpleDateFormat standardDateTimeFormat = new SimpleDateFormat("MM-dd-yyyy");

    private String searchTitleField = "";
    private String searchKeywordField = "";
    private Date searchDateOccurrenceField = null;
    private Date searchDateStartField = null;
    private Date searchDateEndField = null;
    private String searchOrganizationNameField = "";
    private String searchZipCodeField = "";
    private int searchZipCodeRadiusField;
    private String searchVolunteeringAreaField;
    private String searchActiveField;
    private String visible = "hidden";

    private Map<String, Object> userRoles;
    private Map<String, Object> zipCodeRadiuses;
    private Map<String, Object> statesNames;
    private Map<String, Object> statesAbbrv;
    private Map<String, Object> volunteeringAreas;

    private String statusMessage;

    private VolunteeringOpportunities selectedOpportunity;
    private VolunteeringOpportunities selectedHistoryOpportunity;
    private List<VolunteeringOpportunities> opportunities;
    private List<VolunteeringOpportunities> historyOpportunities;

    
    private boolean participation;
    private VolunteeringHistory selectedRecord;
    private List<Integer> listOpportunityIDsFromUserHistory;
    private List<VolunteeringHistory> records;
    
    private Volunteer selectedParticipant;
    private List<Volunteer> participants;
    private List<Integer> participantsIDs;
    
    /*
    The @EJB annotation implies that the EJB container will perform an injection of the object
    reference of the <file_name>Facade object into <file_name>Facade when it is created at runtime.
     */
    @EJB
    private VolunteeringOpportunitiesFacade opportunityFacade;
    
    @EJB
    private VolunteerFacade volunteerFacade;
    
    @EJB
    private OrganizationFacade organizationFacade;
        
    @EJB
    private PhotoFacade photoFacade;
    
    @EJB
    private VolunteeringHistoryFacade volunteeringHistoryFacade;
    
    // Constructor method instantiating an instance of UserController
    public VolunteeringOpportunitiesController() {
    }
    
    
    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getVolunteeringAreaID() {
        return volunteeringAreaID;
    }

    public void setVolunteeringAreaID(String volunteeringAreaID) {
        this.volunteeringAreaID = volunteeringAreaID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Date getDateOccurrence() {
        return dateOccurrence;
    }

    public void setDateOccurrence(Date dateOccurrence) {
        this.dateOccurrence = dateOccurrence;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }
    
    public Integer getVolunteerMatchID() {
        return volunteerMatchID;
    }

    public void setVolunteerMatchID(Integer volunteerMatchID) {
        this.volunteerMatchID = volunteerMatchID;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
    }
    
    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }
    
    public String getSearchTitleField() {
        return searchTitleField;
    }

    public void setSearchTitleField(String searchTitleField) {
        this.searchTitleField = searchTitleField;
    }
    
    public String getSearchKeywordField() {
        return searchKeywordField;
    }

    public void setSearchKeywordField(String searchKeywordField) {
        this.searchKeywordField = searchKeywordField;
    }
    
    public Date getSearchDateOccurrenceField() {
        return searchDateOccurrenceField;
    }

    public void setSearchDateOccurrenceField(Date searchDateOccurrenceField) {
        this.searchDateOccurrenceField = searchDateOccurrenceField;
    }
    
    public Date getSearchDateStartField() {
        return searchDateStartField;
    }

    public void setSearchDateStartField(Date searchDateStartField) {
        this.searchDateStartField = searchDateStartField;
    }
    
    public Date getSearchDateEndField() {
        return searchDateEndField;
    }

    public void setSearchDateEndField(Date searchDateEndField) {
        this.searchDateEndField = searchDateEndField;
    }
    
    public String getSearchOrganizationNameField() {
        return searchOrganizationNameField;
    }

    public void setSearchOrganizationNameField(String searchOrganizationNameField) {
        this.searchOrganizationNameField = searchOrganizationNameField;
    }
    
    public String getSearchZipCodeField() {
        return searchZipCodeField;
    }

    public void setSearchZipCodeField(String searchZipCodeField) {
        this.searchZipCodeField = searchZipCodeField;
    }
    
    public int getSearchZipCodeRadiusField() {
        return searchZipCodeRadiusField;
    }

    public void setSearchZipCodeRadiusField(int searchZipCodeRadiusField) {
        this.searchZipCodeRadiusField = searchZipCodeRadiusField;
    }
    
    public String getSearchActiveField() {
        return searchActiveField;
    }

    public void setActiveField(String searchActiveField) {
        this.searchActiveField = searchActiveField;
    }
    
    public String getSearchVolunteeringAreaField() {
        return searchVolunteeringAreaField;
    }

    public void setSearchVolunteeringAreaField(String searchVolunteeringAreaField) {
        this.searchVolunteeringAreaField = searchVolunteeringAreaField;
    }

    /*
    private Map<String, Object> security_questions;
        String      int
        ---------   ---
        question1,  0
        question2,  1
        question3,  2
            :
    When the user selects a security question, its number (int) is stored; not its String.
    Later, given the number (int), the security question String is retrieved.
     */
    public Map<String, Object> getUserRoles() {

        if (userRoles == null) {
            /*
            Difference between HashMap and LinkedHashMap:
            HashMap stores key-value pairings in no particular order. Values are retrieved based on their corresponding Keys.
            LinkedHashMap stores and retrieves key-value pairings in the order they were put into the map.
             */
            userRoles = new LinkedHashMap<>();

            for (int i = 0; i < Constants.USER_ROLES.length; i++) {
                userRoles.put(Constants.USER_ROLES[i], i);
            }
        }
        return userRoles;
    }
    
    public Map<String, Object> getZipCodeRadiuses() {

        if (zipCodeRadiuses == null) {
            /*
            Difference between HashMap and LinkedHashMap:
            HashMap stores key-value pairings in no particular order. Values are retrieved based on their corresponding Keys.
            LinkedHashMap stores and retrieves key-value pairings in the order they were put into the map.
             */
            zipCodeRadiuses = new LinkedHashMap<>();

            for (int i = 0; i < Constants.ZIPCODE_RADIUSES.length; i++) {
                zipCodeRadiuses.put(Constants.ZIPCODE_RADIUSES[i], i);
            }
        }
        return zipCodeRadiuses;
    }
    
    public Map<String, Object> getStatesNames() {

        if (statesNames == null) {
            /*
            Difference between HashMap and LinkedHashMap:
            HashMap stores key-value pairings in no particular order. Values are retrieved based on their corresponding Keys.
            LinkedHashMap stores and retrieves key-value pairings in the order they were put into the map.
             */
            statesNames = new LinkedHashMap<>();

            for (int i = 0; i < Constants.STATES.length; i++) {
                statesNames.put(Constants.STATES[i], i);
            }
        }
        return statesNames;
    }
    
    public Map<String, Object> getStatesAbbrv() {

        if (statesAbbrv == null) {
            /*
            Difference between HashMap and LinkedHashMap:
            HashMap stores key-value pairings in no particular order. Values are retrieved based on their corresponding Keys.
            LinkedHashMap stores and retrieves key-value pairings in the order they were put into the map.
             */
            statesAbbrv = new LinkedHashMap<>();

            for (int i = 0; i < Constants.STATES_ABBR.length; i++) {
                statesAbbrv.put(Constants.STATES_ABBR[i], i);
            }
        }
        return statesAbbrv;
    }
    
    public Map<String, Object> getVolunteeringAreas() {

        if (volunteeringAreas == null) {
            /*
            Difference between HashMap and LinkedHashMap:
            HashMap stores key-value pairings in no particular order. Values are retrieved based on their corresponding Keys.
            LinkedHashMap stores and retrieves key-value pairings in the order they were put into the map.
             */
            volunteeringAreas = new LinkedHashMap<>();

            for (int i = 0; i < Constants.VOLUNTEERING_AREA.length; i++) {
                volunteeringAreas.put(Constants.VOLUNTEERING_AREA[i], i);
            }
        }
        return volunteeringAreas;
    }
        
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public VolunteeringOpportunities getSelectedOpportunity() {
        return selectedOpportunity;
    }

    public void setSelectedOpportunity(VolunteeringOpportunities selectedOpportunity) {
        this.selectedOpportunity = selectedOpportunity;
    }
    
    public VolunteeringOpportunities getSelectedHistoryOpportunity() {
        return selectedHistoryOpportunity;
    }

    public void setSelectedHistoryOpportunity(VolunteeringOpportunities selectedHistoryOpportunity) {
        this.selectedHistoryOpportunity = selectedHistoryOpportunity;
    }
    
    public List<VolunteeringOpportunities> getOpportunities() {
        return opportunities;
    }

    public void setOpportunities(List<VolunteeringOpportunities> opportunities) {
        this.opportunities = opportunities;
    }

    public List<VolunteeringOpportunities> getHistoryOpportunities() {
//        if (historyOpportunities == null) {
//            historyOpportunities = searchAllVolunteeringHistory(selectedParticipant);
        return historyOpportunities;
    }

    public void setHistoryOpportunities(List<VolunteeringOpportunities> historyOpportunities) {
        this.historyOpportunities = historyOpportunities;
    }
            
    public Volunteer getSelectedParticipant() {
        return selectedParticipant;
    }

    public void setSelectedParticipant(Volunteer selectedParticipant) {
        this.selectedParticipant = selectedParticipant;
    }
    
    public List<Volunteer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Volunteer> participants) {
        this.participants = participants;
    }
    
    public List<Integer> getParticipantsIDs() {
        return participantsIDs;
    }

    public void setParticipantsIDs(List<Integer> participantsIDs) {
        this.participantsIDs = participantsIDs;
    }
    
    public boolean getParticipation() {
        return participation;
    }

    public void setParticipation(boolean participation) {
        this.participation = participation;
    }

    public VolunteeringHistory getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(VolunteeringHistory selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public List<Integer> getListOpportunityIDsFromUserHistory() {
        return listOpportunityIDsFromUserHistory;
    }

    public void setListOpportunityIDsFromUserHistory(List<Integer> listOpportunityIDsFromUserHistory) {
        this.listOpportunityIDsFromUserHistory = listOpportunityIDsFromUserHistory;
    }

            
    public List<VolunteeringHistory> getRecords() {
        return records;
    }

    public void setRecords(List<VolunteeringHistory> records) {
        this.records = records;
    }
    
        // EL in Profile.xhtml invokes this method to obtain the constant value
    public String photoStorageDirectoryName() {
        return Constants.STORAGE_DIRECTORY;
    }

    public String accountPhoto(VolunteeringOpportunities opportunity) {

        /*
        Roommate photo files are not stored in the database. Only the primary key (id) of the
        Roommate's photo is stored in the database.
        
        When Roommate uploads a photo, a thumbnail (small) version of the file is created
        in the saveThumbnail() method of FileManager by using the Scalr.resize method provided
        in the imgscalr (Java Image Scaling Library) imported as imgscalr-lib-4.2.jar

        Both uploaded and thumbnail photo files are named after the primary key (id) of the
        Roommate's photo and are stored in the PizzaHutStorageLocation. For example,
        for the primary key (id) = 25 and file extension = jpeg, the files are named as:
            e.g., 25.jpeg
            e.g., 25_thumbnail.jpeg
         */
        // Obtain a list of photo files (e.g., 25.jpeg and 25_thumbnail.jpeg) associated
        // with the logged-in Roommate whose database primary key is roommate.getId()
        Photo photo = photoFacade.findPhotoByOpportunityID(opportunity.getOpportunityID());

        if (photo == null) {
            // No Roommate photo exists. Return the default Roommate photo image.
            return "no_image_1.png";
        }

        /*
        photoList.get(0) returns the object reference of the first Photo object in the list.
        getThumbnailName() message is sent to that Photo object to retrieve its
        thumbnail image file name, e.g., 25_thumbnail.jpeg
         */
        return photo.getThumbnailName();
    }
    
    
    /*
    ===============================
     Opportunity Methods
    ===============================
     */
    
    // Return True if a roommate is logged in; otherwise, return False
    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userEmail") != null;
    }
    
    // Return True if a roommate is logged in; otherwise, return False
    public boolean isVolunteer() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userRole") == "Volunteer";
    }

    // Return True if a roommate is logged in; otherwise, return False
    public boolean isVolunteer(int userRole) {
        userRoles = getUserRoles();
        return (userRole) == (int) userRoles.get("Volunteer");
    }    
    
    // Return True if a roommate is logged in; otherwise, return False
    public boolean isOrganization() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userEmail") == "Organization";
    }
    
    // Return True if a roommate is logged in; otherwise, return False
    public boolean isOrganization(int userRole) {
        userRoles = getUserRoles();
        return (userRole) == (int) userRoles.get("Organization");
    }   
    
    public boolean isOwner() {
        return (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userID") == selectedOpportunity.getOwnerID().getUserID();
    }
    
    public boolean isVolunteerSubcribed() {
        int userID = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userID");
        return volunteeringHistoryFacade.isVolunteerSubscribedToOpportunity(userID, selectedOpportunity.getOpportunityID());
    }
    
    public boolean isVolunteerSubcribed(int userID) {
        return volunteeringHistoryFacade.isVolunteerSubscribedToOpportunity(userID, selectedOpportunity.getOpportunityID());
    }
    
    public boolean isVolunteerParticipationConfirmed(String participated) {
        return (participated != null) ? true : false;
    }
    
    public boolean isVolunteerParticipationConfirmed(int userID) {
        return volunteeringHistoryFacade.isVolunteerParticipationConfirmed(userID, selectedOpportunity.getOpportunityID());
    }
    
    /*
    Create a new task account. Return "" if an error occurs; otherwise,
    upon successful account creation, redirect to show the SignIn page.
     */
    public String createOpportunity() {

        try {
            // Instantiate a new Task object
            VolunteeringOpportunities opportunity = new VolunteeringOpportunities();

            // Get ID of user creating the opportunity
            ownerID = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userID");
            Users owner = organizationFacade.findByID(ownerID);
            
            // Dress up the newly created Task object with the values given
            opportunity.setOwnerID(owner);
            opportunity.setVolunteeringAreaID(volunteeringAreaID);
            opportunity.setTitle(title);
            opportunity.setDescription(description);
            opportunity.setAddress(address);
            opportunity.setCity(city);
            opportunity.setState(stateID);                
            opportunity.setZipCode(zipCode);
            opportunity.setDateOccurrence(dateOccurrence);
            opportunity.setActive('Y');

            state = Constants.STATES[stateID];
            opportunityFacade.create(opportunity);
            selectedOpportunity = opportunity;

            if (!JsfUtil.isValidationFailed()) {
                // The CREATE operation is successfully performed
                opportunities = null;    // Empty the items list
                showOpportunityInfo();
            }

        } catch (Exception e) {
            statusMessage = "Something went wrong while creating your Opportunity!";
            return "";
        }
        return "";
    }

    /*
    Create a new Task account. Return "" if an error occurs; otherwise,
    upon successful account creation, redirect to show the SignIn page.
     */
    public String editOpportunity() {
        // Check if date only checks calendar day or time as well
        if(selectedOpportunity.getDateOccurrence().before(today)) {
            statusMessage = "You cannot edit a past event.";
            return "";
        }
        
        if (statusMessage == null || statusMessage.isEmpty()) {
            
            try { 
                opportunityFacade.edit(selectedOpportunity);
                state = Constants.STATES[selectedOpportunity.getState()];
                opportunities = null;
            } catch (EJBException e) {
                statusMessage = "Something went wrong while editing your opportunity.";
                return "";
            }
        }
        return showUpdatedOpportunityInfo();
    }

    public String cancelOpportunity() {
        // Check if date only checks calendar day or time as well
        if(selectedOpportunity.getDateOccurrence().before(today)) {
            statusMessage = "You cannot cancel a past event.";
            return "";
        }
        
        if (selectedOpportunity.getDateOccurrence().after(today) && (statusMessage == null || statusMessage.isEmpty())) {
            try {
                selectedOpportunity.setActive('N');
                opportunityFacade.edit(selectedOpportunity);
                selectedOpportunity = null;
                opportunities = null;
                statusMessage = "Your opportunity has been canceled!";

            } catch (EJBException e) {
                statusMessage = "Something went wrong while cancelling your event";
                return "";
            }
            
            // show Volunteering Activity instead!
            return showVolunteeringActivity();
        }    
        return "";
    }
    
    public void deleteOpportunity() {
        if (statusMessage == null || statusMessage.isEmpty()) {
            opportunityFacade.remove(selectedOpportunity);
            if (!JsfUtil.isValidationFailed()) {
                // The DELETE operation is successfully performed
                selectedOpportunity = null;
                state = null;
                opportunities = null;
            }
        }
    }
    
    public List<VolunteeringOpportunities> searchAllOpportunities() {
        opportunities = opportunityFacade.findAll();
        return opportunities;
    }
    
    public void searchOwnedOpportunities() {
        
        // Make sure the logged in user is an organization
        if(!isOrganization()) {
            statusMessage = "Only organizations own volunteering activities.";
            return;
        }
        
        int organizationID = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userID");
        opportunities = opportunityFacade.findByOrganization(organizationID);

    }
    
    // Without Filters
    public List<VolunteeringOpportunities> searchAllVolunteeringHistory(Users user) {

        opportunities = null;
        statusMessage = "";
        
        // Returns all history without filter
        if (isVolunteer(user.getUserRole())) {
            listOpportunityIDsFromUserHistory = getUserVolunteeringHistoryRecords(user.getUserID());
            opportunities = opportunityFacade.SearchVolunteerHistoryOpportunities(listOpportunityIDsFromUserHistory);
        } else {
            opportunities = opportunityFacade.SearchOrganizationHistoryOpportunities(user.getUserID());
        }

        return opportunities;
    }
    
    // Search History with filter
    public List<VolunteeringOpportunities> searchVolunteeringHistory(Users user) {

        if (searchDateStartField == null ^ searchDateEndField == null) { // XOR -> '^'
            statusMessage = "Please fill both date fields or leave them empty.";
            return null;
        }
        
//        opportunities = null;
        statusMessage = "";
        listOpportunityIDsFromUserHistory = getUserVolunteeringHistoryRecords(user.getUserID());
        
        // If no search fields then show all
        if(searchDateStartField == null && searchDateEndField == null) 
            opportunities = opportunityFacade.SearchHistoryOpportunities(user.getUserID(), listOpportunityIDsFromUserHistory, searchZipCodeField, searchTitleField, searchKeywordField, searchOrganizationNameField, searchVolunteeringAreaField);
        else if(!searchDateStartField.after(searchDateEndField))
            opportunities = opportunityFacade.SearchHistoryOpportunitiesWithinDateRange(user.getUserID(), listOpportunityIDsFromUserHistory, searchZipCodeField, searchTitleField, searchKeywordField, searchOrganizationNameField, searchVolunteeringAreaField, searchDateStartField, searchDateEndField);
        else
            statusMessage = "Start Date cannot be later than End Date.";
        
        return opportunities;
        
    }
    
    public void searchOpportunities() {

        if (searchDateStartField == null ^ searchDateEndField == null) { // XOR -> '^'
            statusMessage = "Please fill both date fields or leave them empty.";
            return;
        }
        
        visible = "visible";
        opportunities = null;
        statusMessage = "";
        
        // Get list of Zip Codes
        List<String> zipCodesList = getZipCodesList();
        
        //Use this instead to get list of Zip Codes when testing
//        List<String> zipCodesList = new ArrayList<String>();
//        zipCodesList.add("24060");
//        zipCodesList.add("24061");
        
        // If no search fields then show all
        if(searchDateStartField == null && searchDateEndField == null) 
            opportunities = opportunityFacade.SearchOpportunities(zipCodesList, searchTitleField, searchKeywordField, searchOrganizationNameField, searchVolunteeringAreaField);
        else if(!searchDateStartField.after(searchDateEndField))
            opportunities = opportunityFacade.SearchOpportunitiesWithinDateRange(zipCodesList, searchTitleField, searchKeywordField, searchOrganizationNameField, searchVolunteeringAreaField, searchDateStartField, searchDateEndField); // , searchVolunteeringAreaField)
        else
            statusMessage = "Start Date cannot be later than End Date.";
            
        return;
        
    }
  
    public List<String> getZipCodesList () {
        
        /*
            Creating json object and json array to hold desired data 
         
            JSON data use the following notation:
            { }    represents a JavaScript object with its properties as Key:Value pairs
            [ ]    represents Array
            [{ }]  represents an Array of JavaScript objects
            :    separates Key from the Value
        */
        JSONObject resultsObject;
        JSONArray resultsArray;
        String zipCodes = null;
        List<String> zipCodesList = new ArrayList<String>();
        
        if (searchZipCodeField == null || searchZipCodeField.isEmpty())
            return zipCodesList;
        
        // Create the movies list containing movies
        try {
            resultsObject = new JSONObject(readUrlContent(ZipCodeAPI1 + searchZipCodeField + "/" + Constants.ZIPCODE_RADIUSES[searchZipCodeRadiusField] + ZipCodeAPI2));
            resultsArray = resultsObject.getJSONArray("zip_codes");
            
            int index = 0;
            
            if (resultsArray.length() > index) {
                
                while (resultsArray.length() > index) {
                    zipCodesList.add(resultsArray.getString(index++));
                }

            } else {
                statusMessage = "ZipCodeAPI is unreachable!";
                return null;
            }
            
        } catch (Exception ex) {
            Logger.getLogger(VolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return zipCodesList;
    }
   
    public String getParticipationMessage(String participated){
        if (participated.equals("Y"))
            return "Participated";
        else if (participated.equals("N"))
            return "No participation";
        else
            return "";
    }
    
    public String opportunitySubscription() {
        
        int userID = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userID");
        
        if (!isVolunteerSubcribed(userID))
            subscribeToOpportunity(userID);
        else
            unsubscribeFromOpportunity(userID);
        
        getOpportunityParticipants();
        return "";
    }
    
    public void subscribeToOpportunity(int userID) {
        
        
        if(selectedOpportunity.getDateOccurrence().before(today)) {
            statusMessage = "You cannot subscribe to a past event.";
            return;
        }
        
        if (statusMessage == null || statusMessage.isEmpty()) {
            try {
                // Instantiate a new Task object
                Users volunteer = volunteerFacade.findByID(userID);
                VolunteeringHistory record = new VolunteeringHistory();
                record.setParticipant(volunteer);
                record.setOpportunityID(selectedOpportunity.getOpportunityID());
                volunteeringHistoryFacade.create(record);
                
                if (!JsfUtil.isValidationFailed()) {
                    // The CREATE operation is successfully performed
                    opportunities = null;    // Empty the items list
                }
            } catch (Exception e) {
                statusMessage = "Something went wrong while subscribing to the Opportunity!";
                return;
            }
        }
    }
    
    public void unsubscribeFromOpportunity(int userID) {

        if(selectedOpportunity.getDateOccurrence().before(today)) {
            statusMessage = "You cannot unsubscribe from a past event.";
            return;
        }
        
        if (statusMessage == null || statusMessage.isEmpty()) {
            try {
                volunteeringHistoryFacade.deleteRecord(userID, selectedOpportunity.getOpportunityID());
                opportunities = null;       // Empty the items list
            } catch (EJBException e) {
                statusMessage = "Something went wrong while unsubscribing from opportunity.";
            }
        }
    }
    
    public String confirmParticipation(VolunteeringHistory participantRecord) {
        
        
        if (participation)
            confirmVolunteerParticipation(participantRecord);
        else
            declineVolunteerParticipation(participantRecord);
        
        getOpportunityParticipants();
        return "";
    }
    
    public void confirmVolunteerParticipation(VolunteeringHistory participantRecord) {
        
        // Check if date only checks calendar day or time as well
        if(selectedOpportunity.getDateOccurrence().after(today)) {
            statusMessage = "You cannot confirm participation of a future event.";
            return;
        }
        
        if (selectedOpportunity.getDateOccurrence().before(today) && (statusMessage == null || statusMessage.isEmpty())) {
            try {
                participantRecord.setParticipated("Y");
                volunteeringHistoryFacade.edit(participantRecord);
                getOpportunityParticipants();
                //selectedRecord.setParticipated("Y");
                //volunteeringHistoryFacade.edit(selectedRecord);
            } catch (EJBException e) {
                statusMessage = "Something went wrong while confirming the volunteer's participation.";
            }
        }    
    }
    
    public void declineVolunteerParticipation(VolunteeringHistory participantRecord) {
             
        // Check if date only checks calendar day or time as well
        if(selectedOpportunity.getDateOccurrence().after(today)) {
            statusMessage = "You cannot decline participation of a future event.";
            return;
        }
        
        if (selectedOpportunity.getDateOccurrence().before(today) && (statusMessage == null || statusMessage.isEmpty())) {
            try {
                participantRecord.setParticipated("N");
                volunteeringHistoryFacade.edit(participantRecord);
                getOpportunityParticipants();
                //selectedRecord.setParticipated("N");
                //volunteeringHistoryFacade.edit(selectedRecord);
            } catch (EJBException e) {
                statusMessage = "Something went wrong while declining the volunteer's participation.";
            }
        }    
    }
    
    public List<Integer> getUserVolunteeringHistoryRecords(int userID) {
        statusMessage = "";
        return volunteeringHistoryFacade.getOpportunityIDsFromVolunteerHistory(userID);
    }

    public void getOpportunityParticipants() {
        participants = null;
        records = null;
        statusMessage = "";
        records = volunteeringHistoryFacade.getOpportunityParticipantsRecords(selectedOpportunity.getOpportunityID());
        return;
    }
    
    //Method hides table with data when exiting the page
    public void leaving() {
        searchTitleField = "";
        searchKeywordField = "";
        searchDateOccurrenceField = null;
        searchDateStartField = null;
        searchDateEndField = null;
        searchOrganizationNameField = "";
        searchVolunteeringAreaField = null;
        searchZipCodeField = "";
        searchActiveField = null;
        visible = "hidden";
    }
    
    /**
     * Author: Dr. Balci
     * Return the content of a given URL as String 
     * @param webServiceURL to retrieve the JSON data from
     * @return JSON data from the given URL as String
     * @throws Exception
     */
    public String readUrlContent(String webServiceURL) throws Exception {
        BufferedReader reader = null;

        try {
            URL url = new URL(webServiceURL);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));

            StringBuilder buffer = new StringBuilder();

            char[] chars = new char[10240];

            int numberOfCharactersRead;
            
            while ((numberOfCharactersRead = reader.read(chars)) != -1) {
                buffer.append(chars, 0, numberOfCharactersRead);
            }

            return buffer.toString();

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        //pdf.addTitle("Volunteering History");
    }
    
    
    /*
    ===============================
     Navigation Methods
    ===============================
     */
    
    public String homePageDestination() {

        statusMessage = null;
        if (isLoggedIn()) {
            return showDashboard();
        } else {
            return showIndexPage();
        }
    }

    // Show the Index page
    public String showIndexPage() {

        statusMessage = null;
        return "index?faces-redirect=true";
    }

    // Show the Dashboard page
    public String showDashboard() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "Dashboard?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }
    
    public String showSearchOpportunity() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "SearchOpportunity?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }
    
    public String showUpdatedOpportunityInfo() {
        statusMessage = null;
        return "OpportunityInfo.xhtml?faces-redirect=true";
    }
    
    public void showOpportunityInfo() {
        statusMessage = null;
        try {
            getOpportunityParticipants();
            FacesContext.getCurrentInstance().getExternalContext().redirect("OpportunityInfo.xhtml?faces-redirect=true");
        } 
        catch(IOException e) { 
        
        }
    }
    
    public void showOpportunityInfo(VolunteeringOpportunities opportunity) {
        try {
            statusMessage = null;
            selectedOpportunity = opportunity;
            getOpportunityParticipants();
            FacesContext.getCurrentInstance().getExternalContext().redirect("OpportunityInfo.xhtml?faces-redirect=true");
        } 
        catch(IOException e) { 
        
        }
    }
    
    public void showEditOpportunity() {
        statusMessage = null;
        try {
            // CallMethodForVolunteerInfo();
            FacesContext.getCurrentInstance().getExternalContext().redirect("EditOpportunity.xhtml?faces-redirect=true");
        } 
        catch(IOException e) { 
        
        }
    }
    
    public String showChangeOpportunityPhoto() {

        statusMessage = null;
        if (isLoggedIn()) {
            FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("opportunityID", selectedOpportunity.getOpportunityID());
            return "ChangeOpportunityPhoto?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }
        
    public String showVolunteeringActivity() {
        statusMessage = null;
        if (isLoggedIn()) {
            return "ViewActivity?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }
    
    public void showVolunteerInfo() {
        statusMessage = null;
        try {
            // CallMethodForVolunteerInfo();
            FacesContext.getCurrentInstance().getExternalContext().redirect("VolunteerInfo.xhtml?faces-redirect=true");
        } 
        catch(IOException e) { 
        
        }
    }
            
    public void showOrganizationInfo() {
        statusMessage = null;
        try {
            // CallMethodForVolunteerInfo();
            FacesContext.getCurrentInstance().getExternalContext().redirect("OrganizationInfo.xhtml?faces-redirect=true");
        } 
        catch(IOException e) { 
        
        }
    }
    
}
