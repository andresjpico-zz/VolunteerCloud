
Steps Development -> Production

1) Project Stage
web.xml -> Change Development to Production

2) Storage Path
glassfish-web.xml -> Change storage location path
constants.java -> Change storage location path

3) ZipCodes API
volunteerController.java -> Change to use zipcodes api
organizationController.java -> Change to use zipcodes api
volunteeringOpportunitiesController.java -> Change to use zipcodes api

4) Twilio Service
usersController.java -> Change development service to production
webchatController.java -> Change development service to production

5) New Message Notification
headerTemplate.xhtml -> Uncomment primefaces poll element



Steps Production -> Development

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


