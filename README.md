# Glucose Prediction Android App

## Features
- It lets an user create a new glucose level entry for the current hour
- It lets an user see their history of glucose vs hour

## Instructions for running the app
- Go to *[app/main/java/com/alfredoqt/glucoseprediction/ApiConstants.java](https://github.com/Auto-Learning-BioTech/GlucosePrediction/blob/master/app/src/main/java/com/alfredoqt/glucoseprediction/ApiConstants.java)* and make the following changes to the *HOST* constant:

```java
public class ApiConstants {

    ...
    public static final String HOST = "The ip address where the API is running";
    ...

}
```
- Go to *[app/main/res/xml/network_security_config](https://github.com/Auto-Learning-BioTech/GlucosePrediction/blob/master/app/src/main/res/xml/network_security_config.xml)* and make the following changes. This will add your ip address to the app's trusted domains so you can make insecure *http* calls

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <!-- TODO: Change this to your own ip -->
        <domain includeSubdomains="true">SAME_IP_ADDRESS_AS_API_CONSTANTS</domain>
    </domain-config>
</network-security-config>
```

## Next steps
- Make a background service that makes a request to the [Prediction API](https://github.com/Auto-Learning-BioTech/Glucose-Prediction) every hour to get the predicted status of the glucose level at the current date
- Let the users register themselves in the application in order to have actual custom data

## Screenshots

### New Entry

![New Entry](https://github.com/Auto-Learning-BioTech/GlucosePrediction/blob/master/screenshots/new_entry.png)

### History

![History](https://github.com/Auto-Learning-BioTech/GlucosePrediction/blob/master/screenshots/history.png)
