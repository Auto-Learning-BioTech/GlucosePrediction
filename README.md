# Glucose Prediction Aplicación Android 

## Características
- Permite al usuario crear una nueva entrada de nivel de glucosa para la hora actual
- Permite al usuario ver su historial de glucosa vs. tiempo

## Instrucciones para ejecutar la aplicación
- Ir hacia *[app/main/java/com/alfredoqt/glucoseprediction/ApiConstants.java](https://github.com/Auto-Learning-BioTech/GlucosePrediction/blob/master/app/src/main/java/com/alfredoqt/glucoseprediction/ApiConstants.java)* y realice los siguientes cambios en la constante *HOST* :

```java
public class ApiConstants {

    ...
    public static final String HOST = "The ip address where the API is running";
    ...

}
```
- Ir hacia *[app/main/res/xml/network_security_config](https://github.com/Auto-Learning-BioTech/GlucosePrediction/blob/master/app/src/main/res/xml/network_security_config.xml)* y haga los siguientes cambios. Esto agregará su dirección IP a los dominios de confianza de la aplicación para que pueda hacer llamadas inseguras *http* 

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <!-- TODO: Change this to your own ip -->
        <domain includeSubdomains="true">SAME_IP_ADDRESS_AS_API_CONSTANTS</domain>
    </domain-config>
</network-security-config>
```

## Próximos pasos
- Realice un servicio en segundo plano que realice una solicitud a [Prediction API](https://github.com/Auto-Learning-BioTech/Glucose-Prediction) cada hora para obtener el estado previsto del nivel de glucosa en la fecha actual.
- Permita que los usuarios se registren en la aplicación para tener datos personalizados reales

## Capturas

### Nueva entrada

![New Entry](https://github.com/Auto-Learning-BioTech/GlucosePrediction/blob/master/screenshots/new_entry.png)

### Historial

![History](https://github.com/Auto-Learning-BioTech/GlucosePrediction/blob/master/screenshots/history.png)
