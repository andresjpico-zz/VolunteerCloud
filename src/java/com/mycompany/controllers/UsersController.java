package com.mycompany.controllers;

import com.mycompany.entityclasses.Users;
import com.mycompany.entityclasses.Volunteer;
import com.mycompany.entityclasses.Organization;
import com.mycompany.entityclasses.UserVolunteeringInterest;
import com.mycompany.entityclasses.Constants;
import com.mycompany.entityclasses.Photo;
import com.mycompany.controllers.util.JsfUtil;
import com.mycompany.controllers.util.JsfUtil.PersistAction;
import com.mycompany.sessionbeans.UsersFacade;
import com.mycompany.sessionbeans.VolunteerFacade;
import com.mycompany.sessionbeans.OrganizationFacade;
import com.mycompany.sessionbeans.UserVolunteeringInterestFacade;
import com.mycompany.sessionbeans.PhotoFacade;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres
 */

@Named(value = "UsersController")
@SessionScoped
public class UsersController implements Serializable {
//public class UsersController implements Serializable {  Could be called AccountController Later!
    
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private String username;
    private String loginUsername;
    private String email;
    private String loginEmail;
    private String password;
    private String newPassword;
    private String loginPassword;
    private String securityAnswer;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String organizationName;
    private String mission;
    private String website;
    private String address;
    private String city;
    private String userRole;
    private int userRoleID;
    private int securityQuestion;
    private Integer volunteerMatchID;
    private Character active;
    private int stateID;
    private String state;
    private String zipCode;
    
    private Map<String, Object> securityQuestions;
    private Map<String, Object> userRoles;
    private Map<String, Object> statesNames;
    private Map<String, Object> statesAbbrv;
    private Map<String, Object> volunteeringAreas;
    private List<String> userAreasOfInterest;


    private String statusMessage;
    private boolean modeEditInterestAreas;

    private Users selectedUser;
    private List<Users> users;
    private List<Users> usersButMe;
    
    private Volunteer selectedVolunteer;
    private List<Volunteer> volunteers;
    private List<Volunteer> volunteersButMe;
    
    private Organization selectedOrganization;
    private List<Organization> organizations;
    private List<Organization> organizationsButMine;

    /*
    The @EJB annotation implies that the EJB container will perform an injection of the object
    reference of the <file_name>Facade object into <file_name>Facade when it is created at runtime.
     */
    @EJB
    private UsersFacade usersFacade;
    
    @EJB
    private VolunteerFacade volunteerFacade;
    
    @EJB
    private OrganizationFacade organizationFacade;
    
    @EJB
    private UserVolunteeringInterestFacade volunteeringInterestFacade;
    
    @EJB
    private PhotoFacade photoFacade;
    
    // Constructor method instantiating an instance of UserController
    public UsersController() {
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
    
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    
    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }
    
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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
    
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    public int getUserRoleID() {
        return userRoleID;
    }

    public void setUserRoleID(int userRoleID) {
        this.userRoleID = userRoleID;
    }
    
    public int getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(int securityQuestion) {
        this.securityQuestion = securityQuestion;
    }
    
    public Integer getVolunteerMatchID() {
        return volunteerMatchID;
    }

