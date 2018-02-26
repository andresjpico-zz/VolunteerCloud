// Sets Chat Container Scroller to Bottom of Conversation 
window.onload = function() {
    setScrollToBottom();
    hideEmptyRow();
};

function setScrollToBottom() { 
    // Setting it to an extreme bottom position does the trick for the purposes of the project
    jQuery('#WebchatForm\\:webchatMessageList .ui-datatable-scrollable-body').animate({ scrollTop: "1000000px" }); 
}

function hideEmptyRow() {
    // Hides empty row displayed when no messages have been sent before
    jQuery('#WebchatForm\\:webchatMessageList .ui-datatable-empty-message').hide();
}
