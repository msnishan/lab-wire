Wire Mock Example
=================

<h3>Steps to Run :</h3>
- - -
 > git clone url<br>
 cd lab-wire<br>
 ./gradlew clean run -Dspring.profiles.active=live (for live mode)<br>
 ./gradlew clean run -Dspring.profiles.active=stub (for stub mode)<br>
 - - -
 >>
 
- This application uses two modes : **live** and **stub**.
- In live mode the application invokes a real service (details in application-live.properties.
- In Stub mode the client remains the same but the url gets changed to a wire mock server url (application-stub.properties).
- In stub mode the application first starts a WireMock server and registers its behavior (details in WireConfig.java).
- When you run the application in stub mode, apart from invoking the mock service it verifies also whether the request
 was actually made to the server or not.
 
Start from Main.java


 