    public void setVolunteerMatchID(Integer volunteerMatchID) {
        this.volunteerMatchID = volunteerMatchID;
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
    
    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
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
    public Map<String, Object> getSecurityQuestions() {

        if (securityQuestions == null) {
            /*
            Difference between HashMap and LinkedHashMap:
            HashMap stores key-value pairings in no particular order. Values are retrieved based on their corresponding Keys.
            LinkedHashMap stores and retrieves key-value pairings in the order they were put into the map.
             */
            securityQuestions = new LinkedHashMap<>();

            for (int i = 0; i < Constants.SECURITY_QUESTIONS.length; i++) {
                securityQuestions.put(Constants.SECURITY_QUESTIONS[i], i);
            }
        }
        return securityQuestions;
    }
        
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
    
    public List<String> getUserAreasOfInterest() {
        userAreasOfInterest = volunteeringInterestFacade.getStringListInterestAreaIDsFromUser(selectedUser.getUserID());
        return userAreasOfInterest;
    }

    public void setUserAreasOfInterest(List<String> userAreasOfInterest) {
        this.userAreasOfInterest = userAreasOfInterest;
    }
    
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    public boolean getModeEditInterestAreas() {
        return modeEditInterestAreas;
    }

    public void setModeEditInterestAreas(boolean modeEditInterestAreas) {
        this.modeEditInterestAreas = modeEditInterestAreas;
    }
            
    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
    
    public List<Users> getUsersButMe() {
        return usersButMe;
    }

    public void setUsersButMe(List<Users> usersButMe) {
        this.usersButMe = usersButMe;
    }
    
    public Volunteer getSelectedVolunteer() {
        return selectedVolunteer;
    }

    public void setSelectedVolunteer(Volunteer selectedVolunteer) {
        this.selectedVolunteer = selectedVolunteer;
    }
    
    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }
    
    public List<Volunteer> getVolunteersButMe() {
        return volunteersButMe;
    }

    public void setVolunteersButMe(List<Volunteer> volunteersButMe) {
        this.volunteersButMe = volunteersButMe;
    }

    public Organization getSelectedOrganization() {
        return selectedOrganization;
    }

    public void setSelectedOrganization(Organization selectedOrganization) {
        this.selectedOrganization = selectedOrganization;
    }
    
    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
    
    public List<Organization> getOrganizationButMine() {
        return organizationsButMine;
    }

    public void setOrganizationsButMine(List<Organization> organizationsButMine) {
        this.organizationsButMine = organizationsButMine;
    }

    // EL in Profile.xhtml invokes this method to obtain the constant value
    public String photoStorageDirectoryName() {
        return Constants.STORAGE_DIRECTORY;
    }

    public String accountPhoto() {

        // Obtain the username of the logged-in roommate
        String userEmail = (String) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("userEmail");

        Users user = usersFacade.findByEmail(userEmail);

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
        Photo photo = photoFacade.findPhotoByUserID(user.getUserID());

        if (photo == null) {
            // No Roommate photo exists. Return the default Roommate photo image.
            return "defaultPhoto.png";
        }

        /*
        photoList.get(0) returns the object reference of the first Photo object in the list.
        getThumbnailName() message is sent to that Photo object to retrieve its
        thumbnail image file name, e.g., 25_thumbnail.jpeg
         */
        return photo.getThumbnailName();
    }

    public String accountPhoto(Users user) {

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
        Photo photo = photoFacade.findPhotoByUserID(user.getUserID());

        if (photo == null) {
            // No Roommate photo exists. Return the default Roommate photo image.
            return "defaultPhoto.png";
        }

        /*
        photoList.get(0) returns the object reference of the first Photo object in the list.
        getThumbnailName() message is sent to that Photo object to retrieve its
        thumbnail image file name, e.g., 25_thumbnail.jpeg
         */
        return photo.getThumbnailName();
    }
    
    /*
    Delete both uploaded and thumbnail photo files that belong to the roommate
    object whose database primary key is roommateId
     */
    public void deletePhoto(int userID) {

        /*
        Obtain the list of Photo objects that belong to the roommate whose
        database primary key is roommateId.
         */
        Photo photo = photoFacade.findPhotoByUserID(userID);

        if (photo != null ) {

            try {
                // Delete the uploaded photo file if it exists
                Files.deleteIfExists(Paths.get(photo.getFilePath()));

                // Delete the thumbnail image file if it exists
                Files.deleteIfExists(Paths.get(photo.getThumbnailFilePath()));

                // Delete the temporary file if it exists
                Files.deleteIfExists(Paths.get(Constants.ROOT_DIRECTORY + "tmp_file"));

                // Remove the roommate photo's record from the database
                photoFacade.remove(photo);

            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*
    ===============================
     Users Methods
    ===============================
     */
    
    // Return True if a roommate is logged in; otherwise, return False
    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userEmail") != null;
    }
    
    public boolean isVolunteer() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userRole") == "Volunteer";
    }
    
    public boolean isUserSameAsUserLoggedIn(int userID) {
        return selectedUser.getUserID() == userID;
    }
    
    public boolean isOrganization() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userRole") == "Organization";
    }
    
