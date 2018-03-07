var fingerprint = new Fingerprint2();
var request = window.superagent;
var activeChannel;
var client;

// Sets Chat Container Scroller to Bottom of Conversation 
window.onload = function() {
    
    // Got it from javascript sdk initialization
//    let client = await Twilio.Chat.Client.create(token);

    // Got it from javascript sdk initialization
//    Twilio.Chat.Client.create(token).then(client => {
//        // Use client
//        alert("Successful");
//    });
    
    // Got it from debugging page
//    let clientOptions = { logLevel: 'debug' };
//    let chatClient = new Twilio.Chat.Client(token, clientOptions);
    
    logIn();
//    client = new Twilio.Chat.Client(token, { logLevel: 'debug' });

 //This ONE!   
//    // Listen for new messages sent to a channel
//    myChannel.on('messageAdded', function(message) {
//      console.log(message.author, message.body);
//    });
    
//    channel.on('messageAdded', addMessage);
    
};

function logIn() {
  
    //Explains process of how to get client token
//    $.getJSON('/token', function(data) {
//        // The data sent back from the server should contain a long string, which
//        // is the token you'll need to initialize the SDK. This string is in a format
//        // called JWT (JSON Web Token) - more at http://jwt.io
//        console.log(data.token);
//
//        // Since the starter app doesn't implement authentication, the server sends
//        // back a randomly generated username for the current client, which is how
//        // they will be identified while sending messages. If your app has a login
//        // system, you should use the e-mail address or username that uniquely identifies
//        // a user instead.
//        console.log(data.identity);
//
//        handler(data);
//    });

    // Requests token to server with identity
//    request('/getToken?identity=' + 'andresjp', function(err, res) {
//        if (err) { throw new Error(res.text); }
//
//        var token = res.text;
//        client = new Twilio.Chat.Client(token, { logLevel: 'debug' });
//        //client.getSubscribedChannels().then(updateChannels);
//        return;
//    });
        
    // Requests token to server with endpoint and identity
//    fingerprint.get(function(endpointId) {
//        request('/getToken?identity=' + 'andresjp' + '&endpointId=' + endpointId, function(err, res) {
//            if (err) { throw new Error(res.text); }
//
//            var token = res.text;
//            client = new Twilio.Chat.Client(token, { logLevel: 'debug' });
//            //client.getSubscribedChannels().then(updateChannels);
//            return;
//        });
//    });

    var token = document.getElementById('WebchatForm:clientToken').value;
    return;
}




