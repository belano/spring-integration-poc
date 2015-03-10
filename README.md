## spring-integration-poc
This sample demonstrates a barebones inbound Web Service Gateway. 

+ Take a look at `WebServiceConfig.java` class where the Spring Web Services Message-dispatching Servlet is defined. 
+ Take also a look at `ws-dispatcher-servlet.xml` in the resources directory where the Spring WS EndpointMapping is defined
alongside the Spring Integration configuration where the actual Gateway is defined.

## Requirements
An ActiveMQ's instance running on its default port ie. `tcp://localhost:61616`

## Running the sample
The easiest way is to use the Spring Boot Maven Plugin which allows to run the application "in-place".
```
$ mvn spring-boot:run
```

## Test that it works
Configure your soapui-hermesJMS installation as per the following blog

http://xebee.xebia.in/index.php/2014/03/16/soap-over-jms-with/

Just make sure you add a destination "country-destination".

When the application is running, import the wsdl using the following url

http://localhost:8080/ws/countries.wsdl

Add a new JMS endpoint to the CountriesPortSoap11 

jms://activemq_session::queue_country-destination

Send a sample request and inspect the logs