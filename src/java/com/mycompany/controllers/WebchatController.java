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
import com.mycompany.entityclasses.VolunteerMatchAPI;
import com.mycompany.sessionbeans.VolunteeringOpportunitiesFacade;
import com.mycompany.sessionbeans.VolunteerFacade;
import com.mycompany.sessionbeans.OrganizationFacade;
import com.mycompany.sessionbeans.PhotoFacade;
import com.mycompany.sessionbeans.VolunteeringHistoryFacade;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
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
import org.primefaces.json.JSONException;


import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.chat.v2.Service;
import com.twilio.rest.chat.v2.service.Channel;
import com.twilio.rest.chat.v2.service.channel.Member;
import com.twilio.rest.chat.v2.service.channel.Message;
import com.twilio.rest.chat.v2.service.User;
import com.twilio.rest.chat.v2.service.user.UserChannel;
import com.twilio.rest.chat.v2.service.Role;
import com.twilio.rest.chat.v2.Credential;
import com.twilio.exception.ApiException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import org.primefaces.context.RequestContext;


@Named("WebchatController")
@SessionScoped
public class WebchatController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    
    // Twilio Console: https://www.twilio.com/console
    // Auth Tokens: https://support.twilio.com/hc/en-us/articles/223136027-Auth-Tokens-and-how-to-change-them
    // API Credentials: https://www.twilio.com/docs/api/rest/request
    // API Keys: https://www.twilio.com/docs/api/rest/keys
    private final String Twilio_API_URL = "https://chat.twilio.com/v2";
    private final String Twilio_Account_SID = "AC3c09366829bd06a96b2cc607873e9fce";
    private final String Twilio_Service_SID = "IS4732387b4a864b7481c1722d65941b79";
    private final String Twilio_API_Key_SID = "SKc67b29116e35278d4dd6931d529ab6ac";
    private final String Twilio_API_key_Secret = "UfMOOJpfWKdiMwTYUkrfgUOyAFdu2b5R";
    private final String Twilio_Auth_Token = "cf1aae22ae5c4a873e06bd34937e794d";
    
    private final AtomicBoolean isChatting = new AtomicBoolean(false);
    private ChatPollingThread chatPollingThread;
    private final Service service;
    private Channel selectedChannel;
    private List<Channel> channels;
    private ResourceSet<UserChannel> channelsUserBelongsTo;
    private String inputMessage;
    private Message lastMessage;
    private List<Message> messages;
    private ResourceSet<Message> twilioMessages;
    
    private String senderUsername;
    private String recipientUsername;
    private String statusMessage;
    
    
    // Constructor method instantiating an instance of WebchatController
    public WebchatController() {
        
        // Two Main Ways of Authentication
        // Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        // Twilio.init(API_KEY, API_SECRET, ACCOUNT_SID);
        Twilio.init(Twilio_API_Key_SID, Twilio_API_key_Secret, Twilio_Account_SID);
        
        // Fetches service where all channels for VolunterCloud exist
        service = Service.fetcher(Twilio_Service_SID).fetch();
    }
    
    
    /*
    =========================
    Getter and Setter Methods
    =========================
     */ 
    
    public Service getService() {
        return service;
    }
    
    public Channel getChannel() {
        return selectedChannel;
    }
    
    public void setChannel(Channel selectedChannel) {
        this.selectedChannel = selectedChannel;
    }
    
    public List<Channel> getChannels() {
        return channels;
    }
    
    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
    
    public ResourceSet<UserChannel> getChannelsUserBelongsTo() {
        return channelsUserBelongsTo;
    }
    
    public void setChannelsUserBelongsTo(ResourceSet<UserChannel> channelsUserBelongsTo) {
        this.channelsUserBelongsTo = channelsUserBelongsTo;
    }
    
    public String getInputMessage() {
        return inputMessage;
    }
    
    public void setInputMessage(String inputMessage) {
        this.inputMessage = inputMessage;
    }
    
    public Message getLastMessage() {
        return lastMessage;
    }
    
    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }
    
    public List<Message> getMessages() {
        return messages;
    }
    
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    
    public ResourceSet<Message> getTwilioMessages() {
        return twilioMessages;
    }
    
    public void setTwilioMessages(ResourceSet<Message> twilioMessages) {
        this.twilioMessages = twilioMessages;
    }
    
    public String getSenderUsername() {
        if (senderUsername == null || senderUsername.isEmpty())
            senderUsername = (String) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("username");
        return senderUsername;
    }
    
    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }
    
    public String getRecipientUsername() {
        return recipientUsername;
    }
    
    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }
    
    public String getStatusMessage() {
        return statusMessage;
    }
    
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    
    /*
    ===============================
     Webchat Methods
    ===============================
     */
    
    // Return True if a roommate is logged in; otherwise, return False
    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userEmail") != null;
    }
    
    public boolean isVolunteer() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userRole") == "Volunteer";
    }
    
    public boolean isOrganization() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userRole") == "Organization";
    }
    
    public String getChatUniqueIdentity(String senderUsername, String recipientUsername) {
      
        // Chat unique identity template: volunteerUsername-organizationUsername
        if (isVolunteer())
            return senderUsername + "-" + recipientUsername;
        else
            return recipientUsername + "-" + senderUsername;
    }
    
    public void getUserChannels() {

        updateUserChannels();
        return;
    }
    
    public Channel getChatChannel(String chatUniqueIdentity) {
        
        Channel channel = null;
        try {
            // Try to retrieve Channel
            channel = Channel.fetcher(Twilio_Service_SID, chatUniqueIdentity).fetch();
            
        }
        catch (ApiException ex) {
            Integer code = ex.getCode(); // Twilio's not found code: 20404
            String message = ex.getMessage();
            
            // Create Channel
            channel = Channel.creator(Twilio_Service_SID)
                .setFriendlyName(chatUniqueIdentity)
                .setUniqueName(chatUniqueIdentity)
                .setType(Channel.ChannelType.PRIVATE)
                .create();
            
            // Add chat members to Channel
            Member.creator(Twilio_Service_SID, channel.getSid(), getSenderUsername()).create();
            Member.creator(Twilio_Service_SID, channel.getSid(), recipientUsername).create();
        }
        return channel;
    }
    
    public void startConversation() {
        
        // Obtain the username of the logged-in User
        String username = getSenderUsername();
        
        // Initialize this service's channel unique identity
        String chatUniqueIdentity = getChatUniqueIdentity(username, recipientUsername);

        // Get Chat Channel
        selectedChannel = getChatChannel(chatUniqueIdentity);

        // Update messages of chat
        updateMessages();
        
//        // Prepare controller for active chat
//        isChatting.set(true);
//        
//        // Launch chat polling thread
//        chatPollingThread = new ChatPollingThread();
//        chatPollingThread.start();
    }
    
    public void sendMessage() {
        
        // Only send message the user has written something
        if (inputMessage != null && !inputMessage.isEmpty()) {

            // Obtain the username of the logged-in User
            String username = getSenderUsername();

            // Send message
            lastMessage = Message.creator(Twilio_Service_SID, selectedChannel.getSid())
                    .setFrom(username)
                    .setBody(inputMessage)
                    .create();

            // Update messages of chat
            updateMessages();

            //To avoid keep sending messages to the API
            //Message message = messages.get(0);
            //messages.add(message);

            // Reset input text
            inputMessage = "";
        }
    }
    
    // Twilio's REST API does not produce webhook responses in this way
    public void sendTestWebhookMessage() {
        
        // Update the service webhooks
        String newMessageTemplate = "A New message in ${CHANNEL} from ${USER}: ${MESSAGE}";
        Service service = Service.fetcher(Twilio_Service_SID).fetch();
        service = Service.updater(Twilio_Service_SID)
                .setNotificationsNewMessageEnabled(true)
                .setNotificationsNewMessageTemplate(newMessageTemplate)
                .setNotificationsNewMessageSound("default")
                .update();
        
        Channel channel = Channel.fetcher(Twilio_Service_SID, "andresjp-shpe").fetch();
        Message message = Message.creator(Twilio_Service_SID, channel.getSid())
                .setFrom("andresjp")
                .setBody("Test message!")
                .create();
    }
    
    public void updateMessages() {
        twilioMessages = Message.reader(Twilio_Service_SID, selectedChannel.getSid()).read();
        messages = new ArrayList<Message>();
        
        // Get messages of chat as a list of Strings
        for (Message message : twilioMessages)
            messages.add(message);
        
        return;
    }
    
    public void updateUserChannels() {
        Channel channel;
        channels = new ArrayList<Channel>();
        channelsUserBelongsTo = UserChannel.reader(Twilio_Service_SID, getSenderUsername()).read();
        List<Member> members = new ArrayList<Member>();
        
        // Get messages of chat as a list of Strings
        for (UserChannel userChannel : channelsUserBelongsTo) {
            channel = Channel.fetcher(Twilio_Service_SID, userChannel.getChannelSid()).fetch();
            channels.add(channel);
            members.add(Member.fetcher(Twilio_Service_SID, userChannel.getChannelSid(), "andresjp").fetch()); // Can parse recipient's username
            // Here each member has a lastMessageConsumedMessageIndex that can be used to retrieve that last message to show in the list
        }
        return;
    }
    
    // Thread that will update messages when user is in chat
    public class ChatPollingThread extends Thread {
        public void run() {
            try {
                while (isChatting.get()) {                
                    updateMessages();
                    RequestContext.getCurrentInstance().update("WebchatForm");
                    sleep(2000);
                }
                return;
            } catch (Exception ex) {
                Logger.getLogger(WebchatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Prepares controller to leave chat
    public void leaving() throws InterruptedException {
        isChatting.set(false);
        chatPollingThread.join();
        return;
    }
    
    // Prepares controller to leave chat
    public void testPoll() {
        return;
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
    
    public String showWebchat(String recipientUsername) {

        statusMessage = null;
        if (isLoggedIn()) {
            this.recipientUsername = recipientUsername;
            startConversation();
            return "Webchat?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }
    
    public String showActiveWebchats() {

        statusMessage = null;
        if (isLoggedIn()) {
            getUserChannels();
            return "ViewWebchats?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }
    
}