    // Initialize the session map for the Roommate object
    public void initializeSessionMap() {

        // Obtain the object reference of the roommate object
        Users user = usersFacade.findByEmail(email);

        // Put the roommate's object reference into session map variable roommate
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("user", user);

        // Put the roommate's database primary key into session map variable roommateID
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("userID", user.getUserID());
        
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("userEmail", user.getEmail());

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("username", user.getUsername());
        
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("firstName", user.getFirstName());

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("lastName", user.getLastName());

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("organizationName", user.getOrganizationName());
        
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("userRole", getUserRole());
        
    }     

    // Initialize the session map for the roommate object
    public void initializeSessionMap(Users user) {

        // Put the roommate's object reference into session map variable roommate
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("user", user);

        // Put the roommate's database primary key into session map variable roommateID
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("userID", user.getUserID());

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("userEmail", user.getEmail());
        
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("username", user.getUsername());
        
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("firstName", user.getFirstName());

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("lastName", user.getLastName());

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("organizationName", user.getOrganizationName());
        
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("userRole", getUserRole());

    }
    
    /*
    Create a new Roommate account. Return "" if an error occurs; otherwise,
    upon successful account creation, redirect to show the SignIn page.
     */
    public String registerUser(String selectedUserRole) {
    
        if(selectedUserRole.equals(Constants.USER_ROLES[0]))
            userRole = Constants.USER_ROLES[0];
        else
            userRole = Constants.USER_ROLES[1];
        
        return showRegister();
    }
    
    /*
    Create a new Roommate account. Return "" if an error occurs; otherwise,
    upon successful account creation, redirect to show the SignIn page.
     */
    public String createUser() {

        // Obtain the object reference of the Roommate object with username
        Users userEmailRegistered = usersFacade.findByEmail(email);
        Users userUsernameRegistered = usersFacade.findByUsername(username);
        statusMessage = "";

        if (userEmailRegistered != null) {
            // A Roommate already exists with the username given
            email = "";
            statusMessage = "Email already registered!";
            return "";
        }
        
        if (userUsernameRegistered != null) {
            // A Roommate already exists with the username given
            email = "";
            statusMessage = "Username already registered!";
            return "";
        }

        try {
            
            Users user = new Users();

            
            if (userRole.equals(Constants.USER_ROLES[0])) {
                
                // Instantiate a new Roommate object
                Volunteer volunteer = new Volunteer();

                // Dress up the newly created Roommate object with the values given
                volunteer.setFirstName(firstName);
                volunteer.setLastName(lastName);
                volunteer.setEmail(email);
                volunteer.setUsername(username);
                volunteer.setPassword(password);
                volunteer.setSecurityQuestion(securityQuestion);
                volunteer.setSecurityAnswer(securityAnswer);
                volunteer.setMission(mission);
                volunteer.setPhoneNumber(phoneNumber);
                volunteer.setWebsite(website);
                volunteer.setAddress(address);
                volunteer.setCity(city);
                volunteer.setState(stateID);
                volunteer.setZipCode(zipCode);
                volunteer.setActive('Y');
                
                volunteerFacade.create(volunteer);
                user = volunteer;
                
            } else {
                // Instantiate a new Roommate object
                Organization organization = new Organization();

                // Dress up the newly created Roommate object with the values given
                organization.setFirstName(firstName);
                organization.setLastName(lastName);
                organization.setOrganizationName(organizationName);
                organization.setEmail(email);
                organization.setUsername(username);
                organization.setPassword(password);
                organization.setSecurityQuestion(securityQuestion);
                organization.setSecurityAnswer(securityAnswer);
                organization.setMission(mission);
                organization.setPhoneNumber(phoneNumber);
                organization.setWebsite(website);
                organization.setAddress(address);
                organization.setCity(city);
                organization.setState(stateID);
                organization.setZipCode(zipCode);
                organization.setActive('Y');
                
                organizationFacade.create(organization);
                user = organization;
                
            }
            
            state = Constants.STATES[stateID];
            loginUsername = username;
            loginPassword = password;
            selectedUser = user;
            initializeSessionMap(user);

        } catch (EJBException e) {
            email = "";
            statusMessage = "Something went wrong while creating your account!";
            return "";
        }

        /*
        The Profile page cannot be shown since the Roommate has not signed in yet.
        Therefore, we show the Sign In page for the Roommate to sign in first.
         */
        return showDashboard();
    }
    
