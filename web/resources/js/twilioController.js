var fingerprint = new Fingerprint2();
var request = window.superagent;
var activeChannelName;
var activeChannel;
var client;
var token;

// Initializes client when webchat is launched 
window.onload = function() {
    
    // Sets Chat Container Scroller to Bottom of Conversation 
    setScrollToBottom();
    hideEmptyRow();

    // Get access token from backend
    token = getToken();
    
    // Initiate client
    initializeClient();
    return;
};

function initializeClient() {
        
    // Contents inside wait to run just until client has been fully instantiated
    client = new Twilio.Chat.Client.create(token, { logLevel: 'debug' }).then(function(chatClient) {
        
        // Assign initialized chatClient to global client
        client = chatClient;
        
        // Client identity
        var clientIdentity = client.user.identity;

        // Channel unique name
        activeChannelName = getActiveChannelName();
        
        // Lists channels this user belongs to
        client.getUserChannelDescriptors().then(function(paginator) {
            for (i=0; i<paginator.items.length; i++) {
                var channel = paginator.items[i];
                
                if (channel.uniqueName === activeChannelName) {    
                    // Turn on events when message is received for channel
                    retrieveChannel(channel);
                    break;
                }
            }
        });
    
    });
    return;
}

function retrieveChannel(channelDescriptor) {
    
    // Contents inside wait to run just until channel has been fully retrieved
    activeChannel = channelDescriptor.getChannel().then(function(channel) {
        
        // Assign retrieved channel to global active channel
        activeChannel = channel;

        // Send test message to the selected channel to ensure it works
        //activeChannel.sendMessage("Testing sending message");
        
        // Listen for new messages sent to a channel
        activeChannel.on('messageAdded', updateMessages);
    });
    return;
}

function updateMessages(message) {
    // Call updateMessages() from bean and refreshes page when done
    rcUpdateMessages();
    return;
}

function sendMessage() {
    // Call sendMessages() from bean and refreshes page when done
    rcSendMessage();
    return;
}

function getToken() {
    // Retrieves access token sent from the backend
    return document.getElementById('WebchatForm:clientToken').value;
}

function getActiveChannelName() {
    // Retrieves channel name sent from the backend
    return document.getElementById('WebchatForm:channelName').value;
}

function setScrollToBottom() { 
    // Setting it to an extreme bottom position does the trick for the purposes of the project
    jQuery('#WebchatForm\\:webchatMessageList .ui-datatable-scrollable-body').animate({ scrollTop: "1000000px" }); 
}

function hideEmptyRow() {
    // Hides empty row displayed when no messages have been sent before
    jQuery('#WebchatForm\\:webchatMessageList .ui-datatable-empty-message').hide();
}

