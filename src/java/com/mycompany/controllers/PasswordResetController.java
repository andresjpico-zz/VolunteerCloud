package com.mycompany.controllers;

import com.mycompany.entityclasses.Constants;
import com.mycompany.entityclasses.Users;
import com.mycompany.sessionbeans.UsersFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

@Named(value = "PasswordResetController")
@SessionScoped
/**
 * Based on Osman Balci's PasswordResetManager.java
 * @author Andres
 */
public class PasswordResetController implements Serializable {

    // Instance Variables (Properties)
    private String email;
    private String username;
    private String userRole;
    private String statusMessage = "";
    private String securityAnswer;
    private String password;


    @EJB
    private UsersFacade usersFacade;

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
    ================
     Instance Methods
    ================
     */
    
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
        
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("volunteerMatchID", user.getVolunteerMatchID());

    }

    // Process the submitted username
    public String emailSubmit() {

        // Obtain the object reference of the Roommate object with username
        Users user = usersFacade.findByEmail(email);

        if (user == null) {
            throwFacesMessage("Entered email does not exist!");
            return "";
        } else {
            // Entered username exists
            statusMessage = "";

            // Redirect to show the SecurityQuestion page
            return "SecurityQuestion?faces-redirect=true";
        }
    }
    
    // Process the submitted username
    public String usernameSubmit() {

        // Obtain the object reference of the Roommate object with username
        Users user = usersFacade.findByUsername(username);
        
        if (user == null) {
            throwFacesMessage("Entered username does not exist!");
            return "";
        } else {
            // Entered username exists
            statusMessage = "";
            return "SecurityQuestion?faces-redirect=true";
        }
    }

    // Process the submitted answer to the security question
    public String securityAnswerSubmit() {

        // Obtain the object reference of the Roommate object with username
        Users user = usersFacade.findByUsername(username);
        
        String actualSecurityAnswer = user.getSecurityAnswer();
        String enteredSecurityAnswer = getSecurityAnswer();

        if (actualSecurityAnswer.equals(enteredSecurityAnswer)) {
            // Answer to the security question is correct
            statusMessage = "";

            // Redirect to show the ResetPassword page
            return "ResetPassword?faces-redirect=true";
        } else {
            // Answer to the security question is wrong
            throwFacesMessage("Security question answer is incorrect!");
            return "";
        }
    }

    // Return the security question selected by the Roommate object with username
    public String getSecurityQuestion() {

        // Obtain the object reference of the Roommate object with username
        Users user = usersFacade.findByUsername(username);

        // Obtain the number of the security question selected by the Roommate
        int securityQuestion = user.getSecurityQuestion();

        // Return the security question corresponding to the question number
        return Constants.SECURITY_QUESTIONS[securityQuestion];
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
            throwFacesMessage("Password and Confirm Password must match!");
        } else {
            statusMessage = "";
        }
    }

    public String resetPassword() {

        if (statusMessage == null || statusMessage.isEmpty()) {

            // Obtain the object reference of the Roommate object with email
            Users user = usersFacade.findByUsername(username);

            try {
                // Reset Roommate object's password
                user.setPassword(password);

                // Update the database
                usersFacade.edit(user);

                // Initialize the instance variables
                email = username = statusMessage = securityAnswer = password = "";

            } catch (EJBException e) {
                throwFacesMessage("Something went wrong while resetting your password, please try again!");
                
                // Redirect to show the ResetPassword page
                return "";
            }

            //
            userRole = Constants.USER_ROLES[user.getUserRole()];

            // Initialize the session map with Roommate properties of interest
            initializeSessionMap(user);
            
            // Redirect to show the index (Home) page
            return "Dashboard?faces-redirect=true";
            
        } else {
            // Redirect to show the ResetPassword page
            return "ResetPassword?faces-redirect=true";
        }
    }
    
    // Throws desired error message
    public void throwFacesMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(message));
    }

}