    /*
    Sign in the Roommate if the entered username and password are valid
    @return "" if an error occurs; otherwise, redirect to show the Profile page
     */
    public String loginUser() {

        // Obtain the object reference of the Roommate object from the entered username
        Users user = usersFacade.findByUsername(loginUsername);
        statusMessage = "";
        
        if (user == null) {
            statusMessage = "Entered username " + loginUsername + " does not exist!";
            return "";

        } else {

            String actualUsername = user.getUsername();

            String actualPassword = user.getPassword();

            if (!actualUsername.equals(loginUsername)) {
                statusMessage = "Invalid Username!";
                return "";
            }

            if (!actualPassword.equals(loginPassword)) {
                statusMessage = "Invalid Password!";
                return "";
            }
            
            if (user.getActive() == 'N') {

                try {
                    user.setActive('Y');
                    usersFacade.edit(user);
                } catch (EJBException e) {
                    username = password = "";
                    statusMessage = "Something went wrong while reactivating your account!";
                    return "";
                }
            }

            statusMessage = "";

            // Retrieve values from Roommate
            selectedUser = user;
            firstName = user.getFirstName();
            lastName = user.getLastName();
            email = user.getEmail();
            username = user.getUsername();
            password = user.getPassword();
            securityQuestion = user.getSecurityQuestion();
            securityAnswer = user.getSecurityAnswer();
            mission = user.getMission();
            organizationName = user.getOrganizationName();
            volunteerMatchID = user.getVolunteerMatchID();
            phoneNumber = user.getPhoneNumber();
            website = user.getWebsite();
            address = user.getAddress();
            city = user.getCity();
            userRoleID = user.getUserRole();
            stateID = user.getState();
            state = Constants.STATES[stateID];
            zipCode = user.getZipCode();
            userRoles = getUserRoles();
            userRole = Constants.USER_ROLES[userRoleID];
            
            // Initialize the session map with Roommate properties of interest
            initializeSessionMap(user);

            // Redirect to show the Profile page
            return showDashboard();
        }
    }
    
    public String resetPassword() {
        // Redirect to show the EnterUsername page
//        loginEmail = ".";
//        loginPassword = ".";
        return "EnterUsername.xhtml?faces-redirect=true";
    }

