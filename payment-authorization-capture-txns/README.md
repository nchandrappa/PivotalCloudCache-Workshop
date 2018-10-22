## Payment Authorization & Capture  Request/Repsonse Demo
This is a simple app which integration of a 3rd party website that supports two posts and two requests that echo the data that was posted.  It is inspired from the documentation found here:

#Authorize - https://docs.adyen.com/api-explorer/#/Payment/v40/authorise
#Capture - https://docs.adyen.com/api-explorer/#/Payment/v40/capture

Please reach out to Pivotal Architecture for Data(PAD) team at pad@pivotal.io for any information

![IMG_002](https://github.com/Pivotal-Field-Engineering/pad-pcc-demo/blob/master/images/IMG_002.png) 
  
#### What you will build
You will build a service that simulates calling a 3rd party to authorize a payment and then to capture the payment authorization information.
Sample data is loaded on startup via Spring Boot's data loader.  That data and the syntax for the required posts can be found in `data.sql`.
The GET apis will work immediately because of the data initialization. 

The Ayden Request Response service has the following APIs:

`- GET /capture`          - get authorizations that have been captured
`- GET /authorize`         - get authorizations stored in the embedded database on the server side
`- curl -d "@authorize.txt" -H "Content-Type: application/json" -X POST http://localhost:8080/authorize
`- curl -d "@capture-payment.txt" -H "Content-Type: application/json" -X POST http://localhost:8080/capture

Here are some test links:

`- https://authorization-capture-api-silly-genet.apps.pcfone.io/browser/index.html#/authorize/4
`- https://authorization-capture-api-silly-genet.apps.pcfone.io/authorize
`- https://authorization-capture-api-silly-genet.apps.pcfone.io/capture
  
#### What you will need
Checkout the code from https://github.com/wxlund/pad-pcc-demo.git. 

You can launch the program with:

./gradlew assemble
java -jar build/libs/ayden-request-response-0.0.1-SNAPSHOT.jar 

- You can alo import the code straight into your IDE: 
Spring Tool Suite (STS) / IntelliJ IDEA
