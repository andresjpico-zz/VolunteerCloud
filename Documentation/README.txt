
KNOWN BUGS

- The most important bug to be aware of is that the program is not using session-scoped variables in most of the pages.. this means that it is relying on the server's memory storage of the server for most of the data of each session.. The implication of this bug is that as soon as another user signs-in after you, the user-data in the server's memory storage is replaced by the new user who logged in.. So if you go to let's say 'Edit Profile' or 'View Activity' after another user signed-in, the page will show the data from the other user.. The solution is easy but a bit tedious.. You'll see that in the functions 'initializeSessionMap', we are storing the user who just logged in.. The solution is to use that session-scoped 'user' to perform operations relevant to each user in the back-end.. So for example in 'ViewActivity', we could replace the 'UsersController.selectedUser' by 'sessionScope.user' or something similar just as in 'Profile'..  Edit: Now it's been fixed for user related data (UsersController).. But problem should still remain for 'Search' and 'Info' pages which include volunteer, organization, and opportunity controllers..
- After filtering volunteering history, and then re-entering to the ViewActivity Page, the page still shows the filtered volunteering history
- For some reason When selecting a Volunteer or Organization 'Info' page, their activity is not showing
- When creating a new account, if you visit 'ViewActivity' it throws an error for not having registered to an opportunity


DEVELOPMENT -> PRODUCTION

1) Project Stage
web.xml -> Change Development to Production

2) Storage Path
glassfish-web.xml -> Change storage location path
constants.java -> Change storage location path

3) ZipCodes API
organizationController.java -> Change to use zipcodes api
volunteerController.java -> Change to use zipcodes api
volunteeringOpportunitiesController.java -> Change to use zipcodes api

4) Twilio Service
usersController.java -> Change development service to production
webchatController.java -> Change development service to production

5) New Message Notification
headerTemplate.xhtml -> Uncomment primefaces poll element



Steps PRODUCTION -> DEVELOPMENT

1) Project Stage
web.xml -> Change Production to Development

2) Storage Path
glassfish-web.xml -> Change storage location path
constants.java -> Change storage location path

3) ZipCodes API
volunteerController.java -> Change to use dummy zipcodes
organizationController.java -> Change to use dummy zipcodes
volunteeringOpportunitiesController.java -> Change to use dummy zipcodes

4) Twilio Service
usersController.java -> Change production service to development
webchatController.java -> Change production service to development

5) New Message Notification
headerTemplate.xhtml -> Comment primefaces poll element