    /*
    Update the logged-in Roommate's account profile. Return "" if an error occurs;
    otherwise, upon successful account update, redirect to show the Profile page.
     */
    public String editUser() {
        
        if (statusMessage == null || statusMessage.isEmpty()) {
            
            // Obtain the username of the logged-in Roommate
            String userEmail = (String) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("userEmail");
        
            // If changes to the email address have been made
            if (!userEmail.equals(this.selectedUser.getEmail())) {

                Users aUser = usersFacade.findByEmail(this.selectedUser.getEmail());

                if (aUser != null) {
                    // A Roommate already exists with the username given
                    email = "";
                    statusMessage = "New email already registered!";
                    return "";
                }
            }
            
            Users user = usersFacade.findByEmail(userEmail);
            
            try {
                // Set the logged-in Roommate's properties to the given values
                user.setEmail(this.selectedUser.getEmail());
                user.setFirstName(this.selectedUser.getFirstName());
                user.setLastName(this.selectedUser.getLastName());
                user.setOrganizationName(this.selectedUser.getOrganizationName());
                user.setMission(this.selectedUser.getMission());
                user.setPhoneNumber(this.selectedUser.getPhoneNumber());
                user.setWebsite(this.selectedUser.getWebsite());
                user.setAddress(this.selectedUser.getAddress());
                user.setCity(this.selectedUser.getCity());
                user.setState(this.selectedUser.getState());
                user.setZipCode(this.selectedUser.getZipCode());
                
                // It is optional for the Roommate to change his/her password
                String new_Password = getNewPassword();

                if (new_Password == null || new_Password.isEmpty()) {
                    // Do nothing. The user does not want to change the password.
                } else {
                    user.setPassword(new_Password);
                    // Password changed successfully!
                    // Password was first validated by invoking the validatePasswordChange method below.
                }

                // The changes are stored in the database
                usersFacade.edit(user);
                state = Constants.STATES[this.selectedUser.getState()];
                // Initialize the session map with roommate properties of interest
                initializeSessionMap(user);

            } catch (EJBException e) {
                email = "";
                statusMessage = "Something went wrong while editing your profile!";
                return "";
            }
            // Account update is completed, redirect to show the Profile page.
            return showProfile();
        }
        return "";
    }

    /*
    Delete the logged-in roommate's account. Return "" if an error occurs; otherwise,
    upon successful account deletion, redirect to show the index (home) page.
     */
    public String deactivateAccount() {

        if (statusMessage == null || statusMessage.isEmpty()) {

            // Obtain the username of the logged-in Roommate
            String userEmail = (String) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("userEmail");
        
            Users user = usersFacade.findByEmail(userEmail);
            
            try {
                // Set the logged-in Roommate's properties to the given values
                user.setActive('N');
                usersFacade.edit(user);

            } catch (EJBException e) {
                email = "";
                statusMessage = "Something went wrong while deactivating your account!";
                return "";
            }
            // Account update is completed, redirect to show the Profile page.
            logout();
            return showIndexPage();
        }
        return "";
    }
    
    /*
    Delete the logged-in roommate's account. Return "" if an error occurs; otherwise,
    upon successful account deletion, redirect to show the index (home) page.
     */
    public String deleteAccount() {

        if (statusMessage == null || statusMessage.isEmpty()) {

            // Obtain the database primary key of the logged-in roommate object
            int userID = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userID");

            try {
                deletePhoto(userID);
                // Delete the roommate entity, whose primary key is roommate_id, from the database
                usersFacade.deleteUser(userID);

            } catch (EJBException e) {
                userID = -1;
                statusMessage = "Something went wrong while deleting your account!";
                return "";
            }

            logout();
            return showIndexPage();
        }
        return "";
    }
    
    public String logout() {

        // Clear the logged-in roommate's session map
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();

        // Reset the logged-in roommate's properties
        email = username = password = "";
        firstName = lastName = organizationName = "";
        mission = website = phoneNumber = "";
        address = city = state = zipCode = "";
        stateID = userRoleID = volunteerMatchID = securityQuestion = 0;
        userRole = securityAnswer = "";
        statusMessage = "";
        selectedUser = null;
        
        // Invalidate the logged-in roommate's session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        // Redirect to show the index (Home) page
        return showIndexPage();
    }    

    // Validate if the entered password matches the entered confirm password
    public void validateInformation(ComponentSystemEvent event) {

        /*
        FacesContext contains all of the per-request state information related to the processing of
        a single JavaServer Faces request, and the rendering of the corresponding response.
        It is passed to, and potentially modified by, each phase of the request processing lifecycle.
         */
        FacesContext fc = FacesContext.getCurrentInstance();

        /*
        UIComponent is the base class for all user interface components in JavaServer Faces. 
        The set of UIComponent instances associated with a particular request and response are organized into
        a component tree under a UIViewRoot that represents the entire content of the request or response.
         */
        // Obtain the UIComponent instances associated with the event
        UIComponent components = event.getComponent();

        /*
        UIInput is a kind of UIComponent for the user to enter a value in.
         */
        // Obtain the object reference of the UIInput field with id="password" on the UI
        UIInput uiInputPassword = (UIInput) components.findComponent("password");

        // Obtain the password entered in the UIInput field with id="password" on the UI
        String entered_password = uiInputPassword.getLocalValue()
                == null ? "" : uiInputPassword.getLocalValue().toString();

        // Obtain the object reference of the UIInput field with id="confirmPassword" on the UI
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");

        // Obtain the confirm password entered in the UIInput field with id="confirmPassword" on the UI
        String entered_confirm_password = uiInputConfirmPassword.getLocalValue()
                == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

        if (entered_password.isEmpty() || entered_confirm_password.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }

        if (!entered_password.equals(entered_confirm_password)) {
            statusMessage = "Password and Confirm Password must match!";
        } else {
            statusMessage = "";
        }
    }

