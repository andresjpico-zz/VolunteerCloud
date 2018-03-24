package com.mycompany.controllers;

import com.mycompany.entityclasses.Users;
import com.mycompany.entityclasses.Photo;
import com.mycompany.entityclasses.Constants;
import com.mycompany.sessionbeans.UsersFacade;
import com.mycompany.controllers.util.JsfUtil;
import com.mycompany.controllers.util.JsfUtil.PersistAction;
import java.io.Serializable;
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
import javax.faces.context.FacesContext;
import org.primefaces.json.JSONObject;
import java.util.concurrent.atomic.AtomicBoolean;
import org.primefaces.context.RequestContext;

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
import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.ChatGrant;
import java.io.IOException;
import java.util.stream.StreamSupport;

/**
 *
 * @author Andres
 */

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
    private String clientToken;
    private final Service service;
    private Channel selectedChannel;
    private MyOwnChannel selectedOwnChannel;
    private List<Channel> channels;
    private List<MyOwnChannel> myOwnChannels;
    private ResourceSet<UserChannel> channelsUserBelongsTo;
    private String inputMessage;
    private Message lastMessage;
    private List<Message> messages;
    private ResourceSet<Message> twilioMessages;
    private ChatRecipient selectedChatRecipient;
    private List<ChatRecipient> chatRecipients;
    
    private boolean unreadMessages = false;
    private String senderName;
    private String senderUsername;
    private String recipientName;
    private String recipientUsername;
    private String statusMessage;
    
    /*
    The @EJB annotation implies that the EJB container will perform an injection of the object
    reference of the <file_name>Facade object into <file_name>Facade when it is created at runtime.
     */
    @EJB
    private UsersFacade usersFacade;
    
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
    
    public String getClientToken() {
        return clientToken;
    }
    
    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }
    
    public Service getService() {
        return service;
    }
    
    public Channel getSelectedChannel() {
        return selectedChannel;
    }
    
    public void setSelectedChannel(Channel selectedChannel) {
        this.selectedChannel = selectedChannel;
    }
    
    public MyOwnChannel getSelectedOwnChannel() {
        return selectedOwnChannel;
    }
    
    public void setSelectedOwnChannel(MyOwnChannel selectedOwnChannel) {
        this.selectedOwnChannel = selectedOwnChannel;
    }
    
    public List<Channel> getChannels() {
        return channels;
    }
    
    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
    
    public List<MyOwnChannel> getMyOwnChannels() {
        return myOwnChannels;
    }
    
    public void setMyOwnChannels(List<MyOwnChannel> myOwnChannels) {
        this.myOwnChannels = myOwnChannels;
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
    
    public ChatRecipient getSelectedChatRecipient() {
        return selectedChatRecipient;
    }
    
    public void setSelectedChatRecipient(ChatRecipient selectedChatRecipient) {
        this.selectedChatRecipient = selectedChatRecipient;
    }
    
    public List<ChatRecipient> getChatRecipients() {
        return chatRecipients;
    }
    
    public void setChatRecipients(List<ChatRecipient> chatRecipients) {
        this.chatRecipients = chatRecipients;
    }
    
    public boolean getUnreadMessages() {
        return unreadMessages;
    }
    
    public void setUnreadMessages(boolean unreadMessages) {
        this.unreadMessages = unreadMessages;
    }
    
    public String getSenderName() {
        if (isVolunteer())
            senderName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("firstName");
        else
            senderName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("organizationName");
        return senderName;
    }
    
    public void setSenderName(String senderName) {
        this.senderName = senderName;
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
    
    public String getRecipientName() {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
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
  
    // Checks if sent by recipient by comparing name of sender
    public boolean isSentByRecipient_ByName(String messageSender) {
        return (!messageSender.equals(getSenderName())) ? true : false;
    }
    
    // Checks if sent by recipient by comparing name of sender
    public boolean isSentByRecipient(String attributes) {
        
        JSONObject jsonObject = new JSONObject(attributes); 
        String messageSender = jsonObject.optString("username");
        return (!messageSender.equals(senderUsername)) ? true : false;
    }
    
    public String getRecipientName(JSONObject attributes) {

        // Returns the name of the recipient
        if (isVolunteer())
            return attributes.optString("organization");
        else
            return attributes.optString("volunteer");
    }
    
    public String getRecipientUsername(String chatUniqueIdentity) {

        // Returns the username of the recipient
        if (isVolunteer())
            return chatUniqueIdentity.split("-")[1];
        else
            return chatUniqueIdentity.split("-")[0];
    }
    
    public String getChatUniqueIdentity(String senderUsername, String recipientUsername) {
      
        // Chat unique identity template: volunteerUsername-organizationUsername
        if (isVolunteer())
            return senderUsername + "-" + recipientUsername;
        else
            return recipientUsername + "-" + senderUsername;
    }
    
    public void generateTwilioAccessToken() {
        
        ChatGrant grant = new ChatGrant();
        grant.setServiceSid(Twilio_Service_SID);

        AccessToken token = new AccessToken.Builder(Twilio_Account_SID, Twilio_API_Key_SID, Twilio_API_key_Secret)
            .identity("andresjp").grant(grant).ttl(3600).build();
        
        clientToken = token.toJwt();
        return;
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
//        // Launch chat polling thread
//        chatPollingThread = new ChatPollingThread();
//        chatPollingThread.start();
    }
    
    public void sendMessage() {
        
        // Only send message the user has written something
        if (inputMessage != null && !inputMessage.isEmpty()) {

        JSONObject attributes = new JSONObject()
                .put("username", senderUsername);
                        
            // Send message
            lastMessage = Message.creator(Twilio_Service_SID, selectedChannel.getSid())
                    .setFrom(getSenderName())
                    .setBody(inputMessage)
                    .setAttributes(attributes.toString())
                    .create();

            // Use this when testing to avoid making calls to the API
            //Message message = messages.get(0);
            //messages.add(message);
  
            // Update messages no longer needs to be called here thanks to js 'onMessageAdded' event
//            // Update messages of chat
//            updateMessages();
//            // Being called from updateMessages() now - Update sender's last message read
//            //updateLastMessageRead();

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
    
    public Channel getChatChannel(String chatUniqueIdentity) {
        
        Channel channel = null;
        try {
            // Try to retrieve Channel
            channel = Channel.fetcher(Twilio_Service_SID, chatUniqueIdentity).fetch();
        }
        catch (ApiException ex) {
            Integer code = ex.getCode(); // Twilio's not found code: 20404
            String message = ex.getMessage();
            String volunteerName = (isVolunteer()) ? senderName : recipientName;
            String organizationName = (isOrganization()) ? senderName : recipientName;
            
            // CREATE ATTRIBUTES JSON HERE!
            JSONObject jsonObject = new JSONObject()
                    .put("volunteer", volunteerName)
                    .put("organization", organizationName)
                    .put("lastMessage", "")
                    .put(senderUsername, 0)
                    .put(recipientUsername, 0);
            String attributes = jsonObject.toString();
            
            // Create Channel
            channel = Channel.creator(Twilio_Service_SID)
                .setFriendlyName(chatUniqueIdentity)
                .setUniqueName(chatUniqueIdentity)
                .setType(Channel.ChannelType.PRIVATE)
                .setAttributes(attributes)
                .create();
            
            // Add chat members to Channel
            Member.creator(Twilio_Service_SID, channel.getSid(), getSenderUsername()).create();
            Member.creator(Twilio_Service_SID, channel.getSid(), recipientUsername).create();
        }
        return channel;
    }
    
    public void getUserChannels() {
        updateUserChannels();
        getUserChatInfo();
        return;
    }
    
    public void updateUserChannels() {

        boolean isUpToDate;
        Channel channel;
        MyOwnChannel myOwnChannel;
        myOwnChannels = new ArrayList<MyOwnChannel>();
        unreadMessages = false;
        channelsUserBelongsTo = UserChannel.reader(Twilio_Service_SID, getSenderUsername()).read();
        
        // Get messages of chat as a list of Strings
        for (UserChannel userChannel : channelsUserBelongsTo) {
            channel = Channel.fetcher(Twilio_Service_SID, userChannel.getChannelSid()).fetch();
            isUpToDate = checkForUnreadMessages(channel);
            myOwnChannel = new MyOwnChannel(channel, isUpToDate);
            myOwnChannels.add(myOwnChannel);
        }
        return;
    }

    public void updateMessages() {
        // There is no way to get size of iterator without iterating :(
        // I could still make a dummyMessages list and compare its size with the messages list, and then only update the page if new messages have been received
        twilioMessages = Message.reader(Twilio_Service_SID, selectedChannel.getSid()).read();
        messages = new ArrayList<Message>();

        for (Message message : twilioMessages)
            messages.add(message);
        
        updateLastMessageRead();
        return;
    }
    
    public void updateLastMessageRead() {
        selectedChannel = Channel.fetcher(Twilio_Service_SID, selectedChannel.getSid()).fetch(); //I could retrieve the updated channel from Twilio like this
        JSONObject attributes = new JSONObject(selectedChannel.getAttributes()); 
        if (messages.size() > 0) attributes.put("lastMessage", messages.get(messages.size() - 1).getBody());
        attributes.put(senderUsername, messages.size());
        
        // Update the channel
        Channel.updater(Twilio_Service_SID, selectedChannel.getSid())
            .setAttributes(attributes.toString())
            .update();
        
        return;
    }
    
    // Returns whether user is up to date with channel
    public boolean checkForUnreadMessages(Channel channel) {

        JSONObject jsonObject  = new JSONObject(channel.getAttributes());
        Integer senderLastMessageRead = jsonObject.optInt(getSenderUsername());
        
        if (senderLastMessageRead != channel.getMessagesCount()) {
            unreadMessages = true;
            return false;
        }
        return true;
    }
    
    // 
    public void getUserChatInfo() {

        String recipientName;
        String recipientUsername;
        String lastMessage;
        JSONObject attributes;
        ChatRecipient chatRecipient;
        chatRecipients = new ArrayList<ChatRecipient>();
        
        for (MyOwnChannel myOwnChannel : myOwnChannels) {
            attributes = new JSONObject(myOwnChannel.getChannel().getAttributes());
            recipientName = getRecipientName(attributes);
            recipientUsername = getRecipientUsername(myOwnChannel.getChannel().getUniqueName());
            lastMessage = attributes.optString("lastMessage");
            chatRecipient = new ChatRecipient(recipientName, recipientUsername, myOwnChannel, lastMessage);
            chatRecipients.add(chatRecipient);
        }
        return;
    }
    
    // Implementing my own channel class to keep track of webchats with new messages
    public class MyOwnChannel {
        
        private Channel channel;
        private boolean upToDate;
        
        public MyOwnChannel() {
        
        }

        public MyOwnChannel(Channel channel) {
            this.channel = channel;
        }
        
        public MyOwnChannel(Channel channel, boolean upToDate) {
            this.channel = channel;
            this.upToDate = upToDate;
        }
        
        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }
        
        public boolean getUpToDate() {
            return upToDate;
        }

        public void setUpToDate(boolean upToDate) {
            this.upToDate = upToDate;
        }
        
    }
    
    // Implementing my own chat recipient class to display user information in the ViewWebchats screen
    public class ChatRecipient {
        
        private String recipientName;
        private String recipientUsername;
        private Users chatRecipient;
        private MyOwnChannel myOwnChatChannel;
        private String lastMessage;
        
        public ChatRecipient() {
        
        }

        public ChatRecipient(String recipientName, String recipientUsername, MyOwnChannel myOwnChatChannel, String lastMessage) {
            this.recipientName = recipientName;
            this.recipientUsername = recipientUsername;
            this.myOwnChatChannel = myOwnChatChannel;
            this.lastMessage = lastMessage;
        }
        
        public String getRecipientName() {
            return recipientName;
        }

        public void setRecipientName(String recipientName) {
            this.recipientName = recipientName;
        }
        
        public String getRecipientUsername() {
            return recipientUsername;
        }

        public void setRecipientUsername(String recipientUsername) {
            this.recipientUsername = recipientUsername;
        }
        
        public Users getChatRecipient() {
            return chatRecipient;
        }

        public void setChatRecipient(Users chatRecipient) {
            this.chatRecipient = chatRecipient;
        }
        
        public MyOwnChannel getMyOwnChatChannel() {
            return myOwnChatChannel;
        }

        public void setMyOwnChatChannel(MyOwnChannel myOwnChatChannel) {
            this.myOwnChatChannel = myOwnChatChannel;
        }
        
            public String getLastMessage() {
            return lastMessage;
        }

        public void setLastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
        }
        
    }
    
    // Thread that will update messages when user is in chat
    // Due to the way application servers work, we won't be able to obtain anything from RequestContext
    // The thread could be created bu <poll> would still be needed for updating the page
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
    
    public String showWebchat(Users recipientUser) {

        statusMessage = null;
        if (isLoggedIn()) {
            this.recipientName = (recipientUser.getUserRole() == 0) ? recipientUser.getFirstName() : recipientUser.getOrganizationName();
            this.recipientUsername = recipientUser.getUsername();
            generateTwilioAccessToken();
            startConversation();
            return "Webchat?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }
    
    public void showWebchatFromWebchatList(ChatRecipient chatRecipient) {

        try {
            this.recipientName = chatRecipient.getRecipientName();
            this.recipientUsername = chatRecipient.getRecipientUsername();
            this.selectedChannel = chatRecipient.getMyOwnChatChannel().getChannel();
            generateTwilioAccessToken();
            startConversation();
            FacesContext.getCurrentInstance().getExternalContext().redirect("Webchat.xhtml?faces-redirect=true");
        } 
        catch(IOException e) { 
        
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