    // Validate the new password and new confirm password
    public void validatePasswordChange(ComponentSystemEvent event) {
        /*
        FacesContext contains all of the per-request state information related to the processing of
        a single JavaServer Faces request, and the rendering of the corresponding response.
        It is passed to, and potentially modified by, each phase of the request processing lifecycle.
         */
        FacesContext fc = FacesContext.getCurrentInstance();

        /*
        UIComponent is the base class for all user interface components in JavaServer Faces. 
        The set of UIComponent instances associated with a particular request and response are organized into
        a component tree under a UIViewRoot that represents the entire content of the request or response.
         */
        // Obtain the UIComponent instances associated with the event
        UIComponent components = event.getComponent();

        /*
        UIInput is a kind of UIComponent for the user to enter a value in.
         */
        // Obtain the object reference of the UIInput field with id="newPassword" on the UI
        UIInput uiInputPassword = (UIInput) components.findComponent("newPassword");

        // Obtain the new password entered in the UIInput field with id="newPassword" on the UI
        String new_Password = uiInputPassword.getLocalValue()
                == null ? "" : uiInputPassword.getLocalValue().toString();

        // Obtain the object reference of the UIInput field with id="newConfirmPassword" on the UI
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("newConfirmPassword");

        // Obtain the new confirm password entered in the UIInput field with id="newConfirmPassword" on the UI
        String new_ConfirmPassword = uiInputConfirmPassword.getLocalValue()
                == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

        // It is optional for the roommate to change his/her password
        if (new_Password.isEmpty() || new_ConfirmPassword.isEmpty()) {
            // Do nothing. The user does not want to change the password.
            return;
        }

        if (!new_Password.equals(new_ConfirmPassword)) {
            statusMessage = "New Password and New Confirm Password must match!";
        } else {
            /*
            REGular EXpression (regex) for validating password strength:
            (?=.{8,31})      ==> Validate the password to be minimum 8 and maximum 31 characters long. 
            (?=.*[!@#$%^&*]) ==> Validate the password to contain at least one special character. 
            (?=.*[A-Z])      ==> Validate the password to contain at least one uppercase letter. 
            (?=.*[a-z])      ==> Validate the password to contain at least one lowercase letter. 
            (?=.*[0-9])      ==> Validate the password to contain at least one number from 0 to 9.
             */
            String regex = "^(?=.{8,31})(?=.*[!@#$%^&*])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$";

            if (!new_Password.matches(regex)) {
                statusMessage = "The password must be minimum 8 "
                        + "characters long, contain at least one special character, "
                        + "contain at least one uppercase letter, "
                        + "contain at least one lowercase letter, "
                        + "and contain at least one number 0 to 9.";
            } else {
                statusMessage = "";
            }
        }
    }

    // Validate if the entered password and confirm password are correct
    public void validateUserPassword(ComponentSystemEvent event) {
        /*
        FacesContext contains all of the per-request state information related to the processing of
        a single JavaServer Faces request, and the rendering of the corresponding response.
        It is passed to, and potentially modified by, each phase of the request processing lifecycle.
         */
        FacesContext fc = FacesContext.getCurrentInstance();

        /*
        UIComponent is the base class for all user interface components in JavaServer Faces. 
        The set of UIComponent instances associated with a particular request and response are organized into
        a component tree under a UIViewRoot that represents the entire content of the request or response.
         */
        // Obtain the UIComponent instances associated with the event
        UIComponent components = event.getComponent();

        /*
        UIInput is a kind of UIComponent for the user to enter a value in.
         */
        // Obtain the object reference of the UIInput field with id="password" on the UI
        UIInput uiInputPassword = (UIInput) components.findComponent("password");

        // Obtain the password entered in the UIInput field with id="password" on the UI
        String entered_password = uiInputPassword.getLocalValue()
                == null ? "" : uiInputPassword.getLocalValue().toString();

        // Obtain the object reference of the UIInput field with id="confirmPassword" on the UI
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");

        // Obtain the confirm password entered in the UIInput field with id="confirmPassword" on the UI
        String entered_confirm_password = uiInputConfirmPassword.getLocalValue()
                == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

        if (entered_password.isEmpty() || entered_confirm_password.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }

        if (!entered_password.equals(entered_confirm_password)) {
            statusMessage = "Password and Confirm Password must match!";
        } else {
            // Obtain the logged-in roommate's username
            String userEmail = (String) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("userEmail");

            // Obtain the object reference of the logged-in roommate object
            Users user = usersFacade.findByEmail(userEmail);

            if (entered_password.equals(user.getPassword())) {
                // entered password = logged-in roommate's password
                statusMessage = "";
            } else {
                statusMessage = "Incorrect Password!";
            }
        }
    }

    /*
    UIComponent is the base class for all user interface components in JavaServer Faces. 
    The set of UIComponent instances associated with a particular request and response are organized into
    a component tree under a UIViewRoot that represents the entire content of the request or response.

    @param components: UIComponent instances associated with the current request and response
    @return True if entered password is correct; otherwise, return False
     */
    private boolean correctPasswordEntered(UIComponent components) {

        // Obtain the object reference of the UIInput field with id="verifyPassword" on the UI
        UIInput uiInputVerifyPassword = (UIInput) components.findComponent("verifyPassword");

        // Obtain the verify password entered in the UIInput field with id="verifyPassword" on the UI
        String verifyPassword = uiInputVerifyPassword.getLocalValue()
                == null ? "" : uiInputVerifyPassword.getLocalValue().toString();

        if (verifyPassword.isEmpty()) {
            statusMessage = "Please enter a password!";
            return false;

        } else if (verifyPassword.equals(password)) {
            // Correct password is entered
            return true;

        } else {
            statusMessage = "Invalid password entered!";
            return false;
        }
    }

    /*
    ===============================
     VOLUNTEERING INTERESTS METHODS GO HERE!
    ===============================
     */    
    public void enterModeEditInterestAreas() {
        modeEditInterestAreas = true;
    }
    
    public void exitModeEditInterestAreas() {
        modeEditInterestAreas = false;
    }
    
    public void updateVolunteeringInterestAreas() {
        try {
            volunteeringInterestFacade.updateVolunteeringInterestAreas(selectedUser.getUserID(), userAreasOfInterest);
            exitModeEditInterestAreas();
        } catch (EJBException e) {
            statusMessage = "Something went wrong while updating your volunteering interests!";
        }
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
    
    public String showRegister() {
        statusMessage = null;
        return "Register?faces-redirect=true";
    }

    public String showProfile() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "Profile?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }

    public String showEditProfile() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "EditProfile?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }

    public String showSearchVolunteer() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "SearchVolunteer?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }
    
    public String showVolunteerInfo() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "VolunteerInfo?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }
    
    public String showSearchOrganization() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "SearchOrganization?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }
    
    public String showOrganizationInfo() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "OrganizationInfo?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }
    
    public String showVolunteeringActivity() {
        statusMessage = null;
        if (isLoggedIn()) {
            exitModeEditInterestAreas();
            return "ViewActivity?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }

    public String showCreateOpportunity() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "CreateOpportunity?faces-redirect=true";
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

    public String showOpportunityInfo() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "OpportunityInfo?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }
    
}